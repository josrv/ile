package com.josrv.ile.gui.state

import com.freeletics.coredux.*
import com.josrv.ile.core.TextUtils
import com.josrv.ile.gui.DictionaryService
import com.josrv.ile.gui.StubDictionaryService
import com.josrv.ile.gui.TextId
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import java.nio.file.Files

typealias Store = com.freeletics.coredux.Store<IleState, IleAction>

fun createStore(initialState: IleState): Store =
    GlobalScope.createStore(
        name = "Ile",
        initialState = initialState,
        sideEffects = listOf(lookup, loadFile),
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
                is IleAction.OpenFile -> {
                    OpenFileReducer(currentState, newAction)
                }
                is IleAction.FileOpened -> {
                    FileOpenedReducer(currentState, newAction)
                }
                is IleAction.FileLoaded -> {
                    FileLoadedReducer(currentState, newAction)
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

val OpenFileReducer: Reducer<IleState, IleAction.OpenFile> =
    { currentState, action ->
        currentState.copy(openingFile = true)
    }

val FileOpenedReducer: Reducer<IleState, IleAction.FileOpened> =
    { currentState, action ->
        currentState.copy(openingFile = false, loadingFile = true)
    }

val FileLoadedReducer: Reducer<IleState, IleAction.FileLoaded> =
    { currentState, action ->
        val newText = Text(TextId.new(), action.file, action.pages)
        currentState.copy(loadingFile = false, texts = currentState.texts + newText)
    }


val dictionaryService: DictionaryService = StubDictionaryService()

val lookup = SimpleSideEffect<IleState, IleAction>("lookup") { state, action, logger, handler ->
    when (action) {
        is IleAction.Lookup -> handler {
            val selectedToken = state().selectedToken
            val definitions = dictionaryService.lookup(selectedToken.value)
            IleAction.LookupResult(definitions)
        }
        else -> null
    }
}

val textUtils = TextUtils()

val loadFile = object : SideEffect<IleState, IleAction> {
    override val name = "lookup"

    override fun CoroutineScope.start(
        input: ReceiveChannel<IleAction>,
        stateAccessor: StateAccessor<IleState>,
        output: SendChannel<IleAction>,
        logger: SideEffectLogger
    ): Job = launch {
        for (action in input) {
            when (action) {
                is IleAction.FileOpened -> launch(Dispatchers.IO) {
                    val text = Files.readString(action.file)
                    val page = Page(
                        num = 1,
                        tokens = textUtils.tokenize(text).mapIndexed { index, s -> Token(s, false, index) }
                    )
                    output.send(IleAction.FileLoaded(action.file, listOf(page)))
                }
                else -> {
                }
            }
        }
    }
}
