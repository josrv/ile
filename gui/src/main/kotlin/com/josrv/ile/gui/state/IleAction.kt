package com.josrv.ile.gui.state

import com.josrv.ile.common.Definition
import java.nio.file.Path

sealed class IleAction {
    object Initialize : IleAction()

    data class Select(val index: Int) : IleAction()
    data class Move(val forward: Boolean) : IleAction()

    object OpenFile : IleAction()
    data class FileOpened(val file: Path) : IleAction()
    data class FileLoaded(val file: Path, val text: Text) : IleAction()

    data class SelectText(val text: Text) : IleAction()
    data class TextSelected(val textId: TextId) : IleAction()

    data class LookupResult(val definitions: List<Definition>) : IleAction()
    object Lookup : IleAction()
}
