package com.josrv.ile.gui.component

import com.josrv.ile.gui.state.IleState
import javafx.scene.control.TextField

class IleDictionaryInput : TextField(), IleBlock<String> {
    override fun getStateSlice(state: IleState): String {
        return state.selectedToken.value
    }

    override fun redrawComponent(state: String) {
        text = state
    }
}