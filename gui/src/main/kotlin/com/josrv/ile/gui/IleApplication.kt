package com.josrv.ile.gui

import com.josrv.ile.core.TextUtils
import com.josrv.ile.gui.component.*
import com.josrv.ile.gui.state.*
import javafx.application.Application
import javafx.stage.Stage
import java.nio.file.Files
import java.nio.file.Paths

class IleApplication : Application() {

    override fun start(primaryStage: Stage) {
        val path = Paths.get("kaczynski.txt")
        val textString = Files.readString(path)
        val textUtils = TextUtils()
        val tokens = textUtils.tokenize(textString).mapIndexed { index, value ->
            Token(value, index == 0, index)
        }
        val page = Page(PageId.new(), 1, tokens)
        val text = Text(TextId.new(), path, "manifesto", listOf(page))
        val initialState = IleState(
            text,
            page,
            tokens.first(),
            tokens.first(),
            texts = listOf(text)
        )

        val store = createStore(initialState)

        val textsList = TextsList(initialState.texts, onItemSelected = {
            store.dispatch(IleAction.SelectText(it))
        })
        val wordPane = WordPane(store, page, 5.0, 1.0)
        val dictionaryPane = DictionaryPane(store, Pair(initialState.selectedToken, initialState.definitions))

        val workspace = IleWorkspace(store, initialState,
            textsList,
            wordPane,
            dictionaryPane
        )

        store.subscribe { state ->
            workspace.redraw(state)
        }

        val scene = WordScene(store, primaryStage, workspace)
        primaryStage.title = "Ile"
        primaryStage.scene = scene
        primaryStage.show()
    }


}

fun main(args: Array<String>) {
    Application.launch(IleApplication::class.java, *args)
}