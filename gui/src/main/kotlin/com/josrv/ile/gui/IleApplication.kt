package com.josrv.ile.gui

import com.josrv.ile.gui.component.*
import com.josrv.ile.gui.state.*
import javafx.application.Application
import javafx.stage.Stage

class IleApplication : Application() {

    override fun start(primaryStage: Stage) {
        val initialState = IleState.initial()
        val store = createStore(initialState)

        val textsList = TextsList(initialState.texts, onItemSelected = {
            store.dispatch(IleAction.SelectText(it))
        })
        val wordPane = WordPane(store, initialState.page, 5.0, 1.0)
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