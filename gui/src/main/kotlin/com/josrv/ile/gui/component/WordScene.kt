package com.josrv.ile.gui.component

import com.josrv.ile.gui.state.IleAction
import com.josrv.ile.gui.state.Store
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.input.KeyCode

class WordScene(
    store: Store,
    root: Parent
) : Scene(root) {
    init {
        setOnKeyPressed {
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