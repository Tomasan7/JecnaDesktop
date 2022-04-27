package me.tomasan7.jecnadesktop.parser.parsers;

import me.tomasan7.jecnadesktop.data.Grades;

/**
 * Is responsible for parsing any kind of formatted {@link String} (eg. JSON, XML) to {@link Grades} instance.
 */
public interface GradesParser
{
	/**
	 * @throws me.tomasan7.jecnadesktop.parser.ParseException When the source isn't in correct format.
	 */
	Grades parse (String source);
}
