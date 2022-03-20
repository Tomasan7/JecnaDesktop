package me.tomasan7.jecnadesktop.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import me.tomasan7.jecnadesktop.Main;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SceneManager
{
	private final Main main;
	private final Map<JDScene, Scene> scenes = new HashMap<>();

	public SceneManager (Main main)
	{
		this.main = main;
	}

	public void switchToScene (JDScene jdScene)
	{
		main.getPrimaryStage().setScene(getScene(jdScene));
	}

	private Scene getScene (JDScene jdScene)
	{
		if (scenes.containsKey(jdScene))
			return scenes.get(jdScene);

		Scene scene = null;

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(jdScene.getLocation()));

		fxmlLoader.setController(jdScene.newController(main));
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

		scenes.put(jdScene, scene);
		return scene;
	}
}
