package me.tomasan7.jecnadesktop.ui;

import javafx.scene.Parent;
import me.tomasan7.jecnadesktop.util.FXMLUtils;

/**
 * Extends {@link CachedPage}.
 * In {@link #createContent()} it returns a {@link Parent} loaded from an FXML
 * on the {@link #getFxmlLocation()} path in the resources and supplies it with {@link #getController()}.
 */
public abstract class FXMLPage extends CachedPage
{
	/**
	 * returns a {@link Parent} loaded from an FXML
	 * on the {@link #getFxmlLocation()} path in the resources and supplies it with {@link #getController()}.
	 * @return The loaded {@link Parent}.
	 */
	@Override
	protected Parent createContent ()
	{
		/* Uses the getters, so the class can be extended and the getters overridden. */
		return FXMLUtils.loadFromResources(getFxmlLocation(), getController());
	}

	public abstract String getFxmlLocation ();

	public abstract Object getController ();
}
