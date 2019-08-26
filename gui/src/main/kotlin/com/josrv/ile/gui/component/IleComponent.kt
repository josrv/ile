package com.josrv.ile.gui.component

import com.josrv.ile.conduct.Block
import com.josrv.ile.conduct.Holder
import com.josrv.ile.gui.state.IleAction
import com.josrv.ile.gui.state.IleState

typealias IleBlock<StateSlice> = Block<IleState, StateSlice>
typealias IleHolder = Holder<IleState, IleAction>
