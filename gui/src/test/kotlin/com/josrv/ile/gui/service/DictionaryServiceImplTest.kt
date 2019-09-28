package com.josrv.ile.gui.service

import com.josrv.ile.common.msg.Word
import com.josrv.ile.messaging.RabbitMessagingClient
import io.kotlintest.specs.StringSpec
import java.net.URI

class DictionaryServiceImplTest : StringSpec({
    "send and receive" {

        val client = RabbitMessagingClient()
        client.connect(URI("amqp://guest:guest@localhost:5672"))

        val message = Word("testWord")
        client.send("words", Word.toBytes(message), "123")

        client.registerReceiver(Word.config) { word, clientId ->



        }
    }
})