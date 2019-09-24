package com.josrv.ile.gui.state.sideeffect

import com.freeletics.coredux.SimpleSideEffect
import com.josrv.ile.gui.service.DictionaryService
import com.josrv.ile.gui.state.IleAction
import com.josrv.ile.gui.state.IleState
import com.josrv.ile.gui.state.data.Word

fun DictionaryLookup(dictionaryService: DictionaryService) =
    SimpleSideEffect<IleState, IleAction>("dictionaryLookup") { state, action, logger, handler ->
        when (action) {
            is IleAction.Lookup -> handler {
                val selectedToken = state().selectedToken
                dictionaryService.lookup(Word(selectedToken.value))

                IleAction.LoadingDefinition
            }
            else -> null
        }
    }

