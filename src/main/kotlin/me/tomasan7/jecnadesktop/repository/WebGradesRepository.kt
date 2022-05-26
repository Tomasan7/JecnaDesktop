package me.tomasan7.jecnadesktop.repository

import me.tomasan7.jecnadesktop.data.Grades
import me.tomasan7.jecnadesktop.parser.parsers.HtmlGradesParser
import me.tomasan7.jecnadesktop.web.JecnaWebClient

/**
 * Retrieves [Grades] from the Ječná web.
 */
class WebGradesRepository(private val webClient: JecnaWebClient) : GradesRepository
{
    private val gradesParser = HtmlGradesParser()

    override suspend fun queryGrades() = gradesParser.parse(webClient.queryStringBody(WEB_PATH))

    companion object
    {
        private const val WEB_PATH = "/score/student"
    }
}