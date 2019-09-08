package com.josrv.ile.gui

import com.josrv.ile.gui.state.Definition

interface DictionaryService {
    fun lookup(word: String): List<Definition>
}