package me.tomasan7.jecnadesktop.parser.parsers;

import me.tomasan7.jecnadesktop.data.Timetable;

/**
 * Is responsible for parsing any kind of formatted {@link String} (eg. JSON, XML) to {@link Timetable} instance.
 */
public interface TimetableParser
{
	/**
	 * @throws me.tomasan7.jecnadesktop.parser.ParseException When the source isn't in correct format.
	 */
	Timetable parse (String source);
}
