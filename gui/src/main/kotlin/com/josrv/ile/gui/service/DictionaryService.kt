package com.josrv.ile.gui.service

import com.josrv.ile.gui.state.data.Word

interface DictionaryService {
    fun lookup(word: Word)
}