package com.josrv.ile.gui.state.sideeffect

import com.freeletics.coredux.SideEffect
import com.freeletics.coredux.SideEffectLogger
import com.freeletics.coredux.StateAccessor
import com.josrv.ile.core.TextUtils
import com.josrv.ile.gui.state.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.launch
import java.nio.file.Files

fun LoadFile(textUtils: TextUtils) = object : SideEffect<IleState, IleAction> {
    override val name = "lookup"

    override fun CoroutineScope.start(
        input: ReceiveChannel<IleAction>,
        stateAccessor: StateAccessor<IleState>,
        output: SendChannel<IleAction>,
        logger: SideEffectLogger
    ): Job = launch {
        for (action in input) {
            if (action is IleAction.FileOpened) {
                launch(Dispatchers.IO) {
                    val textContent = Files.readString(action.file)
                    val page = Page.new(
                        num = 1,
                        tokens = textUtils.tokenize(textContent).mapIndexed { index, s -> Token(s, false, index) }
                    )
                    val newText = Text(TextId.new(), action.file, action.file.fileName.toString(), listOf(page))
                    output.send(IleAction.FileLoaded(action.file, newText))
                    output.send(IleAction.SelectText(newText))
                }
            }
        }
    }
}