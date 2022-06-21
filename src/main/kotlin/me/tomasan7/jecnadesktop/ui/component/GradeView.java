package me.tomasan7.jecnadesktop.ui.component;

import io.github.palexdev.materialfx.controls.MFXTooltip;
import javafx.css.PseudoClass;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import me.tomasan7.jecnaapi.data.grade.Grade;

import java.time.format.DateTimeFormatter;

public class GradeView extends AnchorPane
{
	private final Grade grade;

	public GradeView (Grade grade)
	{
		this.grade = grade;

		MFXTooltip tooltip = new MFXTooltip(this, String.format("%s (%s)",
																grade.getDescription() != null
																		? grade.getDescription()
																		: "",
																grade.getReceiveDate() != null
																		? grade.getReceiveDate().format(DateTimeFormatter.ofPattern("d.M.yyyy"))
																		: ""));
		tooltip.getStyleSheets().add("/ui/ToolTip.css");
		tooltip.setShowDelay(Duration.millis(500));
		tooltip.install();

		Label gradeLabel = new Label(String.valueOf(grade.valueChar()));

		gradeLabel.setAlignment(Pos.CENTER);

		AnchorPane.setTopAnchor(gradeLabel, 0d);
		AnchorPane.setRightAnchor(gradeLabel, 0d);
		AnchorPane.setBottomAnchor(gradeLabel, 0d);
		AnchorPane.setLeftAnchor(gradeLabel, 0d);

		getChildren().add(gradeLabel);

		getStyleClass().add("grade-view");
		getStylesheets().add("/ui/component/GradeView.css");

		if (grade.getSmall())
			pseudoClassStateChanged(PseudoClass.getPseudoClass("small"), true);

		pseudoClassStateChanged(PseudoClass.getPseudoClass("value" + grade.valueChar()), true);
	}

	public Grade getGrade ()
	{
		return grade;
	}
}
