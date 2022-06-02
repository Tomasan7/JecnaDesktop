package me.tomasan7.jecnadesktop.repository

import io.ktor.http.*
import me.tomasan7.jecnadesktop.data.SchoolYear
import me.tomasan7.jecnadesktop.data.Timetable
import me.tomasan7.jecnadesktop.parser.parsers.HtmlTimetableParser
import me.tomasan7.jecnadesktop.util.JecnaPeriodEncoder.jecnaEncode
import me.tomasan7.jecnadesktop.web.JecnaWebClient
import me.tomasan7.jecnadesktop.web.append

/**
 * Retrieves [Timetable] from the Ječná web.
 */
class WebTimetableRepository(private val webClient: JecnaWebClient) : TimetableRepository
{
    private val timetableParser = HtmlTimetableParser()

    override suspend fun queryTimetable() = timetableParser.parse(webClient.queryStringBody(WEB_PATH))

    override suspend fun queryTimetable(schoolYear: SchoolYear) =
        timetableParser.parse(webClient.queryStringBody(WEB_PATH, Parameters.build {
            append(schoolYear.jecnaEncode())
        }))

    companion object
    {
        private const val WEB_PATH = "/timetable/class"
    }
}