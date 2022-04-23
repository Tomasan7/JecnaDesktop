package me.tomasan7.jecnadesktop.ui.component;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import me.tomasan7.jecnadesktop.data.LessonHour;

public class LessonHourView extends AnchorPane
{
	/** The distance of the corner {@link Label labels} from the sides. */
	private static final double EDGE_DISTANCE = 1d;

	private final LessonHour lessonHour;

	private final Label hourIndexLabel;
	private final Label periodLabel;

	public LessonHourView (int hourIndex, LessonHour lessonHour)
	{
		this.lessonHour = lessonHour;
		this.hourIndexLabel = new Label(String.valueOf(hourIndex));
		this.periodLabel = new Label(lessonHour.toString());

		initHourIndexLabel();
		initPeriodTable();

		getChildren().addAll(hourIndexLabel, periodLabel);

		getStyleClass().add("lesson-hour-view");
		getStylesheets().add("/ui/component/LessonHourView.css");
	}

	private void initHourIndexLabel ()
	{
		hourIndexLabel.getStyleClass().add("hour-index-label");

		AnchorPane.setTopAnchor(hourIndexLabel, EDGE_DISTANCE);
		AnchorPane.setLeftAnchor(hourIndexLabel, 0d);
		AnchorPane.setRightAnchor(hourIndexLabel, 0d);
	}

	private void initPeriodTable ()
	{
		periodLabel.getStyleClass().add("period-label");

		AnchorPane.setBottomAnchor(periodLabel, EDGE_DISTANCE);
		AnchorPane.setLeftAnchor(periodLabel, 0d);
		AnchorPane.setRightAnchor(periodLabel, 0d);
	}

	public LessonHour getLessonHour ()
	{
		return lessonHour;
	}
}
