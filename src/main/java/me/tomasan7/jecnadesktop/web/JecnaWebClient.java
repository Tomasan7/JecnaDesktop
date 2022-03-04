package me.tomasan7.jecnadesktop.web;

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

	public JecnaWebClient (String username, String password)
	{
		super(username, password);
	}

	@Override
	public CompletableFuture<Boolean> login ()
	{
		try
		{
			URI loginURL = new URI(ENDPOINT + "/user/login");

			HttpClient httpClient = HttpClient.newHttpClient();

			HttpRequest request = HttpRequest.newBuilder(loginURL)
											 .POST(HttpRequest.BodyPublishers.ofString(encodeParams("user", username, "pass", password)))
											 .header("User-Agent", "Mozilla/5.0")
											 .header("Content-Type", "application/x-www-form-urlencoded")
											 .build();

			return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
							 .thenCompose(response ->
							 {
								 try
								 {
									 if (response.statusCode() != 302)
										 throw new RuntimeException("Login credentials are incorrect.");

									 Optional<String> cookieHeaderOpt = response.headers().firstValue("Set-Cookie");

									 if (cookieHeaderOpt.isEmpty())
										 throw new RuntimeException("Set-Cookie not found.");

									 session = JecnaSession.fromHeader(cookieHeaderOpt.get());

									 HttpRequest requestMainPage = HttpRequest.newBuilder(new URI(ENDPOINT))
																			  .GET()
																			  .header("User-Agent", "Mozilla/5.0")
																			  .header("Cookie", response.headers().firstValue("Set-Cookie").get())
																			  .build();

									 return httpClient.sendAsync(requestMainPage, HttpResponse.BodyHandlers.ofString());
								 }
								 catch (URISyntaxException e)
								 {
									 throw new RuntimeException();
								 }
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
		catch (Exception e)
		{
			e.printStackTrace();
			return CompletableFuture.completedFuture(false);
		}
	}
}
