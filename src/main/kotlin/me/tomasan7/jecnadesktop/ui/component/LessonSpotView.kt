package me.tomasan7.jecnadesktop.ui.component

import javafx.scene.layout.VBox
import me.tomasan7.jecnaapi.data.Lesson
import me.tomasan7.jecnaapi.data.LessonSpot
import java.util.function.Consumer

class LessonSpotView(val lessonSpot: LessonSpot) : VBox()
{
    init
    {
        styleClass.add("lesson-spot-view")
        spacing = 1.0
        lessonSpot.forEach { children.add(LessonView(it)) }
    }
}