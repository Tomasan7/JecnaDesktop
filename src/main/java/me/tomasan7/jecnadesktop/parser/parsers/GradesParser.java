package me.tomasan7.jecnadesktop.parser.parsers;

import me.tomasan7.jecnadesktop.data.Grades;

/**
 * Is responsible for parsing any kind of formatted {@link String} (eg. JSON, XML) to {@link Grades} instance.
 */
public interface GradesParser
{
	Grades parse (String source);
}
