package me.tomasan7.jecnadesktop.ui;

/**
 * Contains location to fxml page and controller for all scenes (pages) in the program.
 */
public enum JDScene
{
	LOGIN("/ui/scene/LoginPage.fxml"),
	MAIN("/ui/scene/MainPage.fxml"),
	LOADING("/ui/scene/LoadingPage.fxml");

	/**
	 * The string location of the FXML file inside resources.
	 */
	private final String location;

	JDScene (String location)
	{
		this.location = location;
	}

	/**
	 * Returns the string location of the FXML file inside resources.
	 */
	public String getLocation ()
	{
		return location;
	}
}
