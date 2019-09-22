package com.josrv.ile.gui.state.sideeffect

import com.freeletics.coredux.SideEffectLogger
import com.freeletics.coredux.StateAccessor
import com.josrv.ile.core.TextUtils
import com.josrv.ile.gui.state.IleAction
import com.josrv.ile.gui.state.IleState
import com.josrv.ile.gui.state.SideEffect
import com.josrv.ile.gui.state.data.Page
import com.josrv.ile.gui.state.data.Text
import com.josrv.ile.gui.state.data.Token
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.launch
import java.nio.file.Files

val LoadFile: (TextUtils) -> SideEffect = { textUtils ->
    object : SideEffect {
        override val name = "lookup"

        override fun CoroutineScope.start(
            input: ReceiveChannel<IleAction>,
            stateAccessor: StateAccessor<IleState>,
            output: SendChannel<IleAction>,
            logger: SideEffectLogger
        ): Job = launch(Dispatchers.IO) {
            for (action in input) {
                if (action is IleAction.FileOpened) {
                    val textContent = Files.readString(action.file)
                    //TODO chunk into separate pages
                    val page = Page.new(
                        num = 1,
                        tokens = textUtils.tokenize(textContent).mapIndexed { index, s ->
                            Token(
                                value = s,
                                selected = false,
                                index = index
                            )
                        }
                    )
                    val newText = Text.new(action.file, action.file.fileName.toString(), listOf(page))
                    output.send(IleAction.FileLoaded(action.file, newText))
                    output.send(IleAction.SelectText(newText))
                }
            }
        }
    }
}