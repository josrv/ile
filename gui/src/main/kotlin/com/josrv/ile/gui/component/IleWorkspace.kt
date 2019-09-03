package com.josrv.ile.gui.component

import com.freeletics.coredux.Store
import com.josrv.ile.gui.state.IleAction
import com.josrv.ile.gui.state.IleState
import javafx.scene.layout.TilePane

class IleWorkspace(
    override val store: Store<IleState, IleAction>,
    override var localState: IleState
) : TilePane(), IleHolder<IleState> {
    override fun getStateSlice(state: IleState) = state

    override fun children() = children.filterIsInstance<IleBlock<*>>()
}