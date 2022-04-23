package me.tomasan7.jecnadesktop.data;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * This class represents a spot for {@link Lesson lessons} in a timetable.
 * That spot can contain multiple {@link Lesson lessons}.
 * For example some lessons are split into more groups.
 * This class indicates the one whole lesson and contains the lessons for each group.
 */
public class LessonSpot implements Iterable<Lesson>
{
	private final List<Lesson> lessons;

	public LessonSpot (@NotNull List<Lesson> lessons)
	{
		if (lessons.stream().anyMatch(Objects::isNull))
			throw new IllegalArgumentException("Lessons cannot contain null values.");

		if (lessons.isEmpty())
			throw new IllegalArgumentException("At least one lesson must be present.");

		if (findDuplicateGroups(lessons))
			throw new IllegalArgumentException("Lessons cannot have duplicate groups.");

		/* Encapsulation.  */
		this.lessons = new ArrayList<>(lessons);
		this.lessons.sort(Comparator.comparingInt(Lesson::group));
	}

	public LessonSpot (@NotNull Lesson lesson)
	{
		this.lessons = new ArrayList<>();
		this.lessons.add(lesson);
	}

	/**
	 * @return Whether there are any duplicate groups in provided {@link Lesson lessons}.
	 */
	private boolean findDuplicateGroups (List<Lesson> lessons)
	{
		Set<Integer> groups = new HashSet<>();

		for (Lesson lesson : lessons)
		{
			if (!groups.add(lesson.group()))
				return true;
		}

		return false;
	}

	public Lesson getLessonByGroup (int group)
	{
		return lessons.get(group);
	}

	/**
	 * @return An {@link Iterator} of {@link Lesson}, which doesn't modify this {@link LessonSpot}.
	 */
	@NotNull
	@Override
	public Iterator<Lesson> iterator ()
	{
		/* Encapsulation.  */
		return new ArrayList<>(lessons).iterator();
	}

	@Override
	public String toString ()
	{
		return "LessonSpot{" +
			   "lessons=" + lessons +
			   '}';
	}
}