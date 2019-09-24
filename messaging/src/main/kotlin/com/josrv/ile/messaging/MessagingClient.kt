package com.josrv.ile.messaging

import com.josrv.ile.common.msg.Message
import com.josrv.ile.common.msg.MessageConfig
import java.net.URI

interface MessagingClient {
    fun connect(uri: URI)
    fun disconnect()

    fun send(exchange: String, message: ByteArray, clientId: ClientId)
    fun <T : Message> registerReceiver(messageConfig: MessageConfig<T>, consumer: (T, ClientId) -> Unit)
}