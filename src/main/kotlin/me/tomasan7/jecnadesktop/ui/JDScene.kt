package me.tomasan7.jecnadesktop.ui

/**
 * Contains location to fxml page and controller for all scenes (pages) in the program.
 * @property location The string location of the FXML file inside resources.
 */
enum class JDScene(val location: String)
{
    LOGIN("/ui/scene/LoginPage.fxml"),
    MAIN("/ui/scene/MainPage.fxml"),
    LOADING("/ui/scene/LoadingPage.fxml");
}