package me.tomasan7.jecnadesktop

import javafx.application.Platform
import javafx.event.EventHandler
import javafx.scene.image.Image
import javafx.stage.Stage
import me.tomasan7.jecnaapi.repository.*
import me.tomasan7.jecnaapi.web.JecnaWebClient
import me.tomasan7.jecnadesktop.ui.JDScene
import me.tomasan7.jecnadesktop.ui.SceneManager
import me.tomasan7.jecnadesktop.ui.scene.LoadingScene
import me.tomasan7.jecnadesktop.ui.scene.LoginScene
import me.tomasan7.jecnadesktop.ui.scene.MainScene
import tornadofx.App
import kotlin.system.exitProcess

/**
 * Main class of the application.
 */
class JecnaDesktop : App()
{
    var primaryStage: Stage? = null
        private set

    var jecnaWebClient: JecnaWebClient? = null
        private set

    var gradesRepository: GradesRepository? = null
        private set

    var attendancesRepository: AttendancesRepository? = null
        private set

    var timetableRepository: TimetableRepository? = null
        private set

    var sceneManager: SceneManager? = null
        private set

    override fun start(stage: Stage)
    {
        this.primaryStage = stage

        stage.title = "Ječná Desktop"
        stage.icons.add(Image("/ui/icon/logo.png"))
        /* For some reason the process doesn't exit by itself, so I kill it when the stage is closed. */
        stage.onCloseRequest = EventHandler {
            Platform.exit()
            exitProcess(0)
        }

        sceneManager = SceneManager(stage)
        registerPages()
        sceneManager!!.switchToScene(JDScene.LOGIN)

        stage.show()
    }

    private fun registerPages()
    {
        sceneManager!!.addScene(JDScene.LOGIN, LoginScene(this))
        sceneManager!!.addScene(JDScene.LOADING, LoadingScene())
        sceneManager!!.addScene(JDScene.MAIN, MainScene(this))
    }

    fun initDataAccess(jecnaWebClient: JecnaWebClient?)
    {
        checkNotNull(jecnaWebClient) { "Data access has already been initialized." }

        this.jecnaWebClient = jecnaWebClient
        gradesRepository = WebGradesRepository(jecnaWebClient)
        attendancesRepository = WebAttendancesRepository(jecnaWebClient)
        timetableRepository = WebTimetableRepository(jecnaWebClient)
    }
}