package me.tomasan7.jecnadesktop.data;

import org.jetbrains.annotations.NotNull;

/**
 * A lesson in a timetable.
 *
 * @param group The group's number. Will be {@code 0}, when there aren't groups.
 */
public record Lesson (@NotNull String subject,
					  @NotNull String subjectShort,
					  @NotNull String teacher,
					  @NotNull String teacherShort,
					  @NotNull String classroom,
					  int group)
{
}
