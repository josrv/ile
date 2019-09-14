package com.josrv.ile.common


interface DictionaryService {
    fun lookup(word: String): List<Definition>
}