package me.tomasan7.jecnadesktop.ui.component;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import me.tomasan7.jecnadesktop.ui.SubPage;

import java.io.IOException;

public class SidebarPageElement extends SidebarElement
{
	private SubPage subPage;

	public SidebarPageElement ()
	{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/component/SidebarPageElement.fxml"));

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

	public SubPage getSubPage ()
	{
		return subPage;
	}
}