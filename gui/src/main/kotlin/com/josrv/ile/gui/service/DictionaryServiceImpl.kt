package com.josrv.ile.gui.service

import com.josrv.ile.gui.App
import com.josrv.ile.gui.state.data.Word
import com.josrv.ile.messaging.MessagingClient

class DictionaryServiceImpl(
    private val messagingClient: MessagingClient
) : DictionaryService {
    override fun lookup(word: Word) {
        //TODO extract dto conversion
        val message = com.josrv.ile.common.msg.Word(word.value, word.stemmed)
        val bytes = com.josrv.ile.common.msg.Word.toBytes(message)
        messagingClient.send("words", bytes, App.ID)
    }
}