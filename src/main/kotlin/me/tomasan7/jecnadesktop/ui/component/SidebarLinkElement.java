package me.tomasan7.jecnadesktop.ui.component;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class SidebarLinkElement extends SidebarElement
{
	@FXML
	private IconView linkIcon;
	@FXML
	private String link;

	public SidebarLinkElement ()
	{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/component/SidebarLinkElement.fxml"));

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

		setOnMouseClicked(event ->
						  {
							  if (!Desktop.isDesktopSupported())
								  return;

							  Desktop desktop = Desktop.getDesktop();

							  try
							  {
								  desktop.browse(URI.create(link));
							  }
							  catch (IOException e)
							  {
								  e.printStackTrace();
							  }
						  });
	}

	public IconView getLinkIcon ()
	{
		return linkIcon;
	}

	public void setLinkIcon (IconView linkIcon)
	{
		this.linkIcon = linkIcon;
	}

	public String getLink ()
	{
		return link;
	}

	public void setLink (String link)
	{
		this.link = link;
	}
}
