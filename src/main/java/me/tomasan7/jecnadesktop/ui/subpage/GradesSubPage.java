package me.tomasan7.jecnadesktop.ui.subpage;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import me.tomasan7.jecnadesktop.JecnaDesktop;
import me.tomasan7.jecnadesktop.data.Grade;
import me.tomasan7.jecnadesktop.data.Grades;
import me.tomasan7.jecnadesktop.ui.CachedPage;
import me.tomasan7.jecnadesktop.ui.component.GradeAverageView;
import me.tomasan7.jecnadesktop.ui.component.GradeView;

import java.util.List;
import java.util.Map;

public class GradesSubPage extends CachedPage
{
	private final JecnaDesktop jecnaDesktop;
	private GridPane grid;

	public GradesSubPage (JecnaDesktop jecnaDesktop)
	{
		this.jecnaDesktop = jecnaDesktop;
	}

	@Override
	protected Parent createContent ()
	{
		Parent container = createContainer();

		jecnaDesktop.getGradesRepository().queryGradesAsync().thenAccept(grades -> Platform.runLater(() -> populateData(grades)));

		return container;
	}

	private Parent createContainer ()
	{
		AnchorPane anchorPane = new AnchorPane();
		grid = new GridPane();
		anchorPane.getChildren().add(grid);

		AnchorPane.setLeftAnchor(grid, 10d);
		AnchorPane.setTopAnchor(grid, 10d);
		AnchorPane.setRightAnchor(grid, 10d);

		grid.getStylesheets().add("/ui/subpage/Grades.css");
		grid.getStyleClass().add("grid-pane");
		anchorPane.getStylesheets().add("/ui/subpage/Grades.css");
		anchorPane.getStyleClass().add("anchor-pane");


		ColumnConstraints column1 = new ColumnConstraints();
		column1.setMinWidth(230);

		grid.getColumnConstraints().add(column1);
		grid.setVgap(5);

		return anchorPane;
	}

	private void populateData (Grades grades)
	{
		Map<String, List<Grade>> gradesMap = grades.asMap();

		int i = 0;

		for (String subject : gradesMap.keySet())
		{
			List<Grade> subjectGrades = gradesMap.get(subject);

			grid.add(new Label(subject), 0, i);

			FlowPane flowPane = new FlowPane();
			flowPane.setVgap(7.5);
			flowPane.setHgap(7.5);

			for (Grade grade : subjectGrades)
				flowPane.getChildren().add(new GradeView(grade));

			grid.add(flowPane, 1, i);
			GradeAverageView gradeAvgView = new GradeAverageView(subjectGrades);
			GridPane.setHgrow(gradeAvgView, Priority.NEVER);
			grid.add(gradeAvgView, 2, i);

			i++;
		}
	}

	private record GradesRow(String subject, List<Grade> grades) {}
}
