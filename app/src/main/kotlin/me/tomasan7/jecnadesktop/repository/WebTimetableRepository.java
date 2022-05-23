package me.tomasan7.jecnadesktop.repository;

import me.tomasan7.jecnadesktop.data.Timetable;
import me.tomasan7.jecnadesktop.parser.parsers.HTMLTimetableParser;
import me.tomasan7.jecnadesktop.web.JecnaWebClient;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Retrieves {@link Timetable} from the Ječná web.
 */
public class WebTimetableRepository implements TimetableRepository
{
	private static final String WEB_PATH = "/timetable/class";

	private final JecnaWebClient webClient;
	private final HTMLTimetableParser timetableParser = new HTMLTimetableParser();

	public WebTimetableRepository (JecnaWebClient webClient)
	{
		this.webClient = webClient;
	}

	@Override
	public Timetable queryTimetable ()
	{
		try
		{
			return timetableParser.parse(webClient.query(WEB_PATH).get());
		}
		catch (ExecutionException | InterruptedException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	public CompletableFuture<Timetable> queryTimetableAsync ()
	{
		return webClient.query(WEB_PATH)
						.thenApply(timetableParser::parse);
	}
}
