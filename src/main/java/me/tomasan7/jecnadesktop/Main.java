package me.tomasan7.jecnadesktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application
{
	public static void main (String[] args)
	{
		Application.launch(args);
	}

	@Override
	public void start (Stage primaryStage) throws IOException
	{
		Parent root = FXMLLoader.load(getClass().getResource("/ui/LoginPage.fxml"));
		Scene scene = new Scene(root);

		primaryStage.setTitle("Ječná Desktop");
		primaryStage.getIcons().add(new Image("ui/icon/logo.png"));
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
