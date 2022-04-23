package me.tomasan7.jecnadesktop.ui.component;

import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import me.tomasan7.jecnadesktop.data.Grade;

import java.text.DecimalFormat;

public class GradeAverageView extends AnchorPane
{
	private final float value;

	public GradeAverageView (float value)
	{
		this.value = value;
		Label gradeLabel = new Label(new DecimalFormat("0.##").format(value));

		gradeLabel.setAlignment(Pos.CENTER);

		AnchorPane.setTopAnchor(gradeLabel, 0d);
		AnchorPane.setRightAnchor(gradeLabel, 0d);
		AnchorPane.setBottomAnchor(gradeLabel, 0d);
		AnchorPane.setLeftAnchor(gradeLabel, 0d);

		getChildren().add(gradeLabel);

		getStyleClass().add("grade-view");
		getStylesheets().add("ui/component/GradeView.css");

		pseudoClassStateChanged(PseudoClass.getPseudoClass("value" + Math.round(value)), true);
	}

	public GradeAverageView (Iterable<Grade> grades)
	{
		/* Just copied the value constructor, because I wouldn't been able to call the #weightedAverage() method if I were to use this() constructor call. */

		this.value = weightedAverage(grades);
		Label gradeLabel = new Label(new DecimalFormat("0.##").format(value));

		gradeLabel.setAlignment(Pos.CENTER);

		AnchorPane.setTopAnchor(gradeLabel, 0d);
		AnchorPane.setRightAnchor(gradeLabel, 0d);
		AnchorPane.setBottomAnchor(gradeLabel, 0d);
		AnchorPane.setLeftAnchor(gradeLabel, 0d);

		getChildren().add(gradeLabel);

		getStyleClass().addAll("grade-view", "grade-average-view");
		getStylesheets().add("ui/component/GradeView.css");

		pseudoClassStateChanged(PseudoClass.getPseudoClass("value" + Math.round(value)), true);
	}

	private float weightedAverage (Iterable<Grade> grades)
	{
		int weightedSum = 0;
		int weightSum = 0;

		for (Grade grade : grades)
		{
			if (grade.value() == 0)
				continue;

			int weight = grade.small() ? 1 : 2;

			weightedSum += grade.value() * weight;
			weightSum += weight;
		}

		return (float) weightedSum / weightSum;
	}

	public float getValue ()
	{
		return value;
	}
}
