package com.josrv.ile.gui.component

import com.josrv.ile.gui.state.*
import javafx.scene.layout.FlowPane

class WordPane(
    override val store: Store,
    override var localState: Page,
    hgap: Double, vgap: Double
) : FlowPane(hgap, vgap), IleHolder<Page> {

    init {
        children.addAll(localState.tokens.map { token ->
            IleLabel(token) {
                dispatch(IleAction.Select(token.index))
                dispatch(IleAction.Lookup)
            }
        })
    }

    override fun getStateSlice(state: IleState) = state.page
    override fun children() = children.asSequence().filterIsInstance<IleBlock<*>>()
}
