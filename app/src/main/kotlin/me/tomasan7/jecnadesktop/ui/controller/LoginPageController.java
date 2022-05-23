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
import me.tomasan7.jecnadesktop.util.AuthStore;
import me.tomasan7.jecnadesktop.web.Auth;
import me.tomasan7.jecnadesktop.web.JecnaWebClient;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable
{
	@FXML
	private MFXTextField userInput;
	@FXML
	private MFXPasswordField passwordInput;
	@FXML
	private Button loginBtn;

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

		/* Disables/enables the login button based on whether both the fields are filled or not.  */
		ChangeListener<String> changeListener = (__, ___, ____) -> loginBtn.setDisable(!canLogin());

		passwordInput.textProperty().addListener(changeListener);
		userInput.textProperty().addListener(changeListener);

		passwordInput.setOnAction(event -> {if (canLogin()) onLogin();});
		loginBtn.setOnMouseClicked(event -> {if (canLogin()) onLogin();});
	}

	/**
	 * @return Whether the user can hit login or not. User can log in, when neither field is blank.
	 */
	private boolean canLogin ()
	{
		/* If neither is blank. */
		if (!passwordInput.getText().isBlank() && !userInput.getText().isBlank())
			return true;
		else /* Both are blank. */
			return false;
	}

	/**
	 * Run when you either press the login button or hit enter while the password field is focused.
	 */
	private void onLogin ()
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
			Platform.runLater(() -> jecnaDesktop.getSceneManager().switchToScene(JDScene.MAIN));
		else
			jecnaDesktop.getSceneManager().switchToScene(JDScene.MAIN);
	}
}
