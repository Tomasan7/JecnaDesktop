package me.tomasan7.jecnadesktop.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import me.tomasan7.jecnadesktop.JecnaDesktop;
import me.tomasan7.jecnadesktop.ui.controller.LoginPageController;
import me.tomasan7.jecnadesktop.ui.controller.MainPageController;

import java.io.IOException;
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

	/**
	 * Returns new {@link Parent page's root}.
	 * @param jecnaDesktop The {@link JecnaDesktop} instance.
	 * @return The new {@link Parent page's root}.
	 */
	public Parent loadPageRoot (JecnaDesktop jecnaDesktop)
	{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(location));
		fxmlLoader.setController(newController(jecnaDesktop));

		Parent page = null;

		try
		{
			page = fxmlLoader.load();
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}

		return page;
	}
}
