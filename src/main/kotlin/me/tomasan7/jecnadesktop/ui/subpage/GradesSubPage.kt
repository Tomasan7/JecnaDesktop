package me.tomasan7.jecnadesktop.ui.subpage

import javafx.application.Platform
import javafx.geometry.Insets
import javafx.geometry.Orientation
import javafx.scene.Parent
import javafx.scene.control.Label
import javafx.scene.control.ScrollPane
import javafx.scene.control.Separator
import javafx.scene.layout.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.tomasan7.jecnaapi.repository.GradesRepository
import me.tomasan7.jecnaapi.data.grade.GradesPage
import me.tomasan7.jecnadesktop.ui.CachedPage
import me.tomasan7.jecnadesktop.ui.component.GradeAverageView
import me.tomasan7.jecnadesktop.ui.component.GradeView
import me.tomasan7.jecnadesktop.ui.component.IconView

class GradesSubPage(private val gradesRepository: GradesRepository) : CachedPage()
{
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private lateinit var grid: GridPane

    override fun createContent(): Parent
    {
        val container = createContainer()

        coroutineScope.launch {

            val grades = gradesRepository.queryGradesPage()
            Platform.runLater { populateData(grades) }
        }

        return container
    }

    private fun createContainer(): Parent
    {
        val scrollPane = ScrollPane()

        grid = GridPane()
        scrollPane.content = grid

        scrollPane.stylesheets.add("/ui/subpage/Grades.css")
        grid.styleClass.add("grid-pane")
        scrollPane.styleClass.add("scroll-pane")

        val labelsColumn = ColumnConstraints().apply {
            /* Doesn't work. */
            hgrow = Priority.NEVER
            /* Fix to the previous line not working. */
            minWidth = 225.0
        }

        val none = ColumnConstraints()

        val gradesColumn = ColumnConstraints()
        gradesColumn.hgrow = Priority.ALWAYS

        grid.columnConstraints.addAll(labelsColumn, none, gradesColumn, none, none)

        with(grid) {
            vgap = 5.0
            hgap = 5.0
            padding = Insets(0.0, 10.0, 0.0, 0.0)
        }

        return scrollPane
    }

    private fun populateData(gradesPage: GradesPage)
    {
        val gradesMap = gradesPage.asMap

        var i = 0

        for (subject in gradesMap.keys.sortedBy { it.full })
        {
            val subjectGrades = gradesMap[subject]!!.grades

            grid.add(Label(subject.full), 0, i)

            val flowPane = FlowPane()
            flowPane.styleClass.add("flow-pane")

            for (grade in subjectGrades)
                flowPane.children.add(GradeView(grade))

            grid.add(flowPane, 2, i)

            if (subjectGrades.isNotEmpty())
            {
                val gradeAvgView = GradeAverageView(subjectGrades)
                grid.add(gradeAvgView, 4, i)
            }

            grid.add(Separator(Orientation.HORIZONTAL), 0, i + 1, grid.columnCount, 1)

            i += 2
        }

        /* Vertical line between labels and grades column. */
        grid.add(Separator(Orientation.VERTICAL), 1, 0, 1, grid.rowCount)
        /* Vertical line between grades and grades average column. */
        grid.add(Separator(Orientation.VERTICAL), 3, 0, 1, grid.rowCount)

        addDisclaimer()
    }

    private fun addDisclaimer()
    {
        val disclaimerContainer = HBox().apply {
            styleClass.add("disclaimer-container")
            GridPane.setMargin(this, Insets(10.0, 0.0, 0.0, 0.0))
        }

        val exclamationMarkIcon = IconView("/ui/icon/exclamation-mark-icon.png").apply {
            styleClass.add("exclamation-mark-icon")
            /* Setting the fitHeight property in stylesheets doesn't work. */
            fitHeight = 32.0
            isPreserveRatio = true
        }

        val disclaimer = Label("""
            Každý vyučující si váhy známek určuje dle sebe.
            Průměry známek jsou pouze orintační.
            """.trimIndent()).apply {
            styleClass.add("disclaimer")
            HBox.setMargin(this, Insets(0.0, 0.0, 0.0, 10.0))
        }

        disclaimerContainer.children.addAll(exclamationMarkIcon, disclaimer)

        grid.add(disclaimerContainer, 0, grid.rowCount)
    }
}