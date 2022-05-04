package me.tomasan7.jecnadesktop.ui.component;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public abstract class SidebarElement extends HBox
{
	@FXML
	protected ImageView icon;
	@FXML
	protected Label label;

	public SidebarElement ()
	{
		getStyleClass().add("side-bar-element");
		getStylesheets().add("/ui/component/SidebarElement.css");
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