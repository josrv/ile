package com.josrv.ile.gui

import com.josrv.ile.common.DictionaryService
import com.josrv.ile.core.TextUtils
import com.josrv.ile.gui.state.IleState
import com.josrv.ile.gui.state.createStore
import com.josrv.ile.gui.state.sideeffect.DictionaryLookup
import com.josrv.ile.gui.state.sideeffect.LoadFile
import com.natpryce.konfig.Configuration
import org.koin.core.qualifier.named
import org.koin.dsl.module

val Module = { config: Configuration ->
    module(createdAtStart = true) {

        single() {
            val dictionaryServiceClass = Class.forName(config[dictionary.serviceClass])
            dictionaryServiceClass.getDeclaredConstructor().newInstance() as DictionaryService
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

        single() {
           IleState.initial()
        }

        single() {
            createStore(get(), get(named("dictionaryLookup")), get(named("loadFile")))
        }
    }
}