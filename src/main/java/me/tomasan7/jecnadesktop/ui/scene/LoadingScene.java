package me.tomasan7.jecnadesktop.ui.scene;

import me.tomasan7.jecnadesktop.JecnaDesktop;
import me.tomasan7.jecnadesktop.ui.FXMLPage;
import me.tomasan7.jecnadesktop.ui.JDScene;

public class LoadingScene extends FXMLPage
{
	private final JecnaDesktop jecnaDesktop;

	public LoadingScene (JecnaDesktop jecnaDesktop)
	{
		this.jecnaDesktop = jecnaDesktop;
	}

	@Override
	public String getFxmlLocation ()
	{
		return JDScene.LOADING.getLocation();
	}

	@Override
	public Object getController ()
	{
		return null;
	}
}
