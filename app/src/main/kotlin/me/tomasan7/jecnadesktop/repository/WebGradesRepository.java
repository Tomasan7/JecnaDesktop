package me.tomasan7.jecnadesktop.repository;

import me.tomasan7.jecnadesktop.data.Grades;
import me.tomasan7.jecnadesktop.parser.parsers.HTMLGradesParser;
import me.tomasan7.jecnadesktop.web.JecnaWebClient;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Retrieves {@link Grades} from the Ječná web.
 */
public class WebGradesRepository implements GradesRepository
{
	private static final String WEB_PATH = "/score/student";

	private final JecnaWebClient webClient;
	private final HTMLGradesParser gradesParser = new HTMLGradesParser();

	public WebGradesRepository (JecnaWebClient webClient)
	{
		this.webClient = webClient;
	}

	@Override
	public Grades queryGrades ()
	{
		try
		{
			return gradesParser.parse(webClient.queryStringBody(WEB_PATH).get());
		}
		catch (ExecutionException | InterruptedException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public CompletableFuture<Grades> queryGradesAsync ()
	{
		return webClient.queryStringBody(WEB_PATH)
						.thenApply(gradesParser::parse);
	}
}
