package com.josrv.ile.messaging

import com.josrv.ile.common.msg.Message
import com.josrv.ile.common.msg.MessageConfig
import java.net.URI

interface MessagingClient {
    fun connect(uri: URI)
    fun disconnect()

    fun send(exchange: String, message: ByteArray)
    fun <T : Message> registerReceiver(messageConfig: MessageConfig<T>, consumer: (T) -> Unit)
}