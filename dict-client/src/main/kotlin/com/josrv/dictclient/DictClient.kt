package com.josrv.dictclient

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

sealed class Command {
    abstract fun toProtocolRepr(): String

    class Define(private val word: String) : Command() {
        override fun toProtocolRepr() = "DEFINE $word"
    }
}

sealed class Response {

    data class DefinitionsFound(val n: Int) : Response()
    data class DatabaseName(private val name: String) : Response()
    object Unknown : Response()

    companion object {
        fun parse(text: String): Response {
            val code = text.substring(0, 3).toInt()

            return when (code) {
                150 -> {
                    val n = text[4].toInt()
                    DefinitionsFound(n)
                }
                151 -> {
                    val dbName = text.split(" ")[2]
                    DatabaseName(dbName)
                }

                else -> Unknown
            }
        }
    }
}

class DictClient {

    private val commandQueue: Channel<Command> = Channel()
    private val responseQueue: Channel<Response> = Channel()

    fun connect(host: String, port: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            Socket(host, port).use { socket ->
                BufferedReader(InputStreamReader(socket.getInputStream())).use { reader ->
                    PrintWriter(socket.getOutputStream(), true).use { writer ->
                        try {
                            val command = commandQueue.receive()
                            writer.println(command.toProtocolRepr())

                            var line = reader.readLine()
/*
                            val response = Response.parse(line)
                            when (response) {
                                is Response.DefinitionsFound -> {
                                    generateSequence(0) {i ->
                                        if (i == response.n) null else {
                                            val dbNameResponse = Response.parse(reader.readLine())

                                            if (dbNameResponse !is Response.DatabaseName) {

                                            }

                                        }


                                    }
                                }

                            }


 */

                        } catch (e: CancellationException) {

                        }

                    }
                }
            }
        }
    }


    suspend fun command(c: Command) {
        commandQueue.send(c)

    }
}

fun main(args: Array<String>) = runBlocking {
    val host = args.getOrElse(0) { "localhost" }
    val port = args.getOrElse(1) { "2628" }.toInt()

    val client = DictClient()
    client.connect(host, port)


}

