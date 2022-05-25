package me.tomasan7.jecnadesktop.ui

import javafx.scene.Parent
import me.tomasan7.jecnadesktop.util.FXMLUtils

/**
 * Extends [CachedPage].
 * In [createContent] it returns a [Parent] loaded from an FXML
 * on the [fxmlLocation] path in the resources and supplies it with [controller].
 */
abstract class FxmlPage : CachedPage()
{
    /**
     * returns a [Parent] loaded from an FXML
     * on the [fxmlLocation] path in the resources and supplies it with [controller].
     * @return The loaded [Parent].
     */
    override fun createContent(): Parent
    {
        /* Uses the getters, so the class can be extended and the getters overridden. */
        return FXMLUtils.loadFromResources(fxmlLocation, controller)
    }

    abstract val fxmlLocation: String
    abstract val controller: Any?
}