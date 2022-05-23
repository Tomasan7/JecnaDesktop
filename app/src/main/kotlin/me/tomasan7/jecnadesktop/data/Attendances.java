package me.tomasan7.jecnadesktop.data;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.*;

/**
 * Holds all {@link Attendance attendances} for each day.
 */
public class Attendances
{
	private final Map<LocalDate, List<Attendance>> attendances;

	private Attendances (@NotNull Map<LocalDate, @NotNull List<Attendance>> attendances)
	{
		/* Encapsulation. */
		this.attendances = new HashMap<>(attendances);
	}

	/**
	 * Represents an {@link Attendance} {@link List} as a {@link String}.
	 * Does so by joining all {@link Attendance#toString()}'s with comma.
	 *
	 * @param attendanceList The {@link Attendance} {@link List} to represent.
	 * @return The {@link String} representation.
	 */
	@NotNull
	public static String attendanceListAsString (@NotNull List<Attendance> attendanceList)
	{
		return String.join(", ", attendanceList.stream().map(Attendance::toString).toList());
	}

	/**
	 * @return All {@link Attendance attendances} for the provided day.
	 */
	@NotNull
	public List<Attendance> getAttendancesForDay (@NotNull LocalDate day)
	{
		/* Return an empty list when no data for the passed day is present. */
		if (!attendances.containsKey(day))
			return new LinkedList<>();

		/* Encapsulation. */
		return new LinkedList<>(attendances.get(day));
	}

	/**
	 * @return All days, this {@link Attendances} has data for.
	 */
	@NotNull
	public Set<LocalDate> getDays ()
	{
		/* Encapsulation */
		return new HashSet<>(attendances.keySet());
	}

	public static Builder builder ()
	{
		return new Builder();
	}

	public static class Builder
	{
		private final Map<LocalDate, List<Attendance>> attendances = new HashMap<>();

		/**
		 * Adds {@link Attendance}.
		 *
		 * @param day        The day to add this attendance to.
		 * @param attendance The {@link Attendance} to add.
		 */
		@NotNull
		public Builder addAttendance (@NotNull LocalDate day, @NotNull Attendance attendance)
		{
			/* Gets the list for the day, if none is present, creates a new list and puts it into the map. Then the attendance is added to that list. */
			this.attendances.computeIfAbsent(day, __ -> new LinkedList<>()).add(attendance);
			return this;
		}

		@NotNull
		public Attendances build ()
		{
			return new Attendances(attendances);
		}
	}
}