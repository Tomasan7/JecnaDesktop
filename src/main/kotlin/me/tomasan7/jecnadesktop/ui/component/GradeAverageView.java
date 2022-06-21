package me.tomasan7.jecnadesktop.ui.component;

import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import me.tomasan7.jecnaapi.data.grade.Grade;

import java.text.DecimalFormat;

public class GradeAverageView extends AnchorPane
{
	private final float value;

	public GradeAverageView (float value)
	{
		this.value = value;
		init();
	}

	public GradeAverageView (Iterable<Grade> grades)
	{
		this.value = weightedAverage(grades);
		init();
	}

	private void init ()
	{
		Label gradeLabel = new Label(new DecimalFormat("0.##").format(value));

		gradeLabel.setAlignment(Pos.CENTER);

		AnchorPane.setTopAnchor(gradeLabel, 0d);
		AnchorPane.setRightAnchor(gradeLabel, 0d);
		AnchorPane.setBottomAnchor(gradeLabel, 0d);
		AnchorPane.setLeftAnchor(gradeLabel, 0d);

		getChildren().add(gradeLabel);

		getStyleClass().addAll("grade-view", "grade-average-view");
		getStylesheets().add("/ui/component/GradeView.css");

		pseudoClassStateChanged(PseudoClass.getPseudoClass("value" + Math.round(value)), true);
	}

	private float weightedAverage (Iterable<Grade> grades)
	{
		int weightedSum = 0;
		int weightSum = 0;

		for (Grade grade : grades)
		{
			if (grade.getValue() == 0)
				continue;

			int weight = grade.getSmall() ? 1 : 2;

			weightedSum += grade.getValue() * weight;
			weightSum += weight;
		}

		return (float) weightedSum / weightSum;
	}

	public float getValue ()
	{
		return value;
	}
}
