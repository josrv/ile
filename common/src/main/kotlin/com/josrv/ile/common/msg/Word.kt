package com.josrv.ile.common.msg

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoBuf

@Serializable
data class Word(
    val value: String,
    val stemmed: Boolean = true
) : Message {

    companion object {
        val protobuf = ProtoBuf()

        val toBytes: ToBytes<Word> = {
            protobuf.dump(serializer(), it)
        }

        private val fromBytes: FromBytes<Word> = {
            protobuf.load(serializer(), it)
        }
        val config = MessageConfig("lookup", fromBytes)
    }
}
