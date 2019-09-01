package com.josrv.ile.gui

interface DictionaryService {
    fun lookup(word: String): List<Definition>
}