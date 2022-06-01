package me.tomasan7.jecnadesktop.repository

import io.ktor.http.*
import me.tomasan7.jecnadesktop.data.Grades
import me.tomasan7.jecnadesktop.data.SchoolYear
import me.tomasan7.jecnadesktop.parser.parsers.HtmlGradesParser
import me.tomasan7.jecnadesktop.util.JecnaPeriodEncoder
import me.tomasan7.jecnadesktop.util.JecnaPeriodEncoder.jecnaEncode
import me.tomasan7.jecnadesktop.web.JecnaWebClient
import me.tomasan7.jecnadesktop.web.append

/**
 * Retrieves [Grades] from the Ječná web.
 */
class WebGradesRepository(private val webClient: JecnaWebClient) : GradesRepository
{
    private val gradesParser = HtmlGradesParser()

    override suspend fun queryGrades() = gradesParser.parse(webClient.queryStringBody(WEB_PATH))

    override suspend fun queryGrades(schoolYear: SchoolYear, firstHalf: Boolean) = gradesParser.parse(webClient.queryStringBody(WEB_PATH, Parameters.build {
        append(schoolYear.jecnaEncode())
        append(JecnaPeriodEncoder.encodeSchoolYearHalf(firstHalf))
    }))

    companion object
    {
        private const val WEB_PATH = "/score/student"
    }
}