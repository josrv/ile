package com.josrv.ile.gui.state

data class IleState(
    val text: Text,
    val page: Page,

    val selectedToken: Token,

    val lookedUpToken: Token,
    val definitions: List<Definition> = listOf(),

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