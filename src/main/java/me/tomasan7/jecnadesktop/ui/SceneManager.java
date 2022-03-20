package me.tomasan7.jecnadesktop.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import me.tomasan7.jecnadesktop.JecnaDesktop;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SceneManager
{
	private final JecnaDesktop jecnaDesktop;
	/**
	 * Cached {@link Scene Scenes} for {@link JDScene JDScenes}.
	 */
	private final Map<JDScene, Scene> scenes = new HashMap<>();

	public SceneManager (JecnaDesktop jecnaDesktop)
	{
		this.jecnaDesktop = jecnaDesktop;
	}

	public void switchToScene (JDScene jdScene)
	{
		jecnaDesktop.getPrimaryStage().setScene(getScene(jdScene));
	}

	/**
	 * Lazily gets {@link Scene} for {@link JDScene}.
	 * @param jdScene The {@link JDScene} to get {@link Scene} for.
	 * @return The appropriate {@link Scene}.
	 * @see #scenes
	 */
	private Scene getScene (JDScene jdScene)
	{
		/* Check if whether the scene is already instantiated (cached) and serve it if so. */
		if (scenes.containsKey(jdScene))
			return scenes.get(jdScene);

		/* Instantiate and cache the scene. */

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(jdScene.getLocation()));
		fxmlLoader.setController(jdScene.newController(jecnaDesktop));

		Scene scene = null;
		Parent root = null;

		try
		{
			root = fxmlLoader.load();
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}

		scene = new Scene(root);

		/* Cache the Scene. */
		scenes.put(jdScene, scene);

		return scene;
	}
}
