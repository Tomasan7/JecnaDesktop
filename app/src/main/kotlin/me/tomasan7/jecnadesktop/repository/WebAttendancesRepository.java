package me.tomasan7.jecnadesktop.repository;

import me.tomasan7.jecnadesktop.data.Attendances;
import me.tomasan7.jecnadesktop.parser.parsers.HtmlAttendancesParser;
import me.tomasan7.jecnadesktop.web.JecnaWebClient;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Retrieves {@link Attendances} from the Ječná web.
 */
public class WebAttendancesRepository implements AttendancesRepository
{
	private static final String WEB_PATH = "/absence/passing-student";

	private final JecnaWebClient webClient;
	private final HtmlAttendancesParser attendancesParser = new HtmlAttendancesParser();

	public WebAttendancesRepository (JecnaWebClient webClient)
	{
		this.webClient = webClient;
	}

	@Override
	public Attendances queryAttendances ()
	{
		try
		{
			return attendancesParser.parse(webClient.queryStringBody(WEB_PATH).get());
		}
		catch (ExecutionException | InterruptedException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public CompletableFuture<Attendances> queryAttendancesAsync ()
	{
		return webClient.queryStringBody(WEB_PATH)
						.thenApply(attendancesParser::parse);
	}
}
