package me.tomasan7.jecnadesktop.ui.component;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import me.tomasan7.jecnadesktop.data.LessonHour;

public class LessonHourView extends AnchorPane
{
	/** The distance of the corner {@link Label labels} from the sides. */
	private static final double EDGE_DISTANCE = 1d;

	private final LessonHour lessonHour;
	private final int hourIndex;

	public LessonHourView (int hourIndex, LessonHour lessonHour)
	{
		this.lessonHour = lessonHour;
		this.hourIndex = hourIndex;

		createHourIndexLabel();
		createPeriodLabel();

		getChildren().addAll(createHourIndexLabel(), createPeriodLabel());

		getStyleClass().add("lesson-hour-view");
		getStylesheets().add("/ui/component/LessonHourView.css");
	}

	private Label createHourIndexLabel ()
	{
		Label hourIndexLabel = new Label(String.valueOf(hourIndex));
		hourIndexLabel.getStyleClass().add("hour-index-label");

		AnchorPane.setTopAnchor(hourIndexLabel, EDGE_DISTANCE);
		AnchorPane.setLeftAnchor(hourIndexLabel, 0d);
		AnchorPane.setRightAnchor(hourIndexLabel, 0d);

		return hourIndexLabel;
	}

	private Label createPeriodLabel ()
	{
		Label periodLabel = new Label(lessonHour.toString());
		periodLabel.getStyleClass().add("period-label");

		AnchorPane.setBottomAnchor(periodLabel, EDGE_DISTANCE);
		AnchorPane.setLeftAnchor(periodLabel, 0d);
		AnchorPane.setRightAnchor(periodLabel, 0d);

		return periodLabel;
	}

	public LessonHour getLessonHour ()
	{
		return lessonHour;
	}
}
