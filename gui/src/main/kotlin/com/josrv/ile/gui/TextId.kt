package com.josrv.ile.gui

import java.util.*

inline class TextId(val id: String) {
    companion object {
        fun new() = TextId(UUID.randomUUID().toString())
    }
}