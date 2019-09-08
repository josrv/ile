package com.josrv.ile.gui.component

import com.josrv.ile.gui.state.IleAction
import com.josrv.ile.gui.state.Store
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.input.KeyCode
import javafx.stage.FileChooser
import javafx.stage.Stage

class WordScene(
    store: Store,
    stage: Stage,
    root: Parent
) : Scene(root, 1000.0, 500.0) {
    init {
        val fileChooser = FileChooser()
        setOnKeyPressed {

            if (it.isControlDown && it.code == KeyCode.O) {
//                store.dispatch(IleAction.OpenFile)
                val file = fileChooser.showOpenDialog(stage).toPath()
                store.dispatch(IleAction.FileOpened(file))
            }

            when (it.code) {
                KeyCode.J, KeyCode.RIGHT -> {
                    store.dispatch(IleAction.Move(true))
                    store.dispatch(IleAction.Lookup)
                }
                KeyCode.K, KeyCode.LEFT -> {
                    store.dispatch(IleAction.Move(false))
                    store.dispatch(IleAction.Lookup)
                }

                else -> {
                }
            }
        }
    }
}