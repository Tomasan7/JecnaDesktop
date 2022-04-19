package me.tomasan7.jecnadesktop.ui.component;

import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import me.tomasan7.jecnadesktop.data.Grade;

public class GradeView extends AnchorPane
{
	private final Label gradeLabel;
	private final Grade grade;

	public GradeView (Grade grade)
	{
		this.grade = grade;
		this.gradeLabel = new Label(String.valueOf(grade.valueChar()));

		gradeLabel.setAlignment(Pos.CENTER);

		AnchorPane.setTopAnchor(gradeLabel, 0d);
		AnchorPane.setRightAnchor(gradeLabel, 0d);
		AnchorPane.setBottomAnchor(gradeLabel, 0d);
		AnchorPane.setLeftAnchor(gradeLabel, 0d);

		getChildren().add(gradeLabel);

		getStyleClass().add("grade-view");
		getStylesheets().add("ui/GradeView.css");

		if (grade.small())
			pseudoClassStateChanged(PseudoClass.getPseudoClass("small"), true);

		pseudoClassStateChanged(PseudoClass.getPseudoClass("value" + grade.valueChar()), true);
	}

	public Label getGradeLabel ()
	{
		return gradeLabel;
	}

	public Grade getGrade ()
	{
		return grade;
	}
}
