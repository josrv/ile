package com.josrv.ile.gui.component

import com.josrv.ile.gui.state.Store
import javafx.scene.layout.VBox

class DictionaryPane(
    override val store: Store
) : VBox(), IleHolder {

    override fun children() = children.filterIsInstance<IleBlock<*>>()

    init {
        children.add(IleDictionaryInput())
        children.add(IleDictionaryResultList())
    }
}