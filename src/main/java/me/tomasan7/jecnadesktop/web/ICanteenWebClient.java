package me.tomasan7.jecnadesktop.web;

import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class ICanteenWebClient extends AuthWebClient
{
	private static final String ENDPOINT = "https://objednavky.jidelnasokolska.cz";

	public ICanteenWebClient (@NotNull Auth auth)
	{
		super(auth);
	}

	public ICanteenWebClient (@NotNull String username, @NotNull String password)
	{
		super(username, password);
	}

	@Override
	@NotNull
	public CompletableFuture<Boolean> login ()
	{
		return httpClient.sendAsync(newRequest("/faces/login.jsp")
										 .GET()
										 .build(),
								 HttpResponse.BodyHandlers.ofString())
						 .thenCompose(response ->
						 {
							 /* The user login request. */
							 HttpRequest request = newRequest("/j_spring_security_check")
									 .POST(HttpRequest.BodyPublishers.ofString(encodeParams(
											 "j_username", auth.username(),
											 "j_password", auth.password(),
											 "terminal", "false",
											 "type", "web",
											 "_csrf", Jsoup.parse(response.body()).select("#signup-user-col > div > form > ul > li > input[name=_csrf]").attr("value"),
											 "targetUrl", "/faces/secured/main.jsp?terminal=false&status=true&printer=&keyboard=")))
									 .header("Content-Type", "application/x-www-form-urlencoded")
									 .build();

							 return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
						 })
						 .thenAccept(response ->
						 {
							 /* If the login was unsuccessful, the web redirects back to the login page. */
							 if (response.headers().firstValue("Location").get().startsWith("/faces/login.jsp"))
								 throw new RuntimeException("Login credentials are incorrect.");
						 })
						 .handle((__, exception) ->
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
		return HttpClient.newHttpClient().sendAsync(newRequest(path).GET().build(), HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body);
	}


	/**
	 * Returns a new request to the path relative to {@link #ENDPOINT}.
	 *
	 * @param path The path to query. Must include first slash.
	 * @return The new request.
	 */
	private HttpRequest.Builder newRequest (String path)
	{
		String uriStr = ENDPOINT + path;

		try
		{
			return HttpRequest.newBuilder(new URI(uriStr));
		}
		catch (URISyntaxException e)
		{
			throw new InvalidURIException(uriStr);
		}
	}
}