package com.josrv.ile.gui.component

import com.josrv.ile.gui.state.Definition
import com.josrv.ile.gui.state.IleState
import com.josrv.ile.gui.state.Store
import com.josrv.ile.gui.state.Token
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox

class DictionaryPane(
    override val store: Store,
    override var localState: Pair<Token, List<Definition>>
) : VBox(), IleHolder<Pair<Token, List<Definition>>> {
    init {
        children.add(IleDictionaryInput(localState = localState.first.value))
        children.add(IleDictionaryResultList(localState = localState.second).apply {
            setVgrow(this, Priority.ALWAYS)
        })
    }

    override fun getStateSlice(state: IleState) = Pair(state.lookedUpToken, state.definitions)
    override fun children() = children.asSequence().filterIsInstance<IleBlock<*>>()
}
