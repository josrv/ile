package com.josrv.ile.gui.state

data class Token(
    val value: String,
    val selected: Boolean,
    val index: Int
) {
    companion object {
        fun empty() = Token("EMPTY", false, -1)
    }
}
