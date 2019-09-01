package com.josrv.ile.gui

data class Definition(
    val word: String,
    val pos: POS,
    val value: String
)

enum class POS(repr: String) {
    NOUN("n."), VERB("v."), ADJECTIVE("adj.")
}
