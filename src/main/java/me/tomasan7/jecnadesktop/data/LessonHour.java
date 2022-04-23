package me.tomasan7.jecnadesktop.data;

import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public record LessonHour(LocalTime from, LocalTime to)
{
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");

	@Override
	public String toString ()
	{
		return from.format(formatter) + " - " + to.format(formatter);
	}

	/**
	 * Parses {@link LessonHour} from {@link String}. <b>The {@link String} must be in a "HH:mm - HH:mm" format.</b>
	 * @param string The {@link String} to parse from.
	 * @return The {@link LessonHour}.
	 */
	public static LessonHour fromString (@NotNull String string)
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

		return new LessonHour(from, to);
	}
}
