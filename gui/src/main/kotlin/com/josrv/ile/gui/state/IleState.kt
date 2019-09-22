package com.josrv.ile.gui.state

import com.josrv.ile.gui.state.data.Definition
import com.josrv.ile.gui.state.data.Page
import com.josrv.ile.gui.state.data.Text
import com.josrv.ile.gui.state.data.Token

data class IleState(
    val text: Text,
    val page: Page,

    val selectedToken: Token,

    val lookedUpToken: Token,
    val definitions: List<Definition> = listOf(),
    val loadingDefinition: Boolean = false,

    val texts: List<Text> = emptyList(),

    val loadingFile: Boolean = false
) {
    companion object {

        fun initial(): IleState {
            val emptyText = Text.empty()
            return IleState(emptyText, emptyText.pages[0], Token.empty(), Token.empty())
        }
    }
}