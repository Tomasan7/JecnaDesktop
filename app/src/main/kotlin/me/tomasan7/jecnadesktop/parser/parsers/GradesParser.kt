package me.tomasan7.jecnadesktop.parser.parsers

import me.tomasan7.jecnadesktop.data.Grades

/**
 * Is responsible for parsing any kind of formatted [String] (eg. JSON, XML) to [Grades] instance.
 */
interface GradesParser
{
    /**
     * @throws me.tomasan7.jecnadesktop.parser.ParseException When the source isn't in correct format.
     */
    fun parse(source: String): Grades
}