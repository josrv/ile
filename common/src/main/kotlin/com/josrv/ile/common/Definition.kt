package com.josrv.ile.common

data class Definition(
    val word: String,
    val pos: POS,
    val value: String
)

enum class POS(repr: String) {
    NOUN("n."), VERB("v."), ADJECTIVE("adj.")
}
