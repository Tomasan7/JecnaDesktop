package me.tomasan7.jecnadesktop.data;

/**
 * A lesson in a timetable.
 *
 * @param group The group's number. Will be {@code 0}, when there aren't groups.
 */
public record Lesson (String subject, String subjectShort, String teacher, String teacherShort, String classroom, int group)
{
}
