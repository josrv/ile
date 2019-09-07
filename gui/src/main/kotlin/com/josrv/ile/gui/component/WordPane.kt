package com.josrv.ile.gui.component

import com.josrv.ile.gui.state.IleAction
import com.josrv.ile.gui.state.IleState
import com.josrv.ile.gui.state.Store
import com.josrv.ile.gui.state.Token
import javafx.scene.layout.FlowPane

class WordPane(
    override val store: Store,
    override var localState: Collection<Token>,
    hgap: Double, vgap: Double
) : FlowPane(hgap, vgap), IleHolder<Collection<Token>> {;

    init {
        children.addAll(localState.map { token ->
            IleLabel(token) {
                dispatch(IleAction.Select(token.index))
                dispatch(IleAction.Lookup)
            }
        })
    }

    override fun getStateSlice(state: IleState) = state.tokens
    override fun children() = children.asSequence().filterIsInstance<IleBlock<*>>()
}
