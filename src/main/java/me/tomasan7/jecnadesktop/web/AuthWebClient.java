package me.tomasan7.jecnadesktop.web;

import org.jetbrains.annotations.NotNull;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public abstract class AuthWebClient
{
	@NotNull
	protected final Auth auth;
	@NotNull
	protected CookieHandler cookieHandler = new CookieManager();
	@NotNull
	protected HttpClient httpClient = HttpClient.newBuilder()
												.cookieHandler(cookieHandler).build();

	public AuthWebClient (@NotNull Auth auth)
	{
		this.auth = auth;
	}

	public AuthWebClient (@NotNull String username, @NotNull String password)
	{
		this.auth = new Auth(username, password);
	}

	/**
	 * Logins the client.
	 *
	 * @return True if login was successful, false otherwise.
	 */
	@NotNull
	public abstract CompletableFuture<Boolean> login ();

	/**
	 * Returns an HTML from the web on the given path. May vary depending on whether user is logged in or not.
	 *
	 * @param path Relative path from the domain. Must include first slash.
	 * @return The HTML.
	 */
	@NotNull
	public abstract CompletableFuture<String> query (String path);

	@NotNull
	protected static String encodeParams (Map<String, String> params)
	{
		return params.entrySet().stream()
					 .map(e -> e.getKey() + "=" + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
					 .collect(Collectors.joining("&"));
	}

	@NotNull
	protected static String encodeParams (String... params)
	{
		if (params.length % 2 != 0)
			throw new RuntimeException("Received even number of parameters - cannot assign their values.");

		Map<String, String> paramsMap = new HashMap<>();

		for (int i = 0; i < params.length; i += 2)
			paramsMap.put(params[i], params[i + 1]);

		return encodeParams(paramsMap);
	}
}
