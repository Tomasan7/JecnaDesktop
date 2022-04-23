package me.tomasan7.jecnadesktop.ui.component;

import javafx.scene.layout.VBox;
import me.tomasan7.jecnadesktop.data.LessonSpot;

public class LessonSpotView extends VBox
{
	private final LessonSpot lessonSpot;

	public LessonSpotView (LessonSpot lessonSpot)
	{
		this.lessonSpot = lessonSpot;

		getStyleClass().add("lesson-spot-view");
		setSpacing(1);

		lessonSpot.forEach(lesson -> getChildren().add(new LessonView(lesson)));
	}

	public LessonSpot getLessonSpot ()
	{
		return lessonSpot;
	}
}
