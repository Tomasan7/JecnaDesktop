package me.tomasan7.jecnadesktop.ui;

import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import me.tomasan7.jecnadesktop.JecnaDesktop;
import me.tomasan7.jecnadesktop.data.Attendance;
import me.tomasan7.jecnadesktop.data.Attendances;
import me.tomasan7.jecnadesktop.data.Grade;
import me.tomasan7.jecnadesktop.ui.component.GradeView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
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

					MFXTableColumn<AttendancesRow> dayColumn = new MFXTableColumn<>("Day", true, Comparator.comparing(AttendancesRow::day));
					MFXTableColumn<AttendancesRow> attendancesColumn = new MFXTableColumn<>("Attendances", true, Comparator.comparingInt(row -> row.attendances.size()));

					attendancesColumn.setPrefWidth(200);

					dayColumn.setRowCellFactory(day -> new MFXTableRowCell<>(row -> row.day.format(DateTimeFormatter.ofPattern("dd.MM."))));
					attendancesColumn.setRowCellFactory(person -> new MFXTableRowCell<>(row -> Attendances.attendanceListAsString(row.attendances)));

					table.getTableColumns().setAll(dayColumn, attendancesColumn);

					/* This is wrong, this place shouldn't be responsible for making any data queries.
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
					 * Create a little padding, since now the grid is right next to the edges.
					 * Make it more flexible, so when you smaller the width, it shrinks correctly. */

					GridPane grid = new GridPane();

					grid.getStylesheets().add("/ui/subpage/Grades.css");
					grid.getStyleClass().add("grid-pane");

					ObservableList<GradesRow> rows = FXCollections.observableArrayList();

					jecnaDesktop.getGradesRepository().queryGradesAsync().thenAccept(grades ->
							Platform.runLater(() ->
							{
								for (String subject : grades.getSubjects())
									rows.add(new GradesRow(subject, grades.gradesForSubject(subject)));

								for (int i = 0; i < rows.size(); i++)
								{
									GradesRow row = rows.get(i);

									grid.add(new Label(row.subject()), 0, i);

									FlowPane flowPane = new FlowPane();
									flowPane.setVgap(5);
									flowPane.setHgap(5);

									for (Grade grade : row.grades())
										flowPane.getChildren().add(new GradeView(grade));

									grid.add(flowPane, 1, i);
								}
							}));

					ColumnConstraints column1 = new ColumnConstraints();
					column1.setMinWidth(230);

					grid.getColumnConstraints().add(column1);
					grid.setVgap(5);

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
