package me.tomasan7.jecnadesktop.ui;

import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import me.tomasan7.jecnadesktop.JecnaDesktop;
import me.tomasan7.jecnadesktop.data.Attendance;
import me.tomasan7.jecnadesktop.data.Attendances;
import me.tomasan7.jecnadesktop.data.Grades;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
			HBox hBox = new HBox();
			hBox.getChildren().add(new Label("GRADES"));

			return hBox;
		}
	};

	/**
	 * Builds the subpage.
	 * @param jecnaDesktop The {@link JecnaDesktop} instance.
	 * @return The subpage.
	 */
	public abstract Parent create (JecnaDesktop jecnaDesktop);

	private record AttendancesRow(LocalDate day, List<Attendance> attendances)
	{
	}
}
