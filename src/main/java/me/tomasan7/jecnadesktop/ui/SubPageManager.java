package me.tomasan7.jecnadesktop.ui;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import me.tomasan7.jecnadesktop.JecnaDesktop;

import java.util.HashMap;
import java.util.Map;

public class SubPageManager
{
	/** The MainPage SubPage pane. */
	private final Pane subPageContainer;

	private final JecnaDesktop jecnaDesktop;
	/**
	 * Cached {@link Parent Parents} for {@link SubPage subpages}.
	 */
	private final Map<SubPage, Parent> pages = new HashMap<>();

	public SubPageManager (JecnaDesktop jecnaDesktop, Scene mainPage)
	{
		this.subPageContainer = (Pane) mainPage.getRoot().getChildrenUnmodifiable().stream().filter(node -> {
			String id = node.getId();
			if (id == null)
				return false;
			return node.getId().equals("subPageContainer");
		}).findAny().get();
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
}
