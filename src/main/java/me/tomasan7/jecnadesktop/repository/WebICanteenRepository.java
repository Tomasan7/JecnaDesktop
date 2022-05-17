package me.tomasan7.jecnadesktop.repository;

import me.tomasan7.jecnadesktop.data.Menu;
import me.tomasan7.jecnadesktop.data.MenuItem;
import me.tomasan7.jecnadesktop.parser.parsers.HTMLMenuParser;
import me.tomasan7.jecnadesktop.web.ICanteenWebClient;
import org.jsoup.Jsoup;

import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class WebICanteenRepository implements ICanteenRepository
{
	private static final String WEB_PATH = "/faces/secured/mobile.jsp";

	private final ICanteenWebClient webClient;
	private final HTMLMenuParser menuParser = new HTMLMenuParser();

	public WebICanteenRepository (ICanteenWebClient webClient)
	{
		this.webClient = webClient;
	}

	@Override
	public Menu queryMenu ()
	{
		return menuParser.parse(webClient.query(WEB_PATH).join());
	}

	@Override
	public CompletableFuture<Menu> queryMenuAsync ()
	{
		return webClient.query(WEB_PATH)
						.thenApply(menuParser::parse);
	}

	@Override
	public boolean orderMenuItem (MenuItem menuItem)
	{
		return webClient.queryResponse("/faces/secured/" + menuItem.orderURL()).join().statusCode() == 200;
	}
}
