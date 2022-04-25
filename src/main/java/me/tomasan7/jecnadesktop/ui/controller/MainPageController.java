package me.tomasan7.jecnadesktop.ui.controller;

import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import me.tomasan7.jecnadesktop.JecnaDesktop;
import me.tomasan7.jecnadesktop.ui.SubPage;
import me.tomasan7.jecnadesktop.ui.SubPageManager;
import me.tomasan7.jecnadesktop.ui.component.SideBarElement;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainPageController implements Initializable
{
	private final JecnaDesktop jecnaDesktop;

	@FXML
	private VBox sidebar;

	@FXML
	private AnchorPane subPageContainer;

	private SubPageManager subPageManager;


	public MainPageController (JecnaDesktop jecnaDesktop)
	{
		this.jecnaDesktop = jecnaDesktop;
	}

	@Override
	public void initialize (URL location, ResourceBundle resources)
	{
		subPageManager = new SubPageManager(jecnaDesktop, subPageContainer);
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
					subPageManager.switchToPage(sideBarElement.getSubPage());
				}));

		subPageManager.registerSubPageSwitchListener(((oldSubPage, newSubPage) ->
		{
			/* Edit the modified value of each SideBarElement based on whether it's subpage is the current active one or not. */
			sideBarElements.forEach(sbe ->
					sbe.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"),
							sbe.getSubPage() == newSubPage));
		}));

		subPageManager.switchToPage(SubPage.GRADES);
	}

	public SubPageManager getSubPageManager ()
	{
		return subPageManager;
	}
}
