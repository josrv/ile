package com.josrv.ile.common.msg

interface Message

typealias FromBytes<M> = (ByteArray) -> M

typealias ToBytes<M> = (M) -> ByteArray
