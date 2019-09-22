package com.josrv.ile.common.msg

import com.josrv.ile.common.POS
import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoBuf

@Serializable
data class Definitions(
    val value: List<Definition> = emptyList()
) : Message {

    companion object {
        //TODO extract
        private val protobuf = ProtoBuf()

        private val fromBytes: FromBytes<Definitions> = {
            protobuf.load(serializer(), it)
        }

        val toBytes: ToBytes<Definitions> = {
            protobuf.dump(serializer(), it)
        }

        val config = MessageConfig("lookup-result", fromBytes)
    }
}

@Serializable
data class Definition(
    val word: String,
    val pos: POS,
    val value: String
)
