package me.tomasan7.jecnadesktop.ui;

import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import me.tomasan7.jecnadesktop.JecnaDesktop;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Is responsible for swapping {@link Page SubPages} in a sub-page container ({@link Pane}).
 * Eagerly loads all {@link CachedPage ChachedPages}.
 */
public class SubPageManager
{
	/** The MainPage SubPage pane. */
	private final Pane subPageContainer;

	private final JecnaDesktop jecnaDesktop;
	/**
	 * Cached {@link Parent Parents} for {@link Page subpages}.
	 */
	private final Map<SubPage, Page> pages = new HashMap<>();

	/** The currently active (viewed) {@link Page subpage} */
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
		Parent subPageParent = pages.get(subPage).getContent();
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
	 * Adds a {@link Page} to be handled by the {@link SubPageManager}.
	 * @param subPage The {@link SubPage} enum value.
	 * @param page The {@link Page} to be viewed.
	 */
	public void addSubPage (SubPage subPage, Page page)
	{
		/* This is to eagerly load and cache all cached pages. */
		if (page instanceof CachedPage cachedPage)
			cachedPage.getContent();

		pages.put(subPage, page);
	}

	/**
	 * Removes a {@link Page} to be handled by the {@link SubPageManager}.
	 * @param subPage The {@link SubPage} enum value.
	 */
	public void removeSubPage (SubPage subPage)
	{
		pages.remove(subPage);
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
	 * @return The currently active (viewed) {@link Page subpage}.
	 */
	public SubPage getCurrentPage ()
	{
		return currentPage;
	}
}
