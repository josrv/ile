package com.josrv.ile.gui

import com.josrv.ile.core.TextUtils
import com.josrv.ile.gui.service.DictionaryService
import com.josrv.ile.gui.service.DictionaryServiceImpl
import com.josrv.ile.gui.state.IleState
import com.josrv.ile.gui.state.createStore
import com.josrv.ile.gui.state.sideeffect.DictionaryLookup
import com.josrv.ile.gui.state.sideeffect.LoadFile
import com.josrv.ile.messaging.MessagingClient
import com.natpryce.konfig.Configuration
import org.koin.core.qualifier.named
import org.koin.dsl.module

val Module = { config: Configuration ->
    module(createdAtStart = true) {

        single() {
            val messagingClient = config[messaging.clientClass].getDeclaredConstructor().newInstance() as MessagingClient

            messagingClient.connect(config[messaging.uri])

            messagingClient
        }

        single() {
            TextUtils()
        }

        single(named("dictionaryLookup")) {
            DictionaryLookup(get())
        }

        single(named("loadFile")) {
            LoadFile(get())
        }

        single<DictionaryService>() {
            DictionaryServiceImpl(get())
        }

        single() {
            IleState.initial()
        }

        single() {
            createStore(get(), get(named("dictionaryLookup")), get(named("loadFile")), get())
        }
    }
}
