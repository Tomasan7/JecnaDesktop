package me.tomasan7.jecnadesktop.ui.component

import io.github.palexdev.materialfx.controls.MFXTooltip
import javafx.css.PseudoClass
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.layout.AnchorPane
import javafx.util.Duration
import me.tomasan7.jecnaapi.data.grade.Grade
import java.time.format.DateTimeFormatter

class GradeView(val grade: Grade) : AnchorPane()
{
    init
    {
        val gradeDescText = grade.description ?: ""
        val gradeReceiveDateText = if (grade.receiveDate != null) DATE_PATTERN.format(grade.receiveDate) else ""

        val tooltip = MFXTooltip(this,"$gradeDescText ($gradeReceiveDateText)")

        tooltip.styleSheets.add("/ui/ToolTip.css")
        tooltip.showDelay = Duration.millis(500.0)
        tooltip.install()

        val gradeLabel = Label(grade.valueChar().toString())

        gradeLabel.alignment = Pos.CENTER

        setTopAnchor(gradeLabel, 0.0)
        setRightAnchor(gradeLabel, 0.0)
        setBottomAnchor(gradeLabel, 0.0)
        setLeftAnchor(gradeLabel, 0.0)

        children.add(gradeLabel)

        styleClass.add("grade-view")
        stylesheets.add("/ui/component/GradeView.css")

        if (grade.small)
            pseudoClassStateChanged(PseudoClass.getPseudoClass("small"), true)

        pseudoClassStateChanged(PseudoClass.getPseudoClass("value" + grade.valueChar()), true)
    }

    companion object
    {
        private val DATE_PATTERN = DateTimeFormatter.ofPattern("d.M.yyyy")
    }
}