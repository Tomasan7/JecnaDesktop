package me.tomasan7.jecnadesktop.ui

import javafx.scene.Parent

/**
 * Introduces [createContent] method which children use instead of the [content] property.
 * This method returns the content just like [content].
 * First time, someone calls [content], the result of a [createContent] is cached and returned.
 * Next time someone requests [content], the cached content gets returned.
 */
abstract class CachedPage : Page
{
    private var parent: Parent? = null

    override val content: Parent
        get()
        {
            if (parent == null)
                parent = createContent()

            contentRequested()
            return parent as Parent
        }

    /**
     * Replaces the cached page with the new one returned from [content].
     */
    fun refresh()
    {
        parent = createContent()
    }

    /**
     * Gets called everytime [content] gets called.
     * Indicates, that someone received the first or the cached content.
     * Made to be overridden.
     */
    protected fun contentRequested()
    {
    }

    /**
     * Used instead of [content].
     * Result of this method gets cached and is returned by the [content] next time it gets called.
     * @return The created page's content.
     */
    protected abstract fun createContent(): Parent
}