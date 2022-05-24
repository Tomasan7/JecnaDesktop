package me.tomasan7.jecnadesktop.parser.parsers

import me.tomasan7.jecnadesktop.data.Attendances

/**
 * Is responsible for parsing any kind of formatted [String] (eg. JSON, XML) to [Attendances] instance.
 */
interface AttendancesParser
{
    /**
     * @throws me.tomasan7.jecnadesktop.parser.ParseException When the source isn't in correct format.
     */
    fun parse(source: String): Attendances
}