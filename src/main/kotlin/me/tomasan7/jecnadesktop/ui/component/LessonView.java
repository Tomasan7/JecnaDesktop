package me.tomasan7.jecnadesktop.ui.component;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import me.tomasan7.jecnaapi.data.Lesson;

public class LessonView extends AnchorPane
{
	/** The distance of the corner {@link Label labels} from the sides. */
	private static final double CORNER_DISTANCE = 5d;

	private final Lesson lesson;

	public LessonView (Lesson lesson)
	{
		this.lesson = lesson;

		getChildren().addAll(createSubjectLabel(),
							 createTeacherLabel(),
							 createClassroomLabel());

		if (lesson.getGroup() != 0)
			getChildren().add(createGroupLabel());

		getStyleClass().add("lesson-view");
		getStylesheets().add("/ui/component/LessonView.css");
	}

	private StackPane createSubjectLabel ()
	{
		Label subjectLabel = new Label(lesson.getSubjectShort());
		subjectLabel.getStyleClass().add("subject-label");

		StackPane subjectLabelContainer = new StackPane();

		fit(subjectLabelContainer);
		subjectLabelContainer.getChildren().add(subjectLabel);
		StackPane.setAlignment(subjectLabel, Pos.CENTER);

		return subjectLabelContainer;
	}

	private Label createTeacherLabel ()
	{
		Label teacherLabel = new Label(lesson.getTeacherShort());
		teacherLabel.getStyleClass().add("teacher-label");

		AnchorPane.setLeftAnchor(teacherLabel, CORNER_DISTANCE);
		AnchorPane.setTopAnchor(teacherLabel, CORNER_DISTANCE);

		return teacherLabel;
	}

	private Label createClassroomLabel ()
	{
		Label classroomLabel = new Label(lesson.getClassroom());
		classroomLabel.getStyleClass().add("classroom-label");

		AnchorPane.setRightAnchor(classroomLabel, CORNER_DISTANCE);
		AnchorPane.setTopAnchor(classroomLabel, CORNER_DISTANCE);

		return classroomLabel;
	}

	private Label createGroupLabel ()
	{
		Label groupLabel = new Label(String.valueOf(lesson.getGroup()));
		groupLabel.getStyleClass().add("group-label");

		AnchorPane.setRightAnchor(groupLabel, CORNER_DISTANCE);
		AnchorPane.setBottomAnchor(groupLabel, CORNER_DISTANCE);

		return groupLabel;
	}

	/**
	 * Fits the {@link Node} so it takes 100% of this {@link AnchorPane}.
	 *
	 * @param node The {@link Node} to fit.
	 */
	private void fit (Node node)
	{
		AnchorPane.setTopAnchor(node, 0d);
		AnchorPane.setRightAnchor(node, 0d);
		AnchorPane.setBottomAnchor(node, 0d);
		AnchorPane.setLeftAnchor(node, 0d);
	}

	public Lesson getLesson ()
	{
		return lesson;
	}
}
