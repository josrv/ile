package com.josrv.ile.lookup

import com.josrv.ile.common.POS
import com.josrv.ile.common.msg.Definition
import com.josrv.ile.common.msg.Definitions
import com.josrv.ile.common.msg.Word
import com.josrv.ile.messaging.MessagingClient
import com.josrv.ile.messaging.RabbitMessagingClient
import java.net.URI
import kotlin.concurrent.thread

class LookupProvider {
    private lateinit var messagingClient: MessagingClient

    private val definitions = mapOf(
        "have" to listOf(
            Definition("have", POS.VERB, "To possess, own, hold."),
            Definition(
                "have",
                POS.VERB,
                "To be related in some way to (with the object identifying the relationship)."
            ),
            Definition(
                "have",
                POS.VERB,
                "To partake of a particular substance (especially a food or drink) or action."
            )
        ),
        "human" to listOf(
            Definition(
                "human",
                POS.NOUN,
                "A human being, whether man, woman or child."
            ),
            Definition(
                "human",
                POS.ADJECTIVE,
                "Of or belonging to the species Homo sapiens or its closest relatives. "
            )
        )
    )

    fun start() {
        messagingClient = RabbitMessagingClient()
        messagingClient.connect(URI("amqp://guest:guest@localhost:5672"))
        messagingClient.registerReceiver(Word.config) { (word) ->
            val definitions = Definitions(this.definitions.getOrDefault(word, emptyList()))
            messagingClient.send("definitions", Definitions.toBytes(definitions))
        }
    }

    fun stop() {
        messagingClient.disconnect()
    }
}

fun main() {
    val lookupProvider = LookupProvider().apply { start() }

    Runtime.getRuntime().addShutdownHook(thread(start = false) {
        lookupProvider.stop()
    })
}