package com.josrv.ile.gui.state

import com.freeletics.coredux.createStore
import kotlinx.coroutines.GlobalScope


typealias Store = com.freeletics.coredux.Store<State, IleAction>

fun createStore(initialState: State) =
    GlobalScope.createStore<State, IleAction>(
        "Ile",
        initialState,
        reducer = { currentState, newAction ->
            when (newAction) {
                is IleAction.Select -> {
                    SelectReducer(currentState, newAction)
                }
                is IleAction.Move -> {
                    MoveReducer(currentState, newAction)
                }
            }
        }
    )

val SelectReducer: (State, IleAction.Select) -> State = { currentState, action ->
    val tokenIndex = currentState.tokens.indexOfFirst { it.index == action.index }
    val token = currentState.tokens[tokenIndex].copy(selected = true)
    val newTokens = currentState.tokens.map {
        it.copy(selected = it.index == tokenIndex)
    }

    currentState.copy(tokens = newTokens.toList(), selectedToken = token)
}

val MoveReducer: (State, IleAction.Move) -> State = { currentState, action ->
    val (tokens, selectedToken) = currentState
    val currentIndex = selectedToken.index
    val newIndex = if (action.forward) {
        if (currentIndex == tokens.size - 1) currentIndex else currentIndex + 1
    } else {
        if (currentIndex == 0) currentIndex else currentIndex - 1
    }

    SelectReducer(currentState, IleAction.Select(newIndex))
}

