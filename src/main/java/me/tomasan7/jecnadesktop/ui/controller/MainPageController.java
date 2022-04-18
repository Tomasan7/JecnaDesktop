package me.tomasan7.jecnadesktop.ui.controller;

import javafx.application.Platform;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import me.tomasan7.jecnadesktop.JecnaDesktop;
import me.tomasan7.jecnadesktop.ui.component.SideBarElement;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
		initSideBarElements();
	}

	private void initSideBarElements ()
	{
		/* Get the SideBarElements from the sidebar. */
		List<SideBarElement> sideBarElements = sidebar.getChildrenUnmodifiable().stream()
													  .filter(child -> child instanceof SideBarElement)
													  .map(child -> (SideBarElement) child)
													  .toList();

		sideBarElements.forEach(sideBarElement ->
				sideBarElement.setOnMouseClicked(__ ->
				{
					/* Switch to SideBarElement's subpage. */
					jecnaDesktop.getSubPageManager().switchToPage(sideBarElement.getSubPage());
				}));

		/* We need to set the listener later, because SubPageManager isn't initialized yet.
		* This is kind of a hack, should be change to make it not hacky. */
		Executors.newSingleThreadScheduledExecutor().schedule(() ->
				Platform.runLater(() ->
						jecnaDesktop.getSubPageManager().registerSubPageSwitchListener(((oldSubPage, newSubPage) ->
						{
							/* Edit the modified value of each SideBarElement based on whether it's subpage is the current active one or not. */
							sideBarElements.forEach(sbe ->
									sbe.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"),
											sbe.getSubPage() == newSubPage));
						}))), 10, TimeUnit.MILLISECONDS);
	}
}
