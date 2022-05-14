package me.tomasan7.jecnadesktop.ui.subpage;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import me.tomasan7.jecnadesktop.data.Grade;
import me.tomasan7.jecnadesktop.data.Grades;
import me.tomasan7.jecnadesktop.repository.GradesRepository;
import me.tomasan7.jecnadesktop.ui.CachedPage;
import me.tomasan7.jecnadesktop.ui.component.GradeAverageView;
import me.tomasan7.jecnadesktop.ui.component.GradeView;

import java.util.List;
import java.util.Map;

public class GradesSubPage extends CachedPage
{
	private final GradesRepository gradesRepository;
	private GridPane grid;

	public GradesSubPage (GradesRepository gradesRepository)
	{
		this.gradesRepository = gradesRepository;
	}

	@Override
	protected Parent createContent ()
	{
		Parent container = createContainer();

		gradesRepository.queryGradesAsync().thenAccept(grades -> Platform.runLater(() -> populateData(grades)));

		return container;
	}

	private Parent createContainer ()
	{
		ScrollPane scrollPane = new ScrollPane();

		grid = new GridPane();
		scrollPane.setContent(grid);

		scrollPane.getStylesheets().add("/ui/subpage/Grades.css");
		grid.getStyleClass().add("grid-pane");
		scrollPane.getStyleClass().add("scroll-pane");

		ColumnConstraints labelsColumn = new ColumnConstraints();
		/* Doesn't work. */
		labelsColumn.setHgrow(Priority.NEVER);
		/* Fix to the previous line not working. */
		labelsColumn.setMinWidth(225);

		ColumnConstraints none = new ColumnConstraints();

		ColumnConstraints gradesColumn = new ColumnConstraints();
		gradesColumn.setHgrow(Priority.ALWAYS);

		grid.getColumnConstraints().addAll(labelsColumn, none, gradesColumn, none, none);
		grid.setVgap(5);
		grid.setHgap(5);
		grid.setPadding(new Insets(0d, 10d, 0d, 0d));

		return scrollPane;
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
			flowPane.getStyleClass().add("flow-pane");

			for (Grade grade : subjectGrades)
				flowPane.getChildren().add(new GradeView(grade));

			grid.add(flowPane, 2, i);
			GradeAverageView gradeAvgView = new GradeAverageView(subjectGrades);
			grid.add(gradeAvgView, 4, i);

			grid.add(new Separator(Orientation.HORIZONTAL), 0, i + 1, grid.getColumnCount(), 1);

			i += 2;
		}

		/* Vertical line between labels and grades column. */
		grid.add(new Separator(Orientation.VERTICAL), 1, 0, 1, grid.getRowCount());
		/* Vertical line between grades and grades average column. */
		grid.add(new Separator(Orientation.VERTICAL), 3, 0, 1, grid.getRowCount());
	}
}
