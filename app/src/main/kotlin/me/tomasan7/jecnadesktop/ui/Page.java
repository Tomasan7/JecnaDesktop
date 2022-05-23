package me.tomasan7.jecnadesktop.ui;

import javafx.scene.Parent;

/**
 * Represents any kind of page, sub-page or different content in the window.
 * Exposes a single {@link #getContent()} method, which returns a {@link Parent} with the content of the page.
 */
public interface Page
{
	/**
	 * @return The content of this page.
	 */
	Parent getContent ();
}