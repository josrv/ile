package com.josrv.ile.gui.component

import com.josrv.ile.gui.state.IleState
import com.josrv.ile.gui.state.Text
import javafx.scene.control.ListCell
import javafx.scene.control.ListView

class TextsList(
    override var localState: List<Text>,
    onItemSelected: (Text) -> Unit
) : ListView<Text>(), IleBlock<List<Text>> {
    init {
        selectionModel.selectedItemProperty().addListener { o, old, new ->
            onItemSelected(new)
        }

        setCellFactory {
            object : ListCell<Text>() {
                override fun updateItem(item: Text?, empty: Boolean) {
                    super.updateItem(item, empty)
                    text = if (empty) null else item?.title
                }
            }
        }

        redrawComponent(localState)
    }

    override fun getStateSlice(state: IleState) = state.texts

    override fun redrawComponent(state: List<Text>) {
        this.items.setAll(state)
    }

}