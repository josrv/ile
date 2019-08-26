package com.josrv.ile.gui.state

import com.freeletics.coredux.Reducer
import com.freeletics.coredux.createStore
import kotlinx.coroutines.GlobalScope

typealias Store = com.freeletics.coredux.Store<IleState, IleAction>

fun createStore(initialState: IleState): Store =
    GlobalScope.createStore(
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

