package me.tomasan7.jecnadesktop;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import me.tomasan7.jecnadesktop.ui.JDScene;
import me.tomasan7.jecnadesktop.ui.SceneManager;

public class Main extends Application
{
	private Stage primaryStage;
	private final SceneManager sceneManager = new SceneManager(this);

	public static void main (String[] args)
	{
		Application.launch(args);
	}

	@Override
	public void start (Stage primaryStage)
	{
		this.primaryStage = primaryStage;

		primaryStage.setTitle("Ječná Desktop");
		primaryStage.getIcons().add(new Image("ui/icon/logo.png"));

		getSceneManager().switchToScene(JDScene.LOGIN);

		primaryStage.show();
	}

	public SceneManager getSceneManager ()
	{
		return sceneManager;
	}

	public Stage getPrimaryStage ()
	{
		return primaryStage;
	}
}
