package me.tomasan7.jecnadesktop.ui.subpage

import io.github.palexdev.materialfx.controls.MFXTableColumn
import io.github.palexdev.materialfx.controls.MFXTableView
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell
import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.Parent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.tomasan7.jecnadesktop.data.Attendance
import me.tomasan7.jecnadesktop.data.Attendances
import me.tomasan7.jecnadesktop.repository.AttendancesRepository
import me.tomasan7.jecnadesktop.ui.CachedPage
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AttendancesSubPage(private val attendancesRepository: AttendancesRepository) : CachedPage()
{
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    override fun createContent(): Parent
    {
        val table = MFXTableView<AttendancesRow>()

        table.stylesheets.add("/ui/subpage/Attendances.css")

        val dayColumn = MFXTableColumn("Den", true, Comparator.comparing { row: AttendancesRow -> row.day })

        /* TODO: Change the sorting to not be based on amount of passes, but on something more useful. */
        val attendancesColumn = MFXTableColumn("Příchody a odchody", true, Comparator.comparingInt { row: AttendancesRow -> row.attendances.size })

        attendancesColumn.prefWidth = 400.0

        dayColumn.setRowCellFactory {
            MFXTableRowCell { it.day.format(DateTimeFormatter.ofPattern("dd.MM.")) }
        }

        attendancesColumn.setRowCellFactory {
            MFXTableRowCell {
                Attendances.attendanceListAsString(it.attendances)
            }
        }

        table.tableColumns.setAll(dayColumn, attendancesColumn)

        coroutineScope.launch {

            val attendances = attendancesRepository.queryAttendances()
            Platform.runLater { table.setItems(toRowList(attendances)) }
        }

        /* By default, MFX renders a footer with filter buttons. We don't want that. */
        table.footerVisibleProperty().value = false

        return table
    }

    private fun toRowList(attendances: Attendances): ObservableList<AttendancesRow>
    {
        val rows = FXCollections.observableArrayList<AttendancesRow>()

        for (day in attendances.days)
            rows.add(AttendancesRow(day, attendances.getAttendancesForDay(day)))

        return rows
    }

    private data class AttendancesRow(val day: LocalDate, val attendances: List<Attendance>)
}