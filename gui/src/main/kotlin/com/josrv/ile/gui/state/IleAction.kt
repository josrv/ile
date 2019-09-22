package com.josrv.ile.gui.state

import com.josrv.ile.gui.state.data.Definition
import com.josrv.ile.gui.state.data.Text
import java.nio.file.Path

sealed class IleAction {
    data class Select(val index: Int) : IleAction()
    data class Move(val forward: Boolean) : IleAction()

    data class FileOpened(val file: Path) : IleAction()
    data class FileLoaded(val file: Path, val text: Text) : IleAction()

    data class SelectText(val text: Text) : IleAction()

    object Lookup : IleAction()
    object LoadingDefinition : IleAction()
    data class LookupResult(val definitions: List<Definition>) : IleAction()
}
