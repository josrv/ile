package com.josrv.ile.gui.state

import com.freeletics.coredux.*
import com.josrv.ile.gui.DictionaryService
import com.josrv.ile.gui.StubDictionaryService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.launch

typealias Store = com.freeletics.coredux.Store<IleState, IleAction>

fun createStore(initialState: IleState): Store =
    GlobalScope.createStore(
        name = "Ile",
        initialState = initialState,
        sideEffects = listOf(lookup),
        reducer = { currentState, newAction ->
            when (newAction) {
                is IleAction.Select -> {
                    SelectReducer(currentState, newAction)
                }
                is IleAction.Move -> {
                    MoveReducer(currentState, newAction)
                }
                is IleAction.LookupResult -> {
                    LookupResultReducer(currentState, newAction)
                }
                else -> {
                    NoOpReducer(currentState, newAction)
                }
            }
        }
    )

val NoOpReducer: Reducer<IleState, IleAction> = { state, _ -> state }

val SelectReducer: Reducer<IleState, IleAction.Select> =
    { currentState, action ->
        val token = currentState.tokens[action.index].copy(selected = true)
        val newTokens = currentState.tokens.map {
            it.copy(selected = it.index == action.index)
        }

        currentState.copy(tokens = newTokens.toList(), selectedToken = token)
    }

val MoveReducer: Reducer<IleState, IleAction.Move> =
    { currentState, action ->
        val (tokens, selectedToken) = currentState
        val currentIndex = selectedToken.index
        val newIndex = if (action.forward) {
            if (currentIndex == tokens.size - 1) currentIndex else currentIndex + 1
        } else {
            if (currentIndex == 0) currentIndex else currentIndex - 1
        }

        SelectReducer(currentState, IleAction.Select(newIndex))
    }

val LookupResultReducer: Reducer<IleState, IleAction.LookupResult> =
    { currentState, action ->
        currentState.copy(definitions = action.definitions)
    }


val dictionaryService: DictionaryService = StubDictionaryService()

val lookup = object : SideEffect<IleState, IleAction> {
    override val name = "lookup"

    override fun CoroutineScope.start(
        input: ReceiveChannel<IleAction>,
        stateAccessor: StateAccessor<IleState>,
        output: SendChannel<IleAction>,
        logger: SideEffectLogger
    ): Job = GlobalScope.launch {
        for (inputAction in input) {
            if (inputAction is IleAction.Lookup) {
                launch {
                    val selectedToken = stateAccessor().selectedToken
                    val definitions = dictionaryService.lookup(selectedToken.value)
                    output.send(IleAction.LookupResult(definitions))
                }
            }
        }
    }
}
