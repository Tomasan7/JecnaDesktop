package me.tomasan7.jecnadesktop.web;

import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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
	@NotNull
	public CompletableFuture<Boolean> login ()
	{
		/* The user login request. */
		HttpRequest request = newRequest("/user/login")
				.POST(HttpRequest.BodyPublishers.ofString(encodeParams("user", auth.username(), "pass", auth.password())))
				.header("Content-Type", "application/x-www-form-urlencoded")
				.build();

		return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
						 .thenCompose(response ->
						 {
							 /* If the login was successful, web responds with a redirect status code. */
							 if (response.statusCode() != 302)
								 throw new RuntimeException("Login credentials are incorrect.");

							 /* This is what the redirect redirects to. */
							 HttpRequest requestMainPage = newRequest("/")
									 .GET()
									 .build();

							 return httpClient.sendAsync(requestMainPage, HttpResponse.BodyHandlers.ofString());
						 })
						 .handle((response, exception) ->
						 {
							 if (exception != null)
								 exception.printStackTrace();

							 return exception == null;
						 });
	}

	@Override
	@NotNull
	public CompletableFuture<String> query (String path)
	{
		return httpClient.sendAsync(newRequest(path).GET().build(), HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body);
	}

	/**
	 * Returns a new request to the path relative to {@link #ENDPOINT}.
	 * Adds a User-Agent header, since the web requires it. (uses Mozilla/5.0)
	 *
	 * @param path The path to query. Must include first slash.
	 * @return The new request.
	 */
	private HttpRequest.Builder newRequest (String path)
	{
		String uriStr = ENDPOINT + path;

		try
		{
			/* The web requires a User-Agent header, otherwise it responds to the login request with
			 * 403 - "The page you were looking for is not availible." (yes, it contains the grammar mistake) */

			return HttpRequest.newBuilder(new URI(uriStr))
							  .header("User-Agent", "Mozilla/5.0");
		}
		catch (URISyntaxException e)
		{
			throw new InvalidURIException(uriStr);
		}
	}
}
