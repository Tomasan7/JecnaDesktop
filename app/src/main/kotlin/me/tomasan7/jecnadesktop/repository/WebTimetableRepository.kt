package me.tomasan7.jecnadesktop.repository

import me.tomasan7.jecnadesktop.data.Timetable
import me.tomasan7.jecnadesktop.parser.parsers.HtmlTimetableParser
import me.tomasan7.jecnadesktop.web.JecnaWebClient

/**
 * Retrieves [Timetable] from the Ječná web.
 */
class WebTimetableRepository(private val webClient: JecnaWebClient) : TimetableRepository
{
    private val timetableParser = HtmlTimetableParser()

    override suspend fun queryTimetable() = timetableParser.parse(webClient.queryStringBody(WEB_PATH))

    companion object
    {
        private const val WEB_PATH = "/timetable/class"
    }
}