package com.josrv.ile.gui

import com.josrv.ile.core.TextUtils
import com.josrv.ile.gui.component.IleLabel
import com.josrv.ile.gui.component.WordPane
import com.josrv.ile.gui.component.WordScene
import com.josrv.ile.gui.state.State
import com.josrv.ile.gui.state.Token
import com.josrv.ile.gui.state.createStore
import javafx.application.Application
import javafx.stage.Stage
import java.nio.file.Files
import java.nio.file.Paths


class IleApplication : Application() {

    override fun start(primaryStage: Stage) {
        val text = Files.readString(Paths.get("text.txt")).replace("\n", "")
        val textUtils = TextUtils()
        val tokens = textUtils.tokenize(text).mapIndexed { index, value ->
            Token(value, index == 0, index)
        }
        val initialState = State(tokens, tokens.first())

        val store = createStore(initialState)

        val pane = WordPane(store, 5.0, 1.0)
        pane.children.addAll(tokens.map {
            IleLabel(it)
        })
        store.subscribe { state ->
            pane.redraw(state)
        }
        // Creating a scene object
        val scene = WordScene(store, pane)
        // Adding the title to the window (primaryStage)
        primaryStage.title = "Ile"
        primaryStage.scene = scene
        // Show the window(primaryStage)
        primaryStage.show()
    }


}

fun main(args: Array<String>) {
    Application.launch(IleApplication::class.java, *args)
}