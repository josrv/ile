package com.josrv.ile.common.msg

data class MessageConfig<T: Message>(
    val queue: String,
    val mapper: FromBytes<T>
)