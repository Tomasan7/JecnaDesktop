package me.tomasan7.jecnadesktop.ui

import javafx.scene.Parent

/**
 * Represents any kind of page, sub-page or different content in the window.
 * Exposes a single [content] method, which returns a [Parent] with the content of the page.
 */
interface Page
{
    /**
     * @return The content of this page.
     */
    val content: Parent
}