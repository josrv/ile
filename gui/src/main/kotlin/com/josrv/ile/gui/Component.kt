package com.josrv.ile.gui

import com.josrv.ile.gui.state.State

interface Component<StateSlice> {
    fun redraw(state: StateSlice): Boolean
    fun getStateSlice(state: State): StateSlice
}