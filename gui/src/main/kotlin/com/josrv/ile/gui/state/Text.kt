package com.josrv.ile.gui.state

import com.josrv.ile.gui.TextId
import java.nio.file.Path

data class Text(
    val id: TextId,
    val file: Path,
    val pages: List<Page>
)

data class Page(
    val num: Int,
    val tokens: List<Token>
)

