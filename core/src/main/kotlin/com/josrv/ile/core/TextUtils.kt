package com.josrv.ile.core

class TextUtils {
    fun tokenize(text: String): List<String> {
        return text.replace("\n", "").split(" ")
    }
}