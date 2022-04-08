package me.tomasan7.jecnadesktop;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import me.tomasan7.jecnadesktop.data.Attendances;
import me.tomasan7.jecnadesktop.repository.AttendancesRepository;
import me.tomasan7.jecnadesktop.repository.GradesRepository;
import me.tomasan7.jecnadesktop.repository.WebAttendancesRepository;
import me.tomasan7.jecnadesktop.repository.WebGradesRepository;
import me.tomasan7.jecnadesktop.ui.JDScene;
import me.tomasan7.jecnadesktop.ui.SceneManager;
import me.tomasan7.jecnadesktop.ui.SubPage;
import me.tomasan7.jecnadesktop.ui.SubPageManager;
import me.tomasan7.jecnadesktop.web.JecnaWebClient;

import java.util.concurrent.Executors;

public class JecnaDesktop extends Application
{
	private Stage primaryStage;
	private JecnaWebClient jecnaWebClient = null;
	private GradesRepository gradesRepository = null;
	private AttendancesRepository attendancesRepository = null;
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

		getSceneManager().switchToScene(JDScene.LOGIN);

		subPageManager.switchToPage(SubPage.ATTENDANCES);

		primaryStage.show();
	}

	public Stage getPrimaryStage ()
	{
		return primaryStage;
	}

	public JecnaWebClient getJecnaWebClient ()
	{
		return jecnaWebClient;
	}

	public void initDataAccess (JecnaWebClient jecnaWebClient)
	{
		if (this.jecnaWebClient != null)
			throw new IllegalStateException("Data access has already been initialized.");

		this.jecnaWebClient = jecnaWebClient;
		this.gradesRepository = new WebGradesRepository(jecnaWebClient);
		this.attendancesRepository = new WebAttendancesRepository(jecnaWebClient);
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