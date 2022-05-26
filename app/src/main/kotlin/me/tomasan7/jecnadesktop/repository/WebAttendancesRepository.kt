package me.tomasan7.jecnadesktop.repository

import me.tomasan7.jecnadesktop.data.Attendances
import me.tomasan7.jecnadesktop.parser.parsers.HtmlAttendancesParser
import me.tomasan7.jecnadesktop.web.JecnaWebClient

/**
 * Retrieves [Attendances] from the Ječná web.
 */
class WebAttendancesRepository(private val webClient: JecnaWebClient) : AttendancesRepository
{
    private val attendancesParser = HtmlAttendancesParser()

    override suspend fun queryAttendances() = attendancesParser.parse(webClient.queryStringBody(WEB_PATH))

    companion object
    {
        private const val WEB_PATH = "/absence/passing-student"
    }
}