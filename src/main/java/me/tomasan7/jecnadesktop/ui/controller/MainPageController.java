package me.tomasan7.jecnadesktop.ui.controller;

import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import me.tomasan7.jecnadesktop.JecnaDesktop;
import me.tomasan7.jecnadesktop.ui.SubPage;
import me.tomasan7.jecnadesktop.ui.SubPageManager;
import me.tomasan7.jecnadesktop.ui.component.SidebarElement;
import me.tomasan7.jecnadesktop.ui.component.SidebarPageElement;
import me.tomasan7.jecnadesktop.ui.subpage.AttendancesSubPage;
import me.tomasan7.jecnadesktop.ui.subpage.GradesSubPage;
import me.tomasan7.jecnadesktop.ui.subpage.TimetableSubPage;

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

		subPageManager.addSubPage(SubPage.GRADES, new GradesSubPage(jecnaDesktop));
		subPageManager.addSubPage(SubPage.ATTENDANCES, new AttendancesSubPage(jecnaDesktop));
		subPageManager.addSubPage(SubPage.TIMETABLE, new TimetableSubPage(jecnaDesktop));

		initSideBarElements();
	}

	private void initSideBarElements ()
	{
		/* Get the SideBarElements from the sidebar. */
		List<SidebarPageElement> sidebarElements = sidebar.getChildrenUnmodifiable().stream()
													  .filter(child -> child instanceof SidebarPageElement)
													  .map(child -> (SidebarPageElement) child)
													  .toList();

		sidebarElements.forEach(sidebarPageElement ->
				sidebarPageElement.setOnMouseClicked(__ ->
				{
					/* Switch to SideBarElement's subpage. */
					subPageManager.switchToPage(sidebarPageElement.getSubPage());
				}));

		subPageManager.registerSubPageSwitchListener(((oldSubPage, newSubPage) ->
		{
			/* Edit the modified value of each SideBarElement based on whether it's subpage is the current active one or not. */
			sidebarElements.forEach(sbe ->
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
