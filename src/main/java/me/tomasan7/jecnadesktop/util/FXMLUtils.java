package me.tomasan7.jecnadesktop.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class FXMLUtils
{
	/**
	 * Loads an FXML from file in the resources and supplies it the passed controller.
	 * If the controller is null, the one specified in the FXML will be used.
	 * If there is not controller specified in the FXML, no controller will be used.
	 * @param location The location of the file in the resources.
	 * @param controller The controller you want to use for the loaded FXML.
	 * @return The FXML's {@link Parent}.
	 */
	public static Parent loadFromResources (@NotNull String location, @Nullable Object controller)
	{
		FXMLLoader fxmlLoader = new FXMLLoader(FXMLUtils.class.getResource(location));

		if (controller != null)
			fxmlLoader.setController(controller);

		Parent parent = null;

		try
		{
			parent = fxmlLoader.load();
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}

		return parent;
	}

	/**
	 * Shorthand for {@link #loadFromResources(String, Object)} {@code loadFromResources(location, null)}.
	 */
	public static Parent loadFromResources (@NotNull String location)
	{
		return loadFromResources(location, null);
	}
}
