package me.tomasan7.jecnadesktop.ui.controller

import io.github.palexdev.materialfx.controls.MFXPasswordField
import io.github.palexdev.materialfx.controls.MFXTextField
import javafx.application.Platform
import javafx.beans.value.ChangeListener
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.tomasan7.jecnadesktop.JecnaDesktop
import me.tomasan7.jecnadesktop.ui.JDScene
import me.tomasan7.jecnadesktop.util.AuthStore
import me.tomasan7.jecnadesktop.web.Auth
import me.tomasan7.jecnadesktop.web.JecnaWebClient
import java.net.URL
import java.util.*

class LoginPageController(private val jecnaDesktop: JecnaDesktop) : Initializable
{
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    @FXML
    private lateinit var userInput: MFXTextField

    @FXML
    private lateinit var passwordInput: MFXPasswordField

    @FXML
    private lateinit var loginBtn: Button

    override fun initialize(location: URL, resources: ResourceBundle?)
    {
        if (AuthStore.isSaved)
        {
            val auth = AuthStore.load()

            if (auth != null)
            {
                val jecnaWebClient = JecnaWebClient(auth)

                jecnaDesktop.sceneManager!!.switchToScene(JDScene.LOADING)

                coroutineScope.launch {

                    if (jecnaWebClient.login())
                        continueToMain(jecnaWebClient)
                    else
                    {
                        AuthStore.delete()
                        Platform.runLater{ jecnaDesktop.sceneManager!!.switchToScene(JDScene.LOGIN) }
                    }
                }
            }
            else
                AuthStore.delete()
        }

        /* Disables/enables the login button based on whether both the fields are filled or not.  */
        val changeListener = ChangeListener<String> { _, _, _ -> loginBtn.isDisable = !canLogin() }

        passwordInput.textProperty().addListener(changeListener)
        userInput.textProperty().addListener(changeListener)

        passwordInput.onAction = EventHandler { if (canLogin()) onLogin() }
        loginBtn.onMouseClicked = EventHandler { if (canLogin()) onLogin() }
    }

    /**
     * @return Whether the user can hit login or not. User can log in, when neither field is blank.
     */
    private fun canLogin(): Boolean
    {
        /* If neither is blank. */
        return passwordInput.text.isNotBlank() && userInput.text.isNotBlank()
    }

    /**
     * Run when you either press the login button or hit enter while the password field is focused.
     */
    private fun onLogin()
    {
        val auth = try
        {
            Auth(userInput.text, passwordInput.text)
        }
        catch (e: IllegalArgumentException)
        {
            clearFields()
            return
        }

        jecnaDesktop.sceneManager!!.switchToScene(JDScene.LOADING)

        val jecnaWebClient = JecnaWebClient(auth)

        coroutineScope.launch {

            if (jecnaWebClient.login())
            {
                if (!AuthStore.isSaved)
                    AuthStore.save(auth)

                continueToMain(jecnaWebClient)
            }
            else
            {
                Platform.runLater { jecnaDesktop.sceneManager!!.switchToScene(JDScene.LOGIN) }
                clearFields()
            }
        }
    }

    private fun clearFields()
    {
        userInput.clear()
        passwordInput.clear()
    }

    private fun continueToMain(jecnaWebClient: JecnaWebClient)
    {
        jecnaDesktop.initDataAccess(jecnaWebClient)

        if (!Platform.isFxApplicationThread())
            Platform.runLater { jecnaDesktop.sceneManager!!.switchToScene(JDScene.MAIN) }
        else
            jecnaDesktop.sceneManager!!.switchToScene(JDScene.MAIN)
    }
}