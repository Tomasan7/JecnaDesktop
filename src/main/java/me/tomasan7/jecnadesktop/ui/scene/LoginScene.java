package me.tomasan7.jecnadesktop.ui.scene;

import me.tomasan7.jecnadesktop.JecnaDesktop;
import me.tomasan7.jecnadesktop.ui.FXMLPage;
import me.tomasan7.jecnadesktop.ui.JDScene;
import me.tomasan7.jecnadesktop.ui.controller.LoginPageController;

public class LoginScene extends FXMLPage
{
	private final JecnaDesktop jecnaDesktop;

	public LoginScene (JecnaDesktop jecnaDesktop)
	{
		this.jecnaDesktop = jecnaDesktop;
	}

	@Override
	public String getFxmlLocation ()
	{
		return JDScene.LOGIN.getLocation();
	}

	@Override
	public Object getController ()
	{
		return new LoginPageController(jecnaDesktop);
	}
}
