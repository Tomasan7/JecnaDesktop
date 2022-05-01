package me.tomasan7.jecnadesktop.ui.scene;

import me.tomasan7.jecnadesktop.JecnaDesktop;
import me.tomasan7.jecnadesktop.ui.FXMLPage;
import me.tomasan7.jecnadesktop.ui.JDScene;
import me.tomasan7.jecnadesktop.ui.controller.LoginPageController;
import me.tomasan7.jecnadesktop.ui.controller.MainPageController;

public class MainScene extends FXMLPage
{
	private final JecnaDesktop jecnaDesktop;

	public MainScene (JecnaDesktop jecnaDesktop)
	{
		this.jecnaDesktop = jecnaDesktop;
	}

	@Override
	public String getFxmlLocation ()
	{
		return JDScene.MAIN.getLocation();
	}

	@Override
	public Object getController ()
	{
		return new MainPageController(jecnaDesktop);
	}
}
