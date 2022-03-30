package me.tomasan7.jecnadesktop.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import me.tomasan7.jecnadesktop.JecnaDesktop;
import me.tomasan7.jecnadesktop.ui.component.SideBarElement;

import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController implements Initializable
{
	private final JecnaDesktop jecnaDesktop;

	@FXML
	private VBox sidebar;

	public MainPageController (JecnaDesktop jecnaDesktop)
	{
		this.jecnaDesktop = jecnaDesktop;
	}

	@Override
	public void initialize (URL location, ResourceBundle resources)
	{
		sidebar.getChildrenUnmodifiable().forEach(child ->
		{
			if (!(child instanceof SideBarElement sideBarElement))
				return;

			sideBarElement.setOnMouseClicked(event -> jecnaDesktop.getSubPageManager().switchToPage(sideBarElement.getSubPage()));
		});
	}
}
