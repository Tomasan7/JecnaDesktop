package me.tomasan7.jecnadesktop.ui.controller;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import me.tomasan7.jecnadesktop.JecnaDesktop;
import me.tomasan7.jecnadesktop.ui.JDScene;
import me.tomasan7.jecnadesktop.ui.SubPage;
import me.tomasan7.jecnadesktop.util.AuthStore;
import me.tomasan7.jecnadesktop.web.Auth;
import me.tomasan7.jecnadesktop.web.JecnaWebClient;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Supplier;

public class LoginPageController implements Initializable
{
	@FXML
	public MFXTextField userInput;
	@FXML
	public MFXPasswordField passwordInput;
	@FXML
	public Button loginBtn;

	private final JecnaDesktop jecnaDesktop;

	public LoginPageController (JecnaDesktop jecnaDesktop)
	{
		this.jecnaDesktop = jecnaDesktop;
	}

	@Override
	public void initialize (URL location, ResourceBundle resources)
	{
		if (AuthStore.isSaved())
		{
			Auth auth = AuthStore.load();
			if (auth != null)
			{
				JecnaWebClient jecnaWebClient = new JecnaWebClient(auth);

				jecnaDesktop.getSceneManager().switchToScene(JDScene.LOADING);
				jecnaWebClient.login().thenAccept(successful ->
				{
					if (successful)
						continueToMain(jecnaWebClient);
					else
					{
						AuthStore.delete();
						Platform.runLater(() -> jecnaDesktop.getSceneManager().switchToScene(JDScene.LOGIN));
					}
				});
			}
			else
				AuthStore.delete();
		}

		/* Whether the user can hit login or not. User can log in, when neither field is blank. */
		Supplier<Boolean> canlogin = () ->
		{
			/* If neither is blank. */
			if (!passwordInput.textProperty().get().isBlank() && !userInput.textProperty().get().isBlank())
				return true;
			else /* Both are blank. */
				return false;
		};

		/* Disables/enables the login button based on whether both the fields are filled or not.  */
		ChangeListener<String> changeListener = (__, ___, ____) -> loginBtn.setDisable(!canlogin.get());

		passwordInput.textProperty().addListener(changeListener);
		userInput.textProperty().addListener(changeListener);

		/* Run when you either press the login button or hit enter while the password field is focused. */
		Runnable onLogin = () ->
		{
			Auth auth = null;

			try
			{
				auth = new Auth(userInput.getText(), passwordInput.getText());
			}
			catch (IllegalArgumentException e)
			{
				clearFields();
				return;
			}

			jecnaDesktop.getSceneManager().switchToScene(JDScene.LOADING);

			JecnaWebClient jecnaWebClient = new JecnaWebClient(auth);

			Auth finalAuth = auth;
			jecnaWebClient.login().thenAccept(successful ->
			{
				if (successful)
				{
					if (!AuthStore.isSaved())
						AuthStore.save(finalAuth);

					continueToMain(jecnaWebClient);
				}
				else
				{
					Platform.runLater(() -> jecnaDesktop.getSceneManager().switchToScene(JDScene.LOGIN));
					clearFields();
				}
			});
		};

		passwordInput.setOnAction(event -> {if (canlogin.get()) onLogin.run();});
		loginBtn.setOnMouseClicked(event -> {if (canlogin.get()) onLogin.run();});
	}

	private void clearFields ()
	{
		userInput.clear();
		passwordInput.clear();
	}

	private void continueToMain (JecnaWebClient jecnaWebClient)
	{
		jecnaDesktop.initDataAccess(jecnaWebClient);

		if (!Platform.isFxApplicationThread())
		{
			Platform.runLater(() ->
			{
				jecnaDesktop.getSceneManager().switchToScene(JDScene.MAIN);
			});
		}
		else
		{
			jecnaDesktop.getSceneManager().switchToScene(JDScene.MAIN);
		}
	}
}
