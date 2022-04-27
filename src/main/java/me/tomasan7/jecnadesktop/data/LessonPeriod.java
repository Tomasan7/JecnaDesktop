package me.tomasan7.jecnadesktop.data;

import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a {@link Lesson lesson's} time period in a timetable.
 * @param from Lesson's starting time.
 * @param to Lesson's ending time.
 */
public record LessonPeriod(@NotNull LocalTime from, @NotNull LocalTime to)
{
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");

	@Override
	public String toString ()
	{
		return from.format(formatter) + " - " + to.format(formatter);
	}

	/**
	 * Parses {@link LessonPeriod} from {@link String}. <b>The {@link String} must be in a "HH:mm - HH:mm" format.</b>
	 * @param string The {@link String} to parse from.
	 * @throws IllegalArgumentException When the provided {@link String} is in incorrect format.
	 * @return The parsed {@link LessonPeriod}.
	 */
	@NotNull
	public static LessonPeriod fromString (@NotNull String string)
	{
		LocalTime from = null;
		LocalTime to = null;

		String[] split = string.split(" - ");

		try
		{
			from = LocalTime.parse(split[0], formatter);
			to = LocalTime.parse(split[1], formatter);
		}
		catch (IndexOutOfBoundsException e)
		{
			throw new IllegalArgumentException("Provided string wasn't in correct format. Expected format \"HH:mm - HH:mm\", got \"" + string + "\".");
		}

		return new LessonPeriod(from, to);
	}
}
