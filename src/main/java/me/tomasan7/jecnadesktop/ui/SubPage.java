package me.tomasan7.jecnadesktop.ui;

import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import me.tomasan7.jecnadesktop.JecnaDesktop;
import me.tomasan7.jecnadesktop.data.*;
import me.tomasan7.jecnadesktop.ui.component.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public enum SubPage
{
	ATTENDANCES
			{
				@Override
				public Parent create (JecnaDesktop jecnaDesktop)
				{
					MFXTableView table = new MFXTableView();

					table.getStylesheets().add("/ui/subpage/Attendances.css");

					MFXTableColumn<AttendancesRow> dayColumn = new MFXTableColumn<>("Den", true, Comparator.comparing(AttendancesRow::day));
					MFXTableColumn<AttendancesRow> attendancesColumn = new MFXTableColumn<>("Příchody a odchody", true, Comparator.comparingInt(row -> row.attendances.size()));

					attendancesColumn.setPrefWidth(200);

					dayColumn.setRowCellFactory(day -> new MFXTableRowCell<>(row -> row.day.format(DateTimeFormatter.ofPattern("dd.MM."))));
					attendancesColumn.setRowCellFactory(person -> new MFXTableRowCell<>(row -> Attendances.attendanceListAsString(row.attendances)));

					table.getTableColumns().setAll(dayColumn, attendancesColumn);

					/* TODO: This is wrong, this place shouldn't be responsible for making any data queries.
					 * This method (#create()) should only compose provided data into JavaFX component.
					 * Will be redesigned. */

					jecnaDesktop.getAttendancesRepository().queryAttendancesAsync().thenAccept(attendances ->
							Platform.runLater(() ->
							{
								ObservableList<AttendancesRow> rows = FXCollections.observableArrayList();

								for (LocalDate day : attendances.getDays())
									rows.add(new AttendancesRow(day, attendances.getAttendancesForDay(day)));

								table.setItems(rows);
							}));

					/* By default, MFX renders a footer with filter buttons. We don't want that. */
					table.footerVisibleProperty().setValue(false);

					return table;
				}
			},
	GRADES
			{
				@Override
				public Parent create (JecnaDesktop jecnaDesktop)
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
			},
	TIMETABLE
			{
				@Override
				public Parent create (JecnaDesktop jecnaDesktop)
				{
					GridPane grid = new GridPane();

					grid.setPadding(new Insets(10, 0, 0, 10));

					grid.setHgap(1);
					grid.setVgap(1);

					grid.getStyleClass().add("anchor-pane");
					grid.getStylesheets().add("/ui/subpage/Timetable.css");

					jecnaDesktop.getTimetableRepository().queryTimetableAsync().thenAccept(timetable ->
							Platform.runLater(() ->
							{
								List<LessonPeriod> lessonPeriods = timetable.getLessonPeriods();

								/* Creates the first row with the LessonPeriods. */
								for (int i = 0; i < lessonPeriods.size(); i++)
								{
									LessonPeriod lessonPeriod = lessonPeriods.get(i);
									grid.add(new LessonPeriodView(i + 1, lessonPeriod), i + 1, 0);
								}

								List<String> days = timetable.getDays();

								/* Creates the first column with the days. */
								for (int dI = 0; dI < days.size(); dI++)
								{
									String day = days.get(dI);
									Label dayLabel = new Label(day);
									dayLabel.getStyleClass().add("day-label");
									grid.add(dayLabel, 0, dI + 1);

									List<LessonSpot> lessonSpots = timetable.getLessonsForDay(day);

									for (int lI = 0; lI < lessonSpots.size(); lI++)
									{
										LessonSpot lessonSpot = lessonSpots.get(lI);

										if (lessonSpot == null)
											continue;

										LessonSpotView lessonView = new LessonSpotView(lessonSpot);
										grid.add(lessonView, lI + 1, dI + 1);
									}
								}
							}));

					return grid;
				}
			};

	/**
	 * Builds the subpage.
	 *
	 * @param jecnaDesktop The {@link JecnaDesktop} instance.
	 * @return The subpage.
	 */
	public abstract Parent create (JecnaDesktop jecnaDesktop);

	private record AttendancesRow(LocalDate day, List<Attendance> attendances)
	{
	}

	private record GradesRow(String subject, List<Grade> grades)
	{
	}
}
