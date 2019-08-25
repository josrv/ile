package com.josrv.ile.core

class TextUtils {
    fun tokenize(text: String): List<String> {
        return text.split(" ")
    }
}