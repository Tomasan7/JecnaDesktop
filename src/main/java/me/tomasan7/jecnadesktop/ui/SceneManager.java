package me.tomasan7.jecnadesktop.ui;

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
	private final Map<JDScene, Page> pages = new HashMap<>();

	public SceneManager (JecnaDesktop jecnaDesktop)
	{
		this.jecnaDesktop = jecnaDesktop;
	}

	public void switchToScene (JDScene jdScene)
	{
		jecnaDesktop.getPrimaryStage().setScene(new Scene(pages.get(jdScene).getContent()));
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
