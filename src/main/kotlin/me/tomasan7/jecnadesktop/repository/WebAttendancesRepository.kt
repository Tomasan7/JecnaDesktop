package me.tomasan7.jecnadesktop.repository

import io.ktor.http.*
import me.tomasan7.jecnadesktop.data.Attendances
import me.tomasan7.jecnadesktop.data.SchoolYear
import me.tomasan7.jecnadesktop.parser.parsers.HtmlAttendancesParser
import me.tomasan7.jecnadesktop.util.JecnaPeriodEncoder
import me.tomasan7.jecnadesktop.util.JecnaPeriodEncoder.jecnaEncode
import me.tomasan7.jecnadesktop.web.JecnaWebClient
import me.tomasan7.jecnadesktop.web.append

/**
 * Retrieves [Attendances] from the Ječná web.
 */
class WebAttendancesRepository(private val webClient: JecnaWebClient) : AttendancesRepository
{
    private val attendancesParser = HtmlAttendancesParser()

    override suspend fun queryAttendances() = attendancesParser.parse(webClient.queryStringBody(WEB_PATH))

    override suspend fun queryAttendances(schoolYear: SchoolYear, month: Int) =
        attendancesParser.parse(webClient.queryStringBody(WEB_PATH, Parameters.build {
            append(schoolYear.jecnaEncode())
            append(JecnaPeriodEncoder.encodeMonth(month))
        }))

    companion object
    {
        private const val WEB_PATH = "/absence/passing-student"
    }
}