package me.tomasan7.jecnadesktop.ui.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class SideBarElement extends HBox
{
	@FXML
	private ImageView icon;
	@FXML
	private Label label;

	public SideBarElement ()
	{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/SideBarElement.fxml"));

		try
		{
			fxmlLoader.setRoot(this);
			fxmlLoader.setController(this);
			fxmlLoader.load();
		}
		catch (IOException exception)
		{
			throw new RuntimeException(exception);
		}
	}

	public String getIcon ()
	{
		return icon.getImage().getUrl();
	}

	public void setIcon (String url)
	{
		icon.setImage(new Image(url));
	}

	public String getLabel ()
	{
		return label.getText();
	}

	public void setLabel (String label)
	{
		this.label.setText(label);
	}
}
