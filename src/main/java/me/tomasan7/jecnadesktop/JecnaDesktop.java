package me.tomasan7.jecnadesktop;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import me.tomasan7.jecnadesktop.repository.*;
import me.tomasan7.jecnadesktop.ui.JDScene;
import me.tomasan7.jecnadesktop.ui.SceneManager;
import me.tomasan7.jecnadesktop.ui.scene.LoadingScene;
import me.tomasan7.jecnadesktop.ui.scene.LoginScene;
import me.tomasan7.jecnadesktop.ui.scene.MainScene;
import me.tomasan7.jecnadesktop.web.JecnaWebClient;

/**
 * Main class of the application.
 */
public class JecnaDesktop extends Application
{
	private Stage primaryStage = null;
	private JecnaWebClient jecnaWebClient = null;
	private GradesRepository gradesRepository = null;
	private AttendancesRepository attendancesRepository = null;
	private TimetableRepository timetableRepository = null;
	private SceneManager sceneManager = null;

	@Override
	public void start (Stage primaryStage)
	{
		this.primaryStage = primaryStage;

		primaryStage.setTitle("Ječná Desktop");
		primaryStage.getIcons().add(new Image("/ui/icon/logo.png"));
		/* For some reason the process doesn't exit by itself, so I kill it when the stage is closed. */
		primaryStage.setOnCloseRequest(__ -> {Platform.exit(); System.exit(0);});

		sceneManager = new SceneManager(primaryStage);
		registerPages();
		sceneManager.switchToScene(JDScene.LOGIN);

		primaryStage.show();
	}

	private void registerPages ()
	{
		sceneManager.addScene(JDScene.LOGIN, new LoginScene(this));
		sceneManager.addScene(JDScene.LOADING, new LoadingScene(this));
		sceneManager.addScene(JDScene.MAIN, new MainScene(this));
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
		this.timetableRepository = new WebTimetableRepository(jecnaWebClient);
	}

	public GradesRepository getGradesRepository ()
	{
		return gradesRepository;
	}

	public AttendancesRepository getAttendancesRepository ()
	{
		return attendancesRepository;
	}

	public TimetableRepository getTimetableRepository ()
	{
		return timetableRepository;
	}

	public SceneManager getSceneManager ()
	{
		return sceneManager;
	}
}
