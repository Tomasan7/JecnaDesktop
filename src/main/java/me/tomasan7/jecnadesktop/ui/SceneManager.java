package me.tomasan7.jecnadesktop.ui;

import javafx.scene.Parent;
import javafx.scene.Scene;
import me.tomasan7.jecnadesktop.JecnaDesktop;

import java.util.HashMap;
import java.util.Map;

/**
 * Is responsible for swapping {@link JDScene scenes} in a {@link javafx.stage.Stage}.
 * Lazily loads the {@link Scene scenes}.
 * Caches all loaded {@link Scene scenes}, so when you switch to that scene again, it keeps it's state, since it's the same instance.
 */
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
	public Scene getScene (JDScene jdScene)
	{
		/* Check if whether the scene is already instantiated (cached) and serve it if so. */
		if (scenes.containsKey(jdScene))
			return scenes.get(jdScene);

		/* Instantiate and cache the scene. */

		Parent pageRoot = jdScene.loadPageRoot(jecnaDesktop);
		Scene scene = new Scene(pageRoot);

		/* Cache the Scene. */
		scenes.put(jdScene, scene);

		return scene;
	}
}
