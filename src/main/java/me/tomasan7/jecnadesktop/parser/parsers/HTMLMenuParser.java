package me.tomasan7.jecnadesktop.parser.parsers;

import me.tomasan7.jecnadesktop.data.DayMenu;
import me.tomasan7.jecnadesktop.data.Menu;
import me.tomasan7.jecnadesktop.data.MenuItem;
import me.tomasan7.jecnadesktop.parser.ParseException;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.FormElement;
import org.jsoup.select.Elements;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HTMLMenuParser implements MenuParser
{
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");

	@NotNull
	@Override
	public Menu parse (String html)
	{
		try
		{
			Menu.Builder menuBuilder = Menu.builder();

			Document document = Jsoup.parse(html);

			List<FormElement> formEles = document.select("#mainContext > table > tbody > tr > td > form").forms();

			for (FormElement form : formEles)
			{

				String dayStr = form.selectFirst("div > strong > .important").text();
				LocalDate day = LocalDate.parse(dayStr, DATE_FORMAT);

				DayMenu.Builder dayMenuBuilder = DayMenu.builder(day);

				Elements menuItemEles = form.select(".orderContent > div > div");

				for (Element menuItemEle : menuItemEles)
				{
					Elements eles = menuItemEle.select("> span > span");

					Element orderButtonEle = eles.get(0).selectFirst("a");

					Element foodNameEle = eles.get(1);
					String foodName = foodNameEle.ownText();

					if (foodName.isBlank())
						continue;

					String allergensText = foodNameEle.selectFirst(".textGrey").text();
					String allergens = allergensText.substring(1, allergensText.length() - 1);

					String onclick = orderButtonEle.attr("onclick");
					dayMenuBuilder.addItem(new MenuItem(foodName,
														allergens,
														orderButtonEle.hasClass("enabled"),
														orderButtonEle.hasClass("ordered"),
														onclick.substring(90, onclick.length() - 29)));
				}

				DayMenu dayMenu = dayMenuBuilder.build();

				if (dayMenu.itemsCount() != 0)
					menuBuilder.addDayMenu(dayMenu);
			}

			return menuBuilder.build();
		}
		catch (Exception e)
		{
			throw new ParseException(e);
		}
	}
}