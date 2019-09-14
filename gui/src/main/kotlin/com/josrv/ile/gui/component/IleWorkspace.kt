package com.josrv.ile.gui.component

import com.freeletics.coredux.Store
import com.josrv.ile.gui.DraggingResizer
import com.josrv.ile.gui.state.IleAction
import com.josrv.ile.gui.state.IleState
import javafx.scene.Node
import javafx.scene.layout.ColumnConstraints
import javafx.scene.layout.GridPane
import javafx.scene.layout.Priority

class IleWorkspace(
    override val store: Store<IleState, IleAction>,
    override var localState: IleState,
    vararg nodes: Node
) : GridPane(), IleHolder<IleState> {


    init {
        hgap = 5.0
        val resizer = DraggingResizer(nodes.size, { scene.width })
        columnConstraints.addAll(
            List(nodes.size) { ColumnConstraints().apply { percentWidth = 100.0 / nodes.size } }
        )

        addRow(0, *nodes)
        nodes.forEach {
            setVgrow(it, Priority.ALWAYS)
        }

        setOnMousePressed { event ->
            resizer.startDrag(event.sceneX)
        }

        setOnMouseReleased {
            resizer.stopDrag()
        }

        setOnMouseDragged {
            resizer.onDrag(it.sceneX) { (leftPanel, leftRatio), (rightPanel, rightRatio) ->
                columnConstraints[leftPanel].percentWidth = leftRatio * 100.0
                columnConstraints[rightPanel].percentWidth = rightRatio * 100.0
            }
        }
    }


    override fun getStateSlice(state: IleState) = state

    override fun children() = children.asSequence().filterIsInstance<IleBlock<*>>()
}