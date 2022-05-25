package me.tomasan7.jecnadesktop.util

import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import java.io.IOException

object FxmlUtils
{
    /**
     * Loads an FXML from file in the resources and supplies it the passed controller.
     * If the controller is null, the one specified in the FXML will be used.
     * If there is not controller specified in the FXML, no controller will be used.
     * @param location The location of the file in the resources.
     * @param controller The controller you want to use for the loaded FXML.
     * @return The FXML's [Parent].
     */
    fun loadFromResources(location: String, controller: Any? = null): Parent
    {
        val fxmlLoader = FXMLLoader(FxmlUtils::class.java.getResource(location))

        if (controller != null)
            fxmlLoader.setController(controller)

        return try
        {
            fxmlLoader.load()
        }
        catch (e: IOException)
        {
            throw RuntimeException(e)
        }
    }
}