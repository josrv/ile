package com.josrv.ile.lookup

import com.josrv.ile.common.POS
import com.josrv.ile.common.msg.Definition
import com.josrv.ile.common.msg.Definitions
import com.josrv.ile.common.msg.Word
import com.josrv.ile.messaging.ClientId
import com.josrv.ile.messaging.MessagingClient
import com.josrv.ile.messaging.RabbitMessagingClient
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.map
import java.net.URI
import java.util.concurrent.CancellationException
import java.util.concurrent.ConcurrentHashMap
import kotlin.concurrent.thread
import kotlin.coroutines.coroutineContext

class LookupProvider {
    private lateinit var messagingClient: MessagingClient

    private val jobs = ConcurrentHashMap<ClientId, Deferred<Definitions>>()

    private val definitions = mapOf(
        "have" to Definitions(listOf(
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
        )),
        "human" to Definitions(listOf(
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
        ))
    )

    fun start() {
        messagingClient = RabbitMessagingClient()
        messagingClient.connect(URI("amqp://guest:guest@localhost:5672"))

        val readyDefinitions = Channel<Definitions>()

        //TODO research and refactor
        messagingClient.registerReceiver(Word.config) { (word), clientId ->
            val definitionsJob = jobs.compute(clientId) { c, u ->
                u?.cancel()

                GlobalScope.async {
                    try {
                        delay(1000L)
                        definitions.getOrElse(word) { Definitions(emptyList()) }
                    } catch (e: CancellationException) {
                        Definitions(emptyList())
                    }
                }
            }

            definitionsJob?.invokeOnCompletion {
                val definitions = definitionsJob?.getCompleted()
                GlobalScope.launch {
                    readyDefinitions.send(definitions)
                }
            }
        }

        GlobalScope.launch(Dispatchers.IO) {
            for (def in readyDefinitions) {
                messagingClient.send("definitions", Definitions.toBytes(def), "lookupProvider")
            }
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