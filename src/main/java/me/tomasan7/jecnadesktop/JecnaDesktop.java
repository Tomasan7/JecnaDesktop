package me.tomasan7.jecnadesktop;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import me.tomasan7.jecnadesktop.ui.JDScene;
import me.tomasan7.jecnadesktop.ui.SceneManager;
import me.tomasan7.jecnadesktop.ui.SubPage;
import me.tomasan7.jecnadesktop.ui.SubPageManager;

public class JecnaDesktop extends Application
{
	private Stage primaryStage;
	private final SceneManager sceneManager = new SceneManager(this);
	private final SubPageManager subPageManager = new SubPageManager(this, sceneManager.getScene(JDScene.MAIN));

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

		getSceneManager().switchToScene(JDScene.MAIN);
		subPageManager.switchToPage(SubPage.ATTENDANCES);

		primaryStage.show();
	}

	public Stage getPrimaryStage ()
	{
		return primaryStage;
	}

	public SceneManager getSceneManager ()
	{
		return sceneManager;
	}

	public SubPageManager getSubPageManager ()
	{
		return subPageManager;
	}
}
