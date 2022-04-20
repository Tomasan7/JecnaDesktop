package me.tomasan7.jecnadesktop.data;

import java.time.LocalDate;
import java.util.*;

public class Attendances
{
	private final Map<LocalDate, List<Attendance>> attendances;

	private Attendances (Map<LocalDate, List<Attendance>> attendances)
	{
		/* Encapsulation. */
		this.attendances = new HashMap<>(attendances);
	}

	/**
	 * Represents a {@link List<Attendance> Attendance List} as a {@link String}. Does so by joining all {@link Attendance#toString()} with comma.
	 * @param attendanceList {@link List<Attendance> Attendance List} to represent.
	 * @return The {@link String} representation.
	 */
	public static String attendanceListAsString (List<Attendance> attendanceList)
	{
		return String.join(", ", attendanceList.stream().map(Attendance::toString).toList());
	}

	/**
	 * Returns all {@link Attendance attendances} for provided day.
	 * @param day The day to get all {@link Attendance attendances} from.
	 * @return All the {@link Attendance attendances} for the day.
	 */
	public List<Attendance> getAttendancesForDay (LocalDate day)
	{
		/* Return empty list when no data for the passed day is present. */
		if (!attendances.containsKey(day))
			return new ArrayList<>();

		/* Encapsulation. */
		return new ArrayList<>(attendances.get(day));
	}

	/**
	 * Returns all the days.
	 */
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
		 * @param day The day to add this attendance to.
		 * @param attendance The {@link Attendance} to add.
		 */
		public Builder addAttendance (LocalDate day, Attendance attendance)
		{
			/* Gets the list for the day, if none is present, creates a new list and puts it into the map. Then the attendance is added to that list. */
			this.attendances.computeIfAbsent(day, __ -> new ArrayList<>()).add(attendance);
			return this;
		}

		public Attendances build ()
		{
			return new Attendances(attendances);
		}
	}
}