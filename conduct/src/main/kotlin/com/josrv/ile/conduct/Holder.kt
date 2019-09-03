package com.josrv.ile.conduct

import com.freeletics.coredux.Store

interface Holder<State : Any, StateSlice: Any, Action : Any> : Block<State, StateSlice> {
    val store: Store<State, Action>

    fun dispatch(action: Action) {
        store.dispatch(action)
    }

    fun children(): List<Block<State, *>>

    override fun redraw(state: State) {
        children()
            .filter {
                it.shouldRedraw(state)
            }
            .forEach {
                it.redraw(state)
            }
    }
}