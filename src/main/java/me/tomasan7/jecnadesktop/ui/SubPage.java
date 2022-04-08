package me.tomasan7.jecnadesktop.ui;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import me.tomasan7.jecnadesktop.JecnaDesktop;

public enum SubPage
{
	ATTENDANCES
			{
				@Override
				public Parent create (JecnaDesktop jecnaDesktop)
				{
					HBox hBox = new HBox();
					hBox.getChildren().add(new Label("ATTENDANCES"));

					return hBox;
				}
			},
	GRADES
	{
		@Override
		public Parent create (JecnaDesktop jecnaDesktop)
		{
			HBox hBox = new HBox();
			hBox.getChildren().add(new Label("GRADES"));

			return hBox;
		}
	};

	/**
	 * Builds the subpage.
	 * @param jecnaDesktop The {@link JecnaDesktop} instance.
	 * @return The subpage.
	 */
	public abstract Parent create (JecnaDesktop jecnaDesktop);
}
