package me.tomasan7.jecnadesktop.ui;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import me.tomasan7.jecnadesktop.JecnaDesktop;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Is responsible for swapping {@link SubPage SubPages} in a sub-page container ({@link Pane}).
 * Lazily loads the {@link SubPage SubPages}.
 * Caches all loaded {@link SubPage SubPages}, so when you switch to that sub-page again, it keeps it's state, since it's the same instance.
 */
public class SubPageManager
{
	/** The MainPage SubPage pane. */
	private final Pane subPageContainer;

	private final JecnaDesktop jecnaDesktop;
	/**
	 * Cached {@link Parent Parents} for {@link SubPage subpages}.
	 */
	private final Map<SubPage, Parent> pages = new HashMap<>();

	/** The currently active (viewed) {@link SubPage subpage} */
	private SubPage currentPage;

	private final Set<SubPageSwitchListener> subPageSwitchListeners = new HashSet<>();

	public SubPageManager (JecnaDesktop jecnaDesktop, Pane subPageContainer)
	{
		this.subPageContainer = subPageContainer;
		this.jecnaDesktop = jecnaDesktop;
	}

	public void switchToPage (SubPage subPage)
	{
		subPageContainer.getChildren().clear();
		Parent subPageParent = getSubPage(subPage);
		subPageContainer.getChildren().add(subPageParent);

		AnchorPane.setLeftAnchor(subPageParent, 0d);
		AnchorPane.setTopAnchor(subPageParent, 0d);
		AnchorPane.setRightAnchor(subPageParent, 0d);
		AnchorPane.setBottomAnchor(subPageParent, 0d);

		SubPage oldSubPage = currentPage;
		currentPage = subPage;

		/* Invoking the listeners. */
		subPageSwitchListeners.forEach(listener -> listener.onSwitch(oldSubPage, subPage));
	}

	/**
	 * Lazily gets {@link Parent subpage parent} for {@link SubPage}.
	 * @param subPage The {@link SubPage} to get {@link Parent} for.
	 * @return The appropriate {@link Parent}.
	 * @see #pages
	 */
	private Parent getSubPage (SubPage subPage)
	{
		/* Check if whether the scene is already instantiated (cached) and serve it if so. */
		if (pages.containsKey(subPage))
			return pages.get(subPage);

		/* Instantiate and cache the parent. */

		Parent subPageRoot = subPage.create(jecnaDesktop);

		/* Cache the Parent. */
		pages.put(subPage, subPageRoot);

		return subPageRoot;
	}

	public void registerSubPageSwitchListener (SubPageSwitchListener listener)
	{
		subPageSwitchListeners.add(listener);
	}

	public void unregisterSubPageSwitchListener (SubPageSwitchListener listener)
	{
		subPageSwitchListeners.remove(listener);
	}

	/**
	 * @return The currently active (viewed) {@link SubPage subpage}
	 */
	public SubPage getCurrentPage ()
	{
		return currentPage;
	}
}
