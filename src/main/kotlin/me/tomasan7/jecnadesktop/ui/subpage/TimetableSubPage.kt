package me.tomasan7.jecnadesktop.ui.subpage

import javafx.application.Platform
import javafx.scene.Parent
import javafx.scene.control.Label
import javafx.scene.layout.GridPane
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.tomasan7.jecnadesktop.data.Timetable
import me.tomasan7.jecnadesktop.repository.TimetableRepository
import me.tomasan7.jecnadesktop.ui.CachedPage
import me.tomasan7.jecnadesktop.ui.component.LessonPeriodView
import me.tomasan7.jecnadesktop.ui.component.LessonSpotView

class TimetableSubPage(private val timetableRepository: TimetableRepository) : CachedPage()
{
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    override fun createContent(): Parent
    {
        val grid = createGrid()

        coroutineScope.launch {

            val timetable = timetableRepository.queryTimetable()
            Platform.runLater { populateData(timetable, grid) }
        }

        return grid
    }

    private fun createGrid(): GridPane
    {
        val grid = GridPane()
        grid.hgap = 1.0
        grid.vgap = 1.0
        grid.styleClass.add("anchor-pane")
        grid.stylesheets.add("/ui/subpage/Timetable.css")
        return grid
    }

    private fun populateData(timetable: Timetable, grid: GridPane)
    {
        val lessonPeriods = timetable.lessonPeriods

        /* Creates the first row with the LessonPeriods. */
        for (i in lessonPeriods.indices)
        {
            val lessonPeriod = lessonPeriods[i]
            grid.add(LessonPeriodView(i + 1, lessonPeriod), i + 1, 0)
        }

        val days = timetable.days

        for (dI in days.indices)
        {
            val day = days[dI]
            val dayLabel = Label(day)
            dayLabel.styleClass.add("day-label")
            grid.add(dayLabel, 0, dI + 1)

            val lessonSpots = timetable.getLessonsForDay(day)

            for (lI in lessonSpots.indices)
            {
                val lessonSpot = lessonSpots[lI] ?: continue

                val lessonView = LessonSpotView(lessonSpot)
                grid.add(lessonView, lI + 1, dI + 1)
            }
        }
    }
}