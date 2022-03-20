package me.tomasan7.jecnadesktop.ui;

import me.tomasan7.jecnadesktop.JecnaDesktop;
import me.tomasan7.jecnadesktop.ui.controller.LoginPageController;
import me.tomasan7.jecnadesktop.ui.controller.MainPageController;

import java.util.function.Function;

public enum JDScene
{
	LOGIN("/ui/LoginPage.fxml", LoginPageController::new),
	MAIN("/ui/MainPage.fxml", MainPageController::new);

	/**
	 * The string location of the FXML file inside resources.
	 */
	private final String location;
	/**
	 * A function to create new instance of the FXML's controller.
	 */
	private final Function<JecnaDesktop, Object> newController;

	JDScene (String location, Function<JecnaDesktop, Object> newController)
	{
		this.location = location;
		this.newController = newController;
	}

	/**
	 * Returns the string location of the FXML file inside resources.
	 */
	public String getLocation ()
	{
		return location;
	}

	/**
	 * Returns new instance of this scene's controller.
	 * @param jecnaDesktop The {@link JecnaDesktop} instance.
	 * @return The new controller.
	 */
	public Object newController (JecnaDesktop jecnaDesktop)
	{
		return newController.apply(jecnaDesktop);
	}
}
