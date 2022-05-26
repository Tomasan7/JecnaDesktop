package me.tomasan7.jecnadesktop.ui

import javafx.scene.Parent
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.Pane
import java.util.LinkedList

/**
 * Is responsible for swapping sub-pages in a sub-page container ([Pane]).
 * Eagerly loads all [CachedPage]s.
 *
 * @property subPageContainer The MainPage SubPage pane.
 */
class SubPageManager(
    private val subPageContainer: Pane
)
{
    /**
     * Cached [Parent]s for subpages.
     */
    private val pages = HashMap<SubPage, Page>()

    /** The currently active (viewed) subpage. */
    var currentPage: SubPage? = null

    private val subPageSwitchListeners = LinkedList<(SubPage?, SubPage) -> Unit>()

    fun switchToPage (subPage: SubPage)
    {
        subPageContainer.children.clear()
        val subPageParent = pages[subPage]!!.content
        subPageContainer.children.add(subPageParent)

        AnchorPane.setLeftAnchor(subPageParent, 0.0)
        AnchorPane.setTopAnchor(subPageParent, 0.0)
        AnchorPane.setRightAnchor(subPageParent, 0.0)
        AnchorPane.setBottomAnchor(subPageParent, 0.0)

        val oldSubPage = currentPage
        currentPage = subPage

        /* Invoking the listeners. */
        subPageSwitchListeners.forEach { it(oldSubPage, currentPage!!) }
    }

    /**
     * Adds a [Page] to be handled by the [SubPageManager].
     * @param subPage The [SubPage] enum value.
     * @param page The [Page] to be viewed.
     */
    fun addSubPage (subPage: SubPage, page: Page)
    {
        /* This is to eagerly load and cache all cached pages. */
        if (page is CachedPage)
            page.content

        pages[subPage] = page
    }

    /**
     * Removes a [Page] to be handled by the [SubPageManager].
     * @param subPage The [SubPage] enum value.
     */
    fun removeSubPage (subPage: SubPage)
    {
        pages.remove(subPage)
    }

    fun registerSubPageSwitchListener (listener: (SubPage?, SubPage) -> Unit)
    {
        subPageSwitchListeners.add(listener)
    }

    fun unregisterSubPageSwitchListener (listener: (SubPage?, SubPage) -> Unit)
    {
        subPageSwitchListeners.remove(listener)
    }
}
