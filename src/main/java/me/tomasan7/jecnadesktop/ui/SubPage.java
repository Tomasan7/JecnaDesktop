package me.tomasan7.jecnadesktop.ui;

import javafx.application.Platform;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum SubPage
{
	ATTENDANCES
			{
				@Override
				public Parent create (JecnaDesktop jecnaDesktop)
				{
					TableView tableView = new TableView();

					tableView.setPlaceholder(new Label("Obsah se načítá..."));

					/* This is wrong, this place shouldn't be responsible for making any data queries.
					* This method should only compose provided data into JavaFX component.
					* Will be redesigned. */
					jecnaDesktop.getAttendancesRepository().queryAttendancesAsync().thenAccept(attendances ->
							Platform.runLater(() ->
							{
								TableColumn<Map, String> dayColumn = new TableColumn<>("Day");
								dayColumn.setCellValueFactory(new MapValueFactory<>("day"));
								TableColumn<Map, String> attendancesColumn = new TableColumn<>("Attendances");
								attendancesColumn.setCellValueFactory(new MapValueFactory<>("attendances"));

								tableView.getColumns().add(dayColumn);
								tableView.getColumns().add(attendancesColumn);

								for (LocalDate day : attendances.getDays())
								{
									Map<String, Object> item = new HashMap<>();
									item.put("day", day.format(DateTimeFormatter.ofPattern("dd.MM.")));
									item.put("attendances" , attendances.getAttendancesForDay(day).toString());
									tableView.getItems().add(item);
								}
							}));

					return tableView;
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
}
