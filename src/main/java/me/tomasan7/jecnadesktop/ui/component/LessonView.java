package me.tomasan7.jecnadesktop.ui.component;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import me.tomasan7.jecnadesktop.data.Lesson;

public class LessonView extends AnchorPane
{
	/** The distance of the corner {@link Label labels} from the sides. */
	private static final double CORNER_DISTANCE = 5d;

	private final Lesson lesson;

	private final Label subjectLabel;
	private final StackPane subjectLabelContainer;
	private final Label teacherLabel;
	private final Label classroomLabel;
	private final Label groupLabel;

	public LessonView (Lesson lesson)
	{
		this.lesson = lesson;
		this.subjectLabel = new Label(lesson.subjectShort());
		this.subjectLabelContainer = new StackPane();
		this.teacherLabel = new Label(lesson.teacherShort());
		this.classroomLabel = new Label(lesson.classroom());
		this.groupLabel = lesson.group() != 0
				? new Label(String.valueOf(lesson.group()))
				: null;

		initSubjectLabel();
		initTeacherLabel();
		initClassroomLabel();
		initGroupLabel();

		getChildren().addAll(subjectLabelContainer, teacherLabel, classroomLabel);
		if (groupLabel != null)
			getChildren().add(groupLabel);

		getStyleClass().add("lesson-view");
		getStylesheets().add("/ui/component/LessonView.css");
	}

	private void initSubjectLabel ()
	{
		subjectLabel.getStyleClass().add("subject-label");

		fit(subjectLabelContainer);
		subjectLabelContainer.getChildren().add(subjectLabel);
		StackPane.setAlignment(subjectLabel, Pos.CENTER);
	}

	private void initTeacherLabel ()
	{
		teacherLabel.getStyleClass().add("teacher-label");

		AnchorPane.setLeftAnchor(teacherLabel, CORNER_DISTANCE);
		AnchorPane.setTopAnchor(teacherLabel, CORNER_DISTANCE);
	}

	private void initClassroomLabel ()
	{
		classroomLabel.getStyleClass().add("classroom-label");

		AnchorPane.setRightAnchor(classroomLabel, CORNER_DISTANCE);
		AnchorPane.setTopAnchor(classroomLabel, CORNER_DISTANCE);
	}

	private void initGroupLabel ()
	{
		if (groupLabel == null)
			return;

		groupLabel.getStyleClass().add("group-label");

		AnchorPane.setRightAnchor(groupLabel, CORNER_DISTANCE);
		AnchorPane.setBottomAnchor(groupLabel, CORNER_DISTANCE);
	}

	/**
	 * Fits the {@link Node} so it takes 100% of this {@link AnchorPane}.
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
