package com.josrv.ile.gui.state

import java.nio.file.Path
import java.util.*

data class Text(
    val id: TextId,
    val file: Path,
    val title: String,
    val pages: List<Page>
) {
    companion object {
        fun new(file: Path, title: String, pages: List<Page>) = Text(TextId.new(), file, title, pages)
        fun empty() = Text(TextId.empty(), Path.of("nonexistent"), "Empty", listOf(Page.empty()))
    }
}

inline class TextId(val id: String) {
    companion object {
        fun new() = TextId(UUID.randomUUID().toString())
        fun empty() = TextId("EMPTY")
    }
}

data class Page(
    val id: PageId,
    val num: Int,
    val tokens: List<Token>
) {
    companion object {
        fun new(num: Int, tokens: List<Token>) = Page(PageId.new(), num, tokens)
        fun empty() = Page(PageId.empty(), 1, emptyList())
    }
}

inline class PageId(val id: String) {
    companion object {
        fun new() = PageId(UUID.randomUUID().toString())
        fun empty() = PageId("EMPTY")
    }
}
