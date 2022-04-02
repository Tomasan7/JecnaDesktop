package me.tomasan7.jecnadesktop.web;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public abstract class AuthWebClient
{
	@NotNull
	protected final Auth auth;
	@Nullable
	protected Session session = null;

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
	public abstract CompletableFuture<Boolean> login ();

	/**
	 * Returns an HTML from the web on the given path. May vary depending on whether user is logged in or not.
	 * @param path Relative path from the domain. Must include first slash.
	 * @return The HTML.
	 */
	public abstract CompletableFuture<String> queryHTML (String path);

	protected static String encodeParams (Map<String, String> params)
	{
		StringBuilder stringBuilder = new StringBuilder();

		int i = 0;

		for (Map.Entry<String, String> entry : params.entrySet())
		{
			stringBuilder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8))
						 .append('=')
						 .append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));

			/* Add & if this is not the last parameter. */
			if (i <= params.size() - 1)
				stringBuilder.append('&');
		}

		return stringBuilder.toString();
	}

	protected static String encodeParams (String... params)
	{
		if (params.length % 2 != 0)
			throw new RuntimeException("Received even number of parameters - cannot assign their values.");

		Map<String, String> paramsMap = new HashMap<>();

		for (int i = 0; i < params.length; i += 2)
			paramsMap.put(params[i], params[i + 1]);

		return encodeParams(paramsMap);
	}

	/**
	 * Returns the client's session.
	 */
	@Nullable
	public Session getSession ()
	{
		return session;
	}
}
