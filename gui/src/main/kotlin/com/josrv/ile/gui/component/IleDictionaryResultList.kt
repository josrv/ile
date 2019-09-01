package com.josrv.ile.gui.component

import com.josrv.ile.gui.Definition
import com.josrv.ile.gui.state.IleState
import javafx.scene.control.ListView

class IleDictionaryResultList : ListView<Definition>(), IleBlock<List<Definition>> {
    override fun getStateSlice(state: IleState): List<Definition> {
        return state.definitions
    }

    override fun redrawComponent(state: List<Definition>) {
        println("redrawing dictionary result list")
        items.setAll(state)
    }
}