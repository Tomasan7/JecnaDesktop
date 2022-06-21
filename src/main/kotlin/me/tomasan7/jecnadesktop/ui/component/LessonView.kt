package me.tomasan7.jecnadesktop.ui.component

import javafx.geometry.Pos
import javafx.scene.Node
import javafx.scene.control.Label
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.StackPane
import me.tomasan7.jecnaapi.data.Lesson

class LessonView(val lesson: Lesson) : AnchorPane()
{

    init
    {
        children.addAll(createSubjectLabel(),
                        createTeacherLabel(),
                        createClassroomLabel())

        if (lesson.group != 0)
            children.add(createGroupLabel())

        styleClass.add("lesson-view")
        stylesheets.add("/ui/component/LessonView.css")
    }

    private fun createSubjectLabel(): StackPane
    {
        val subjectLabel = Label(lesson.subjectShort)
        subjectLabel.styleClass.add("subject-label")

        val subjectLabelContainer = StackPane()

        fit(subjectLabelContainer)
        subjectLabelContainer.children.add(subjectLabel)
        StackPane.setAlignment(subjectLabel, Pos.CENTER)

        return subjectLabelContainer
    }

    private fun createTeacherLabel(): Label
    {
        val teacherLabel = Label(lesson.teacherShort)
        teacherLabel.styleClass.add("teacher-label")

        setLeftAnchor(teacherLabel, CORNER_DISTANCE)
        setTopAnchor(teacherLabel, CORNER_DISTANCE)

        return teacherLabel
    }

    private fun createClassroomLabel(): Label
    {
        val classroomLabel = Label(lesson.classroom)
        classroomLabel.styleClass.add("classroom-label")

        setRightAnchor(classroomLabel, CORNER_DISTANCE)
        setTopAnchor(classroomLabel, CORNER_DISTANCE)

        return classroomLabel
    }

    private fun createGroupLabel(): Label
    {
        val groupLabel = Label(lesson.group.toString())
        groupLabel.styleClass.add("group-label")

        setRightAnchor(groupLabel, CORNER_DISTANCE)
        setBottomAnchor(groupLabel, CORNER_DISTANCE)

        return groupLabel
    }

    /**
     * Fits the [Node] so it takes 100% of this [AnchorPane].
     *
     * @param node The [Node] to fit.
     */
    private fun fit(node: Node)
    {
        setTopAnchor(node, 0.0)
        setRightAnchor(node, 0.0)
        setBottomAnchor(node, 0.0)
        setLeftAnchor(node, 0.0)
    }

    companion object
    {
        /** The distance of the corner [labels][Label] from the sides.  */
        private const val CORNER_DISTANCE = 5.0
    }
}