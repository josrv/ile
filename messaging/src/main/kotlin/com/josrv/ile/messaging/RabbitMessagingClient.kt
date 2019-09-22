package com.josrv.ile.messaging

import com.josrv.ile.common.msg.Message
import com.josrv.ile.common.msg.MessageConfig
import com.rabbitmq.client.*
import java.net.URI

class RabbitMessagingClient : MessagingClient {

    private lateinit var connection: Connection
    private lateinit var channel: Channel

    override fun connect(uri: URI) {
        connection = ConnectionFactory().apply {
            setUri(uri)
        }.newConnection()

        channel = connection.createChannel()

        //TODO extract to configuration
        channel.exchangeDeclare("words", BuiltinExchangeType.FANOUT, true)
        channel.queueDeclare("lookup", true, false, false, null)
        channel.queueBind("lookup", "words", "")

        channel.exchangeDeclare("definitions", BuiltinExchangeType.FANOUT, true)
        channel.queueDeclare("lookup-result", true, false, false, null)
        channel.queueBind("lookup-result", "definitions", "")
    }

    override fun disconnect() {
        channel.close()
        connection.close()
    }

    override fun send(exchange: String, message: ByteArray) {
        channel.basicPublish(exchange, "", null, message)
    }

    override fun <T : Message> registerReceiver(messageConfig: MessageConfig<T>, consumer: (T) -> Unit) {
        channel.basicConsume(messageConfig.queue, true, "client", object : DefaultConsumer(channel) {
            override fun handleDelivery(
                consumerTag: String?,
                envelope: Envelope?,
                properties: AMQP.BasicProperties?,
                body: ByteArray
            ) {
                val message = messageConfig.mapper(body)
                consumer(message)
            }
        })
    }

}