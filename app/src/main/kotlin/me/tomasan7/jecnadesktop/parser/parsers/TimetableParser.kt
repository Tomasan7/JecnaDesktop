package me.tomasan7.jecnadesktop.parser.parsers

import me.tomasan7.jecnadesktop.data.Timetable

/**
 * Is responsible for parsing any kind of formatted [String] (eg. JSON, XML) to [Timetable] instance.
 */
interface TimetableParser
{
    /**
     * @throws me.tomasan7.jecnadesktop.parser.ParseException When the source isn't in correct format.
     */
    fun parse(source: String): Timetable
}