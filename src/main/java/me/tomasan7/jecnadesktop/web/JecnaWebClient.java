package me.tomasan7.jecnadesktop.web;

import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class JecnaWebClient extends AuthWebClient
{
	public static final String ENDPOINT = "https://www.spsejecna.cz";

	public JecnaWebClient (@NotNull Auth auth)
	{
		super(auth);
	}

	public JecnaWebClient (@NotNull String username, @NotNull String password)
	{
		super(username, password);
	}

	@Override
	public CompletableFuture<Boolean> login ()
	{
		HttpClient httpClient = HttpClient.newHttpClient();

		HttpRequest request = newRequest("/user/login")
				.POST(HttpRequest.BodyPublishers.ofString(encodeParams("user", auth.username(), "pass", auth.password())))
				.header("Content-Type", "application/x-www-form-urlencoded")
				.build();

		return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
						 .thenCompose(response ->
						 {
							 if (response.statusCode() != 302)
								 throw new RuntimeException("Login credentials are incorrect.");

							 Optional<String> cookieHeaderOpt = response.headers().firstValue("Set-Cookie");

							 if (cookieHeaderOpt.isEmpty())
								 throw new RuntimeException("Set-Cookie not found.");

							 session = JecnaSession.fromHeader(cookieHeaderOpt.get());

							 HttpRequest requestMainPage = newRequest("/")
									 .GET()
									 .build();

							 return httpClient.sendAsync(requestMainPage, HttpResponse.BodyHandlers.ofString());
						 })
						 .handle((response, exception) ->
						 {
							 if (exception != null)
								 return false;

							 Document document = Jsoup.parse(response.body());
							 String title = document.select("head > title").text();

							 return title.equals("SPŠE Ječná - Novinky");
						 });
	}

	@Override
	public CompletableFuture<String> queryHTML (String path)
	{
		return HttpClient.newHttpClient().sendAsync(newRequest(path).GET().build(), HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body);
	}

	/**
	 * Returns a new request to the path relative to {@link #ENDPOINT}. Adds {@link #session} cookie, if it's not null.
	 *
	 * @param path The path to query. Must include first slash.
	 * @return The new request.
	 */
	private HttpRequest.Builder newRequest (String path)
	{
		String uriStr = ENDPOINT + path;

		try
		{
			HttpRequest.Builder builder = HttpRequest.newBuilder(new URI(uriStr))
													 .header("User-Agent", "Mozilla/5.0");

			if (session != null)
				builder.header("Cookie", session.toHeader());

			return builder;
		}
		catch (URISyntaxException e)
		{
			throw new InvalidURIException(uriStr);
		}
	}
}
