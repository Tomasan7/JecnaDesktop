package me.tomasan7.jecnadesktop.ui;

import javafx.beans.value.ChangeListener;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable
{
	public Label userLabel;
	public TextField userInput;
	public Label passwordLabel;
	public PasswordField passwordInput;
	public Button loginBtn;

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
	}
}
