package com.josrv.ile.gui.state

import com.josrv.ile.gui.Definition

sealed class IleAction {
    data class Select(val index: Int) : IleAction()
    data class Move(val forward: Boolean) : IleAction()

    object Lookup : IleAction()
    data class LookupResult(val definitions: List<Definition>) : IleAction()
}
