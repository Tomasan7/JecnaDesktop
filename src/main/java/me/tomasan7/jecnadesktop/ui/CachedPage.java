package me.tomasan7.jecnadesktop.ui;

import javafx.scene.Parent;

/**
 * Introduces {@link #createContent()} method which children use instead of the {@link #getContent()} method.
 * This method returns the content just like {@link #getContent()}.
 * First time, someone calls {@link #getContent()}, the result of a {@link #createContent()} is cached and returned.
 * Next time someone requests {@link #getContent()}, the cached content gets returned.
 *
 */
public abstract class CachedPage implements Page
{
	private Parent parent;

	@Override
	public Parent getContent ()
	{
		if (parent == null)
			parent = createContent();

		contentRequested();
		return parent;
	}

	/**
	 * Replaces the cached page with the new one returned from {@link #createContent()}.
	 */
	public void refresh ()
	{
		parent = createContent();
	}

	/**
	 * Gets called everytime {@link #getContent()} gets called.
	 * Indicates, that someone received the first or the cached content.
	 * Made to be overridden.
	 */
	protected void contentRequested ()
	{
	}

	/**
	 * Used instead of {@link #getContent()}.
	 * Result of this method gets cached and is returned by the {@link #getContent()} next time it gets called.
	 * @return The created page's content.
	 */
	protected abstract Parent createContent ();
}
