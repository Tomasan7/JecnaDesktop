package me.tomasan7.jecnadesktop.ui.controller;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import me.tomasan7.jecnadesktop.JecnaDesktop;
import me.tomasan7.jecnadesktop.ui.JDScene;
import me.tomasan7.jecnadesktop.web.Auth;
import me.tomasan7.jecnadesktop.web.JecnaWebClient;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable
{
	@FXML
	public TextField userInput;
	@FXML
	public PasswordField passwordInput;
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
		/* Disables/enables the login button based on whether both the fields are filled or not.  */
		ChangeListener<String> changeListener = (observable, oldValue, newValue) ->
		{
			/* If neither is blank. */
			if (!passwordInput.textProperty().get().isBlank() && !userInput.textProperty().get().isBlank())
				loginBtn.setDisable(false);
			else /* Both are blank. */
				loginBtn.setDisable(true);
		};

		passwordInput.textProperty().addListener(changeListener);
		userInput.textProperty().addListener(changeListener);

		/* Run when you either press the login button or hit enter while the password field is focused. */
		Runnable onLogin = () ->
		{
			JecnaWebClient jecnaWebClient = new JecnaWebClient(new Auth(userInput.getText(), passwordInput.getText()));

			jecnaWebClient.login().thenAccept(successful ->
			{
				if (successful)
				{
					jecnaDesktop.initDataAccess(jecnaWebClient);
					Platform.runLater(() -> jecnaDesktop.getSceneManager().switchToScene(JDScene.MAIN));
				}
				else
				{
					userInput.clear();
					passwordInput.clear();
				}
			});
		};

		passwordInput.setOnAction(event -> onLogin.run());
		loginBtn.setOnMouseClicked(event -> onLogin.run());
	}
}
