package com.josrv.ile.gui.component

import com.josrv.ile.gui.state.IleState
import com.josrv.ile.gui.state.Token
import javafx.scene.control.Label
import javafx.scene.input.MouseEvent
import javafx.scene.text.Font
import javafx.scene.text.FontWeight

class IleLabel(
    private var localState: Token,
    clickHandler: (MouseEvent) -> Unit
) : Label(localState.value), IleBlock<Token> {

    private val normalFont = Font.font("Nimbus Mono PS", FontWeight.SEMI_BOLD, 16.0)
    private val selectedFont = Font.font("Nimbus Mono PS", FontWeight.BOLD, 16.0)

    init {
        setNormalStyle()
        setOnMouseClicked(clickHandler)
    }

    override fun redrawComponent(state: Token): Boolean {
        if (localState != state) {
            if (state.selected) {
                setSelectedStyle()
            } else {
                setNormalStyle()
            }

            localState = state.copy()
            return true
        }

        return false
    }

    override fun getStateSlice(state: IleState): Token {
        return state.tokens[localState.index]
    }

    private fun setNormalStyle() {
        this.font = normalFont
    }

    private fun setSelectedStyle() {
        this.font = selectedFont
    }
}