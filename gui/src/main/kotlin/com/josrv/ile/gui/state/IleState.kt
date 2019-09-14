package com.josrv.ile.gui.state

data class IleState(
    val text: Text,
    val page: Page,

    val selectedToken: Token,
    val lookedUpToken: Token,
    val definitions: List<Definition> = listOf(),

    val texts: List<Text> = emptyList(),

    val openingFile: Boolean = false,
    val loadingFile: Boolean = false
)