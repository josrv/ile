package com.josrv.ile.conduct

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

interface Block<in State: Any, StateSlice: Any> {
    fun shouldRedraw(state: State): Boolean = true

    fun redraw(state: State) {
        GlobalScope.launch(Dispatchers.Main) {
            val stateSlice = getStateSlice(state)
            redrawComponent(stateSlice)
        }
    }

    fun redrawComponent(state: StateSlice) {}

    fun getStateSlice(state: State): StateSlice
}