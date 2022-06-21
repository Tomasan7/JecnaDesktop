package me.tomasan7.jecnadesktop.ui.component

import javafx.css.PseudoClass
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.AnchorPane
import me.tomasan7.jecnaapi.data.grade.Grade
import java.text.DecimalFormat
import kotlin.math.roundToInt

class GradeAverageView : AnchorPane
{
    val value: Float

    constructor(value: Float)
    {
        this.value = value
        init()
    }

    constructor(grades: Iterable<Grade>)
    {
        this.value = weightedAverage(grades)
        init()
    }

    private fun init()
    {
        val gradeLabel = Label(DECIMAL_FORMAT.format(value.toDouble()))

        gradeLabel.alignment = Pos.CENTER

        setTopAnchor(gradeLabel, 0.0)
        setRightAnchor(gradeLabel, 0.0)
        setBottomAnchor(gradeLabel, 0.0)
        setLeftAnchor(gradeLabel, 0.0)

        children.add(gradeLabel)

        styleClass.addAll("grade-view", "grade-average-view")
        stylesheets.add("/ui/component/GradeView.css")

        pseudoClassStateChanged(PseudoClass.getPseudoClass("value" + value.roundToInt()), true)
    }

    private fun weightedAverage(grades: Iterable<Grade>): Float
    {
        var weightedSum = 0
        var weightSum = 0

        for (grade in grades)
        {
            if (grade.value == 0)
                continue

            val weight = if (grade.small) 1 else 2

            weightedSum += grade.value * weight
            weightSum += weight
        }

        return weightedSum.toFloat() / weightSum
    }

    companion object
    {
        private val DECIMAL_FORMAT = DecimalFormat("0.##")
    }
}