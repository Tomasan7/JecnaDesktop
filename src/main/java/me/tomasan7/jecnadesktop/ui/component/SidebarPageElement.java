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

	public SubPage getSubPage ()
	{
		return subPage;
	}

	public void setSubPage (SubPage subPage)
	{
		this.subPage = subPage;
	}
}