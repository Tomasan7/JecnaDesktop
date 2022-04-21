package me.tomasan7.jecnadesktop.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Timetable
{
	private final NavigableMap<String, List<Lesson>> timetable;
	private final List<LessonHour> lessonHours;

	private Timetable (NavigableMap<String, List<Lesson>> timetable, List<LessonHour> lessonHours)
	{
		this.timetable = timetable;
		this.lessonHours = lessonHours;
	}

	/**
	 * @return All the days this timetable has {@link Lesson lessons} in.
	 */
	public List<String> getDays ()
	{
		return timetable.navigableKeySet().stream().toList();
	}

	/**
	 * @return All {@link LessonHour LessonHours} in the {@link Timetable}.
	 */
	public List<LessonHour> getLessonHours ()
	{
		return new ArrayList<>(lessonHours);
	}

	/**
	 * All {@link Lesson lessons} for the provided day.
	 * The {@link Lesson lessons} lessons are order by the hour they are in.
	 *
	 * @param day The day to get all {@link Lesson lessons} for.
	 * @return All {@link Lesson lessons} for the provided day.
	 */
	public List<Lesson> getLessonsForDay (String day)
	{
		return timetable.getOrDefault(day, new ArrayList<>());
	}

	@Override
	public String toString ()
	{
		return "Timetable{" +
			   "timetable=" + timetable +
			   ", lessonHours=" + lessonHours +
			   '}';
	}

	public static Builder builder ()
	{
		return new Builder();
	}

	public static class Builder
	{
		private final NavigableMap<String, List<Lesson>> timetable = new TreeMap<>(new DayComparator());
		private List<LessonHour> lessonHours = new ArrayList<>();

		/**
		 * Sets all the {@link LessonHour LessonHours}.
		 *
		 * @param lessonHours The {@link LessonHour LessonHours} to use.
		 * @return This {@link Builder builder's} instance back.
		 */
		public Builder lessonHours (@NotNull List<LessonHour> lessonHours)
		{
			this.lessonHours = lessonHours;
			return this;
		}

		/**
		 * Sets a {@link LessonHour} to a specified hour index.
		 *
		 * @param hour       The hour index to set the {@link LessonHour} to.
		 * @param lessonHour The {@link LessonHour} to set.
		 * @return This {@link Builder builder's} instance back.
		 */
		public Builder setLessonHour (int hour, @NotNull LessonHour lessonHour)
		{
			lessonHours.set(hour, lessonHour);
			return this;
		}

		/**
		 * Adds a {@link LessonHour} to the {@link Timetable}.
		 *
		 * @param lessonHour The {@link LessonHour} to add.
		 * @return This {@link Builder builder's} instance back.
		 */
		public Builder addLessonHour (@NotNull LessonHour lessonHour)
		{
			lessonHours.add(lessonHour);
			return this;
		}

		/**
		 * Sets a {@link Lesson} to an hour in a day.
		 * Can be {@code null}, if there is no lesson at that time.
		 * Overrides any existing {@link Lesson lessons}.
		 *
		 * @param day    The day to set the {@link Lesson} to.
		 * @param hour   The hour to set the {@link Lesson} to.
		 * @param lesson The {@link Lesson} to be set.
		 * @return This {@link Builder builder's} instance back.
		 */
		public Builder setLesson (@NotNull String day, int hour, @Nullable Lesson lesson)
		{
			/* Gets the list for the day, if none is present, creates a new list and puts it into the map. Then the lesson is added to that list. */
			timetable.computeIfAbsent(day, __ -> new LinkedList<>()).set(hour, lesson);
			return this;
		}

		/**
		 * Adds a {@link Lesson} to a day.
		 * Can be {@code null}, if there is no lesson at that time.
		 * The {@link Lesson} gets appended to the end.
		 *
		 * @param day    The day to add the {@link Lesson} to.
		 * @param lesson The {@link Lesson} to add.
		 * @return This {@link Builder builder's} instance back.
		 */
		public Builder addLesson (@NotNull String day, @Nullable Lesson lesson)
		{
			/* Gets the list for the day, if none is present, creates a new list and puts it into the map. Then the lesson is added to that list. */
			timetable.computeIfAbsent(day, __ -> new LinkedList<>()).add(lesson);
			return this;
		}

		public Timetable build ()
		{
			/* TODO: Maybe check if there is equal or more lessonHours than lessons in any day?
			 * Because that would mean there is a lesson without specified LessonHour. (period) */
			return new Timetable(timetable, lessonHours);
		}
	}

	/**
	 * This comparator compares two days based on their position in a week. Meaning tuesday < friday.
	 * The <b>days must follow this format</b>: Po/Út/St/Čt/Pa/So/Ne.
	 * <b>Note the "Pa", it is not "Pá".</b>
	 */
	public static class DayComparator implements Comparator<String>
	{
		/* Here we are using List#of(), because it returns an unmodifiable list. */
		public static final List<String> DAYS = List.of("Po", "Út", "St", "Čt", "Pa", "So", "Ne");

		@Override
		public int compare (String s1, String s2)
		{
			/* This method works by finding each day's position in a week using DAYS list and then subtracting their position. */

			if (s1.equalsIgnoreCase(s2))
				return 0;

			int s1Pos = -1;
			int s2Pos = -1;

			for (int i = 0; i < DAYS.size(); i++)
			{
				if (s1.equalsIgnoreCase(DAYS.get(i)))
				{
					s1Pos = i;
					continue;
				}

				if (s2.equalsIgnoreCase(DAYS.get(i)))
					s2Pos = i;

				if (s1Pos != -1 && s2Pos != -1)
					break;
			}

			return s1Pos - s2Pos;
		}
	}
}