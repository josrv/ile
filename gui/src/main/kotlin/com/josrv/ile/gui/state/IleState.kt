package com.josrv.ile.gui.state

import com.josrv.ile.gui.Definition

data class IleState(
    val tokens: List<Token>,
    val selectedToken: Token,
    val lookedUpToken: Token,
    val definitions: List<Definition> = listOf()
)