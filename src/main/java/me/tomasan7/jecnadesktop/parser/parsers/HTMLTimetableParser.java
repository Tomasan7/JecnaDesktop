package me.tomasan7.jecnadesktop.parser.parsers;

import me.tomasan7.jecnadesktop.data.Lesson;
import me.tomasan7.jecnadesktop.data.LessonPeriod;
import me.tomasan7.jecnadesktop.data.LessonSpot;
import me.tomasan7.jecnadesktop.data.Timetable;
import me.tomasan7.jecnadesktop.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses correct HTML to {@link Timetable} instance.
 */
public class HTMLTimetableParser implements TimetableParser
{
	@Override
	public Timetable parse (String source)
	{
		try
		{
			Timetable.Builder timetableBuilder = Timetable.builder();

			Document document = Jsoup.parse(source);

			/* All the rows (tr) in the grades table. */
			Elements rowEles = document.select("table.timetable > tbody > tr");

			/* The row (tr) containing all the LessonPeriods details.
			 * Each LessonPeriod is a 'th' with class 'period'. */
			Element lessonPeriodsEle = rowEles.get(0);

			/* All the LessonPeriod elements.
			 * Each element has an inner text with the index of the hour
			 * and a span (with class 'time') containing the time period. */
			Elements lessonPeriodEles = lessonPeriodsEle.select("th.period");

			/* Add all the LessonPeriods to the timetable. */
			lessonPeriodEles.forEach(lessonHourEle -> timetableBuilder.addLessonPeriod(LessonPeriod.fromString(lessonHourEle.selectFirst(".time").text())));

			/* Removes the row with the LessonPeriods, so it leaves all the subjects. */
			rowEles.remove(0);

			for (Element rowEle : rowEles)
			{
				String day = rowEle.selectFirst(".day").text();

				/* All the LessonSpots in this day. */
				Elements lessonSpotEles = rowEle.select("td");

				for (Element lessonSpotEle : lessonSpotEles)
				{
					/* Skip if the lesson spot is empty, this leaves the lesson variable to null -> indicating no lesson. */
					if (lessonSpotEle.hasClass("empty"))
					{
						timetableBuilder.addLesson(day, null);
						continue;
					}

					/* All the lessons in the lesson spot. */
					Elements lessonEles = lessonSpotEle.getElementsByTag("div");

					List<Lesson> lessons = new ArrayList<>();

					for (Element lessonEle : lessonEles)
					{
						int group = 0;
						Element groupEle = lessonEle.selectFirst(".group");
						if (groupEle != null)
							group = Integer.parseInt(groupEle.text().split("/")[0]);

						lessons.add(new Lesson(
								lessonEle.selectFirst(".subject").attr("title"),
								lessonEle.selectFirst(".subject").text(),
								lessonEle.selectFirst(".employee").attr("title"),
								lessonEle.selectFirst(".employee").text(),
								lessonEle.selectFirst(".room").text(),
								group
						));
					}

					timetableBuilder.addLessonSpot(day, new LessonSpot(lessons));
				}
			}

			return timetableBuilder.build();
		}
		catch (Exception e)
		{
			throw new ParseException(e);
		}
	}
}
