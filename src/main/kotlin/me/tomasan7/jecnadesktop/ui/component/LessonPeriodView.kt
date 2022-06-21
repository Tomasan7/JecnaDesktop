package me.tomasan7.jecnadesktop.ui.component

import javafx.scene.control.Label
import javafx.scene.layout.AnchorPane
import me.tomasan7.jecnaapi.data.LessonPeriod

class LessonPeriodView(private val hourIndex: Int, val lessonPeriod: LessonPeriod) : AnchorPane()
{
    init
    {
        createHourIndexLabel()
        createPeriodLabel()

        children.addAll(createHourIndexLabel(), createPeriodLabel())

        styleClass.add("lesson-period-view")
        stylesheets.add("/ui/component/LessonPeriodView.css")
    }

    private fun createHourIndexLabel(): Label
    {
        val hourIndexLabel = Label(hourIndex.toString())
        hourIndexLabel.styleClass.add("hour-index-label")

        setTopAnchor(hourIndexLabel, EDGE_DISTANCE)
        setLeftAnchor(hourIndexLabel, 0.0)
        setRightAnchor(hourIndexLabel, 0.0)

        return hourIndexLabel
    }

    private fun createPeriodLabel(): Label
    {
        val periodLabel = Label(lessonPeriod.toString())
        periodLabel.styleClass.add("period-label")

        setBottomAnchor(periodLabel, EDGE_DISTANCE)
        setLeftAnchor(periodLabel, 0.0)
        setRightAnchor(periodLabel, 0.0)

        return periodLabel
    }

    companion object
    {
        /** The distance of the corner [labels][Label] from the sides.  */
        private const val EDGE_DISTANCE = 1.0
    }
}