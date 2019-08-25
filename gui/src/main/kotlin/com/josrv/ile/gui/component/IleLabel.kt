package com.josrv.ile.gui.component

import com.josrv.ile.gui.Component
import com.josrv.ile.gui.state.State
import com.josrv.ile.gui.state.Token
import javafx.scene.control.Label
import javafx.scene.text.Font
import javafx.scene.text.FontWeight

class IleLabel(
    private var localState: Token
) : Label(localState.value), Component<Token> {
    override fun getStateSlice(state: State): Token {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val normalFont = Font.font("Nimbus Mono PS", FontWeight.SEMI_BOLD, 16.0)
    private val selectedFont = Font.font("Nimbus Mono PS", FontWeight.BOLD, 16.0)
    init {
        font = normalFont
    }

    override fun redraw(state: Token): Boolean {
        if (localState != state) {
            val font = if (state.selected) {
                selectedFont
            } else {
                normalFont
            }

            this.font = font

            localState = state.copy()
            return true
        }

        return false
    }
}