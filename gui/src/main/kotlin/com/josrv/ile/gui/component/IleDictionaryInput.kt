package com.josrv.ile.gui.component

import com.josrv.ile.gui.state.IleState
import javafx.scene.control.TextField

class IleDictionaryInput(
    override var localState: String
) : TextField(), IleBlock<String> {

    override fun getStateSlice(state: IleState) = state.selectedToken.value
    override fun redrawComponent(state: String) {
        text = state
    }
}