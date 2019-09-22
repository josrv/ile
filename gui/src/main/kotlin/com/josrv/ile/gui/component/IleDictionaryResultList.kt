package com.josrv.ile.gui.component

import com.josrv.ile.gui.state.data.Definition
import com.josrv.ile.gui.state.IleState
import javafx.scene.control.ListView

class IleDictionaryResultList(
    override var localState: List<Definition>
) : ListView<Definition>(), IleBlock<List<Definition>> {
    override fun getStateSlice(state: IleState) = state.definitions
    override fun redrawComponent(state: List<Definition>) {
        items.setAll(state)
    }
}