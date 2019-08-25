package com.josrv.ile.gui.component

import com.josrv.ile.gui.Component
import com.josrv.ile.gui.state.IleAction
import com.josrv.ile.gui.state.State
import com.josrv.ile.gui.state.Store
import javafx.scene.control.Label
import javafx.scene.layout.FlowPane
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WordPane(
    hgap: Double, vgap: Double
) : FlowPane(hgap, vgap), Component<State> {
    override fun getStateSlice(state: State): State {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun redraw(state: State): Boolean {
        GlobalScope.launch(Dispatchers.Main) {
            children
                .filterIsInstance<IleLabel>()
                .forEachIndexed() { index, label ->
                    val token = state.tokens[index]

                    if (label.redraw(token)) {
                        children[index] = label
                    }
                }
        }

        return false
    }
}