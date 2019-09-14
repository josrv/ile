package com.josrv.ile.gui.component

import com.josrv.ile.gui.state.*
import javafx.geometry.Insets
import javafx.scene.control.ScrollPane
import javafx.scene.input.MouseEvent
import javafx.scene.layout.FlowPane
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class WordPane(
    override val store: Store,
    override var localState: Page,
    hgap: Double, vgap: Double
) : ScrollPane(), IleHolder<Page> {

    private val flowPane = FlowPane(hgap, vgap)

    private val labelSelect: (Token) -> (MouseEvent) -> Unit = { token ->
        {
            dispatch(IleAction.Select(token.index))
            dispatch(IleAction.Lookup)
        }
    }

    init {
        content = flowPane
        isFitToWidth = true
        flowPane.padding = Insets(5.0, 5.0, 5.0, 5.0)

        flowPane.children.addAll(localState.tokens.map { token ->
            IleLabel(token, clickHandler = labelSelect(token))
        })

        vbarPolicy = ScrollBarPolicy.AS_NEEDED
        hbarPolicy = ScrollBarPolicy.NEVER
    }

    override fun getStateSlice(state: IleState) = state.page
    override fun children() = flowPane.children.asSequence().filterIsInstance<IleBlock<*>>()

    override fun redraw(state: IleState) {
        val newPage = getStateSlice(state)
        if (newPage.id != localState.id) {
            // whole page has to be redrawed
            localState = newPage
            //TODO coroutine here is not good
            GlobalScope.launch(Dispatchers.Main) {
                flowPane.children.clear()
                flowPane.children.addAll(localState.tokens.map { token ->
                    IleLabel(token, clickHandler = labelSelect(token))
                })
            }
        } else {
            // no need to redraw the whole page (e.g. update some labels)
            super.redraw(state)
        }
    }
}
