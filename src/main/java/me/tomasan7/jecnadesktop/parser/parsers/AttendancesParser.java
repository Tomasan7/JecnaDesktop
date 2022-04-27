package me.tomasan7.jecnadesktop.parser.parsers;

import me.tomasan7.jecnadesktop.data.Attendances;

/**
 * Is responsible for parsing any kind of formatted {@link String} (eg. JSON, XML) to {@link Attendances} instance.
 */
public interface AttendancesParser
{
	/**
	 * @throws me.tomasan7.jecnadesktop.parser.ParseException When the source isn't in correct format.
	 */
	Attendances parse (String source);
}
