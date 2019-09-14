package com.josrv.ile.conduct

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

interface Block<in State: Any, StateSlice: Any> {

    var localState: StateSlice

    fun shouldRedraw(state: State) = getStateSlice(state) != localState

    fun redraw(state: State) {
        localState = getStateSlice(state)
        GlobalScope.launch(Dispatchers.Main) {
            logger.debug { "redrawing ${this@Block}" }

            val stateSlice = getStateSlice(state)
            redrawComponent(stateSlice)

        }
    }

    fun redrawComponent(state: StateSlice) {}

    fun getStateSlice(state: State): StateSlice
}