package me.tomasan7.jecnadesktop.ui.subpage;

import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import me.tomasan7.jecnadesktop.JecnaDesktop;
import me.tomasan7.jecnadesktop.data.Attendance;
import me.tomasan7.jecnadesktop.data.Attendances;
import me.tomasan7.jecnadesktop.ui.CachedPage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public class AttendancesSubPage extends CachedPage
{
	private final JecnaDesktop jecnaDesktop;

	public AttendancesSubPage (JecnaDesktop jecnaDesktop)
	{
		this.jecnaDesktop = jecnaDesktop;
	}

	@Override
	protected Parent createContent ()
	{
		MFXTableView<AttendancesRow> table = new MFXTableView<>();

		table.getStylesheets().add("/ui/subpage/Attendances.css");

		MFXTableColumn<AttendancesRow> dayColumn = new MFXTableColumn<>("Den", true, Comparator.comparing(AttendancesRow::day));
		/* TODO: Change the sorting to not be based on amount of passes, but on something more useful. */
		MFXTableColumn<AttendancesRow> attendancesColumn = new MFXTableColumn<>("Příchody a odchody", true, Comparator.comparingInt(row -> row.attendances.size()));

		attendancesColumn.setPrefWidth(200);

		dayColumn.setRowCellFactory(day -> new MFXTableRowCell<>(row -> row.day.format(DateTimeFormatter.ofPattern("dd.MM."))));
		attendancesColumn.setRowCellFactory(person -> new MFXTableRowCell<>(row -> Attendances.attendanceListAsString(row.attendances)));

		table.getTableColumns().setAll(dayColumn, attendancesColumn);

		jecnaDesktop.getAttendancesRepository().queryAttendancesAsync().thenAccept(attendances ->
				Platform.runLater(() -> table.setItems(toRowList(attendances))));

		/* By default, MFX renders a footer with filter buttons. We don't want that. */
		table.footerVisibleProperty().setValue(false);

		return table;
	}

	private ObservableList<AttendancesRow> toRowList (Attendances attendances)
	{
		ObservableList<AttendancesRow> rows = FXCollections.observableArrayList();

		for (LocalDate day : attendances.getDays())
			rows.add(new AttendancesRow(day, attendances.getAttendancesForDay(day)));

		return rows;
	}

	private record AttendancesRow(LocalDate day, List<Attendance> attendances) {}
}
