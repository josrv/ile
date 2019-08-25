package com.josrv.ile.gui.state

sealed class IleAction {
    data class Select(val index: Int) : IleAction()
    data class Move(val forward: Boolean) : IleAction()
}