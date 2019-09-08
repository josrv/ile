package com.josrv.ile.gui.component

import com.josrv.ile.gui.state.IleState
import com.josrv.ile.gui.state.Text
import javafx.scene.control.ListView

class TextsList(
    override var localState: List<Text>,
    onItemSelected: (Text) -> Unit
) : ListView<Text>(), IleBlock<List<Text>> {
    init {

        selectionModel.selectedItemProperty().addListener { o, old, new ->
            onItemSelected(new)
        }

    }

    override fun getStateSlice(state: IleState) = state.texts

    override fun redrawComponent(state: List<Text>) {
        this.items.setAll(state)
    }

}