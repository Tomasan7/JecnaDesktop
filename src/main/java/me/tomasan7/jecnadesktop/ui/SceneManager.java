package me.tomasan7.jecnadesktop.ui;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

/**
 * Is responsible for swapping {@link JDScene scenes} in a {@link javafx.stage.Stage}.
 * Lazily loads the {@link Scene scenes}.
 * Caches all loaded {@link Scene scenes}, so when you switch to that scene again, it keeps it's state, since it's the same instance.
 */
public class SceneManager
{
	private final Stage stage;
	private final Map<JDScene, Page> pages = new HashMap<>();
	private final Map<JDScene, Scene> scenes = new HashMap<>();

	public SceneManager (Stage stage)
	{
		this.stage = stage;
	}

	public void switchToScene (JDScene jdScene)
	{
		/* TODO: This is a very ugly fix.
		 * Basically, the problem is, that you cannot have more Scene instances with the same root instance. */
		Parent content = pages.get(jdScene).getContent();
		Scene scene = scenes.get(jdScene);
		if (scene == null)
		{
			scene = new Scene(content);
			scenes.put(jdScene, scene);
		}
		else
			scene.setRoot(content);

		stage.setScene(scene);
	}

	public void addScene (JDScene jdScene, Page page)
	{
		pages.put(jdScene, page);
	}

	public void removeScene (JDScene jdScene)
	{
		pages.remove(jdScene);
	}
}
