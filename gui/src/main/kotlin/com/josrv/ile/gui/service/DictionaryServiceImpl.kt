package com.josrv.ile.gui.service

import com.josrv.ile.common.msg.Word
import com.josrv.ile.messaging.MessagingClient

class DictionaryServiceImpl(
    private val messagingClient: MessagingClient
) : DictionaryService {
    override fun lookup(word: com.josrv.ile.gui.state.data.Word) {
        //TODO extract dto conversion
        val message = Word(word.value, word.stemmed)
        val bytes = Word.toBytes(message)
        messagingClient.send("words", bytes, "guest")
    }
}