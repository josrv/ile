package com.josrv.ile.gui.state.sideeffect

import com.freeletics.coredux.SimpleSideEffect
import com.josrv.ile.common.DictionaryService
import com.josrv.ile.gui.state.IleAction
import com.josrv.ile.gui.state.IleState

fun DictionaryLookup(dictionaryService: DictionaryService) =
    SimpleSideEffect<IleState, IleAction>("dictionaryLookup") { state, action, logger, handler ->
        when (action) {
            is IleAction.Lookup -> handler {
                val selectedToken = state().selectedToken
                val definitions = dictionaryService.lookup(selectedToken.value)
                IleAction.LookupResult(definitions)
            }
            else -> null
        }
    }
