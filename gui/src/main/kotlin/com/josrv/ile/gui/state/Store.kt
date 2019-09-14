package com.josrv.ile.gui.state

import com.freeletics.coredux.Reducer
import com.freeletics.coredux.createStore
import kotlinx.coroutines.GlobalScope

typealias Store = com.freeletics.coredux.Store<IleState, IleAction>
typealias SideEffect = com.freeletics.coredux.SideEffect<IleState, IleAction>

fun createStore(
    initialState: IleState,
    dictionaryLookup: SideEffect,
    loadFile: SideEffect
): Store =
    GlobalScope.createStore(
        name = "Ile",
        initialState = initialState,
        sideEffects = listOf(dictionaryLookup, loadFile),
        reducer = { currentState, newAction ->
            when (newAction) {
                is IleAction.Select -> SelectReducer(currentState, newAction)
                is IleAction.Move -> MoveReducer(currentState, newAction)
                is IleAction.LookupResult -> LookupResultReducer(currentState, newAction)
                is IleAction.FileOpened -> FileOpenedReducer(currentState, newAction)
                is IleAction.FileLoaded -> FileLoadedReducer(currentState, newAction)
                is IleAction.SelectText -> SelectTextReducer(currentState, newAction)
                else -> NoOpReducer(currentState, newAction)
            }
        }
    )

val NoOpReducer: Reducer<IleState, in IleAction> = { state, _ -> state }

val SelectReducer: Reducer<IleState, IleAction.Select> =
    { currentState, action ->
        val token = currentState.page.tokens[action.index].copy(selected = true)
        val newTokens = currentState.page.tokens.map {
            it.copy(selected = it.index == action.index)
        }

        currentState.copy(page = currentState.page.copy(tokens = newTokens), selectedToken = token)
    }

val MoveReducer: Reducer<IleState, IleAction.Move> =
    { currentState, action ->
        val (_, page, selectedToken) = currentState
        val currentIndex = selectedToken.index
        val newIndex = if (action.forward) {
            if (currentIndex == page.tokens.size - 1) currentIndex else currentIndex + 1
        } else {
            if (currentIndex == 0) currentIndex else currentIndex - 1
        }

        SelectReducer(currentState, IleAction.Select(newIndex))
    }

val LookupResultReducer: Reducer<IleState, IleAction.LookupResult> =
    { currentState, action ->
        currentState.copy(definitions = action.definitions, lookedUpToken = currentState.selectedToken)
    }

val FileOpenedReducer: Reducer<IleState, IleAction.FileOpened> =
    { currentState, _ ->
        currentState.copy(loadingFile = true)
    }

val FileLoadedReducer: Reducer<IleState, IleAction.FileLoaded> =
    { currentState, action ->
        currentState.copy(loadingFile = false, texts = currentState.texts + action.text)
    }

val SelectTextReducer: Reducer<IleState, IleAction.SelectText> =
    { currentState, action ->
        currentState.copy(text = action.text, page = action.text.pages[0])
    }
