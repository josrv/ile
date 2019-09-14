package com.josrv.ile.gui

import com.josrv.ile.gui.component.*
import com.josrv.ile.gui.state.IleAction
import com.josrv.ile.gui.state.IleState
import com.josrv.ile.gui.state.Store
import com.natpryce.konfig.ConfigurationProperties
import com.natpryce.konfig.ConfigurationProperties.Companion.systemProperties
import com.natpryce.konfig.EnvironmentVariables
import com.natpryce.konfig.overriding
import javafx.application.Application
import javafx.stage.Stage
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

class IleApplication : Application() {

    override fun start(primaryStage: Stage) {

        val config = initConfig()

        val app = startKoin { modules(Module(config)) }

        val store = app.koin.get<Store>()
        val initialState = app.koin.get<IleState>()

        val textsList = TextsList(initialState.texts, onItemSelected = {
            store.dispatch(IleAction.SelectText(it))
        })
        val wordPane = WordPane(store, initialState.page, 5.0, 1.0)
        val dictionaryPane = DictionaryPane(store, Pair(initialState.selectedToken, initialState.definitions))

        val workspace = IleWorkspace(
            store, initialState,
            textsList,
            wordPane,
            dictionaryPane
        )

        store.subscribe { state ->
            workspace.redraw(state)
        }

        val scene = WordScene(store, primaryStage, workspace)
        primaryStage.title = "Ile"
        primaryStage.scene = scene
        primaryStage.show()
    }

    override fun stop() {
        stopKoin()
    }

    private fun initConfig() =
        systemProperties() overriding
                EnvironmentVariables() overriding
                ConfigurationProperties.fromResource("application.properties")

}

fun main(args: Array<String>) {
    Application.launch(IleApplication::class.java, *args)
}