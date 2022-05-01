package me.tomasan7.jecnadesktop.ui.subpage;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import me.tomasan7.jecnadesktop.JecnaDesktop;
import me.tomasan7.jecnadesktop.data.Grade;
import me.tomasan7.jecnadesktop.ui.CachedPage;
import me.tomasan7.jecnadesktop.ui.component.GradeAverageView;
import me.tomasan7.jecnadesktop.ui.component.GradeView;

import java.util.LinkedList;
import java.util.List;

public class GradesSubPage extends CachedPage
{
	private final JecnaDesktop jecnaDesktop;

	public GradesSubPage (JecnaDesktop jecnaDesktop)
	{
		this.jecnaDesktop = jecnaDesktop;
	}

	@Override
	protected Parent createContent ()
	{
		/* Make this page prettier:
		 * Make it more flexible, so when you smaller the width, it shrinks correctly. */

		/* The anchor pane is there to create a padding for the grid. */
		AnchorPane anchorPane = new AnchorPane();
		GridPane grid = new GridPane();
		anchorPane.getChildren().add(grid);

		AnchorPane.setLeftAnchor(grid, 10d);
		AnchorPane.setTopAnchor(grid, 10d);
		AnchorPane.setRightAnchor(grid, 10d);

		grid.getStylesheets().add("/ui/subpage/Grades.css");
		grid.getStyleClass().add("grid-pane");
		anchorPane.getStylesheets().add("/ui/subpage/Grades.css");
		anchorPane.getStyleClass().add("anchor-pane");

		List<GradesRow> rows = new LinkedList<>();

		jecnaDesktop.getGradesRepository().queryGradesAsync().thenAccept(grades ->
				Platform.runLater(() ->
				{
					for (String subject : grades.getSubjects())
						rows.add(new GradesRow(subject, grades.getGradesForSubject(subject)));

					for (int i = 0; i < rows.size(); i++)
					{
						GradesRow row = rows.get(i);

						grid.add(new Label(row.subject()), 0, i);

						FlowPane flowPane = new FlowPane();
						flowPane.setVgap(7.5);
						flowPane.setHgap(7.5);

						for (Grade grade : row.grades())
							flowPane.getChildren().add(new GradeView(grade));

						grid.add(flowPane, 1, i);
						GradeAverageView gradeAvgView = new GradeAverageView(row.grades());
						GridPane.setHgrow(gradeAvgView, Priority.NEVER);
						grid.add(gradeAvgView, 2, i);
					}
				}));

		ColumnConstraints column1 = new ColumnConstraints();
		column1.setMinWidth(230);

		grid.getColumnConstraints().add(column1);
		grid.setVgap(5);

		return anchorPane;
	}

	private record GradesRow(String subject, List<Grade> grades) {}
}
