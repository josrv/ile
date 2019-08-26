package com.josrv.ile.gui.component

import com.josrv.ile.gui.state.IleAction
import com.josrv.ile.gui.state.Store
import com.josrv.ile.gui.state.Token
import javafx.scene.layout.FlowPane

class WordPane(
    override val store: Store,
    tokens: Collection<Token>,
    hgap: Double, vgap: Double
) : FlowPane(hgap, vgap), IleHolder {

    init {
        children.addAll(tokens.map { token ->
            IleLabel(token) { dispatch(IleAction.Select(token.index)) }
        })
    }

    override fun children() = children.filterIsInstance<IleBlock<*>>()
}
