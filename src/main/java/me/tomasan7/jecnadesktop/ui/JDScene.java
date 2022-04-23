package me.tomasan7.jecnadesktop.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import me.tomasan7.jecnadesktop.JecnaDesktop;
import me.tomasan7.jecnadesktop.ui.controller.LoginPageController;
import me.tomasan7.jecnadesktop.ui.controller.MainPageController;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.function.Function;

public enum JDScene
{
	LOGIN("/ui/scene/LoginPage.fxml", LoginPageController::new),
	MAIN("/ui/scene/MainPage.fxml", MainPageController::new),
	LOADING("/ui/scene/LoadingPage.fxml", null);

	/**
	 * The string location of the FXML file inside resources.
	 */
	private final String location;
	/**
	 * A function to create new instance of the FXML's controller.
	 */
	@Nullable
	private final Function<JecnaDesktop, Object> newController;

	JDScene (String location, @Nullable Function<JecnaDesktop, Object> newController)
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
	 *
	 * @param jecnaDesktop The {@link JecnaDesktop} instance.
	 * @return The new controller. Or {@code null}, if there's no controller.
	 */
	@Nullable
	public Object newController (JecnaDesktop jecnaDesktop)
	{
		return newController != null ? newController.apply(jecnaDesktop) : null;
	}

	/**
	 * Returns new {@link Parent page's root}.
	 *
	 * @param jecnaDesktop The {@link JecnaDesktop} instance.
	 * @return The new {@link Parent page's root}.
	 */
	public Parent loadPageRoot (JecnaDesktop jecnaDesktop)
	{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(location));
		Object controller = newController(jecnaDesktop);
		if (controller != null)
			fxmlLoader.setController(controller);

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
