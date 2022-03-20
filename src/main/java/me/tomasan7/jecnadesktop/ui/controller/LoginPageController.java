package me.tomasan7.jecnadesktop.ui.controller;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import me.tomasan7.jecnadesktop.Main;
import me.tomasan7.jecnadesktop.ui.JDScene;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable
{
	@FXML
	public Label userLabel;
	@FXML
	public TextField userInput;
	@FXML
	public Label passwordLabel;
	@FXML
	public PasswordField passwordInput;
	@FXML
	public Button loginBtn;

	private final Main main;

	public LoginPageController (Main main)
	{
		this.main = main;
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

		loginBtn.setOnMouseClicked(event ->
		{
			main.getSceneManager().switchToScene(JDScene.MAIN);
		});
	}
}
