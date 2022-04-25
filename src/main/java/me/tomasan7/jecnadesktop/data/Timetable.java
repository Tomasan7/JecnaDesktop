package me.tomasan7.jecnadesktop.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Timetable
{
	private final NavigableMap<String, List<LessonSpot>> timetable;
	private final List<LessonPeriod> lessonPeriods;

	private Timetable (NavigableMap<String, List<LessonSpot>> timetable, List<LessonPeriod> lessonPeriods)
	{
		this.timetable = timetable;
		this.lessonPeriods = lessonPeriods;
	}

	/**
	 * @return All the days this timetable has {@link Lesson lessons} in.
	 */
	public List<String> getDays ()
	{
		return timetable.navigableKeySet().stream().toList();
	}

	/**
	 * @return All {@link LessonPeriod LessonPeriods} in the {@link Timetable}.
	 */
	public List<LessonPeriod> getLessonPeriods ()
	{
		return new ArrayList<>(lessonPeriods);
	}

	/**
	 * All {@link LessonSpot lessons} for the provided day.
	 * The {@link LessonSpot lessons} lessons are order by the hour they are in.
	 *
	 * @param day The day to get all {@link Lesson lessons} for.
	 * @return All {@link LessonSpot lessons} for the provided day.
	 */
	public List<LessonSpot> getLessonsForDay (String day)
	{
		return timetable.getOrDefault(day, new ArrayList<>());
	}

	@Override
	public String toString ()
	{
		return "Timetable{" +
			   "timetable=" + timetable +
			   ", lessonHours=" + lessonPeriods +
			   '}';
	}

	public static Builder builder ()
	{
		return new Builder();
	}

	public static class Builder
	{
		private final NavigableMap<String, List<LessonSpot>> timetable = new TreeMap<>(new DayComparator());
		private List<LessonPeriod> lessonPeriods = new ArrayList<>();

		/**
		 * Sets all the {@link LessonPeriod LessonPeriods}.
		 *
		 * @param lessonPeriods The {@link LessonPeriod LessonPeriods} to use.
		 * @return This {@link Builder builder's} instance back.
		 */
		public Builder lessonHours (@NotNull List<LessonPeriod> lessonPeriods)
		{
			this.lessonPeriods = lessonPeriods;
			return this;
		}

		/**
		 * Sets a {@link LessonPeriod} to a specified hour index.
		 *
		 * @param hour       The hour index to set the {@link LessonPeriod} to.
		 * @param lessonPeriod The {@link LessonPeriod} to set.
		 * @return This {@link Builder builder's} instance back.
		 */
		public Builder setLessonPeriod (int hour, @NotNull LessonPeriod lessonPeriod)
		{
			lessonPeriods.set(hour, lessonPeriod);
			return this;
		}

		/**
		 * Adds a {@link LessonPeriod} to the {@link Timetable}.
		 *
		 * @param lessonPeriod The {@link LessonPeriod} to add.
		 * @return This {@link Builder builder's} instance back.
		 */
		public Builder addLessonPeriod (@NotNull LessonPeriod lessonPeriod)
		{
			lessonPeriods.add(lessonPeriod);
			return this;
		}

		/**
		 * Sets a {@link LessonSpot} to an hour in a day.
		 * Can be {@code null}, if there is no lesson at that time.
		 * Overrides any existing {@link LessonSpot lessons}.
		 *
		 * @param day    The day to set the {@link LessonSpot} to.
		 * @param hour   The hour to set the {@link LessonSpot} to.
		 * @param lessonSpot The {@link LessonSpot} to be set.
		 * @return This {@link Builder builder's} instance back.
		 */
		public Builder setLessonSpot (@NotNull String day, int hour, @Nullable LessonSpot lessonSpot)
		{
			/* Gets the list for the day, if none is present, creates a new list and puts it into the map. Then the lesson is added to that list. */
			timetable.computeIfAbsent(day, __ -> new LinkedList<>()).set(hour, lessonSpot);
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
			timetable.computeIfAbsent(day, __ -> new LinkedList<>()).set(hour, lesson != null ? new LessonSpot(lesson) : null);
			return this;
		}

		/**
		 * Adds a {@link LessonSpot} to a day.
		 * Can be {@code null}, if there is no lesson at that time.
		 * The {@link LessonSpot} gets appended to the end.
		 *
		 * @param day    The day to add the {@link LessonSpot} to.
		 * @param lessonSpot The {@link LessonSpot} to add.
		 * @return This {@link Builder builder's} instance back.
		 */
		public Builder addLessonSpot (@NotNull String day, @Nullable LessonSpot lessonSpot)
		{
			/* Gets the list for the day, if none is present, creates a new list and puts it into the map. Then the lesson is added to that list. */
			timetable.computeIfAbsent(day, __ -> new LinkedList<>()).add(lessonSpot);
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
			timetable.computeIfAbsent(day, __ -> new LinkedList<>()).add(lesson != null ? new LessonSpot(lesson) : null);
			return this;
		}

		public Timetable build ()
		{
			/* TODO: Maybe check if there is equal or more lessonHours than lessons in any day?
			 * Because that would mean there is a lesson without specified LessonPeriod. (period) */
			return new Timetable(timetable, lessonPeriods);
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