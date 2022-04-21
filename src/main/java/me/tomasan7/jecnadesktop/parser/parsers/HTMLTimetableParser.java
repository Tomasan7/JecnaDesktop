package me.tomasan7.jecnadesktop.parser.parsers;

import me.tomasan7.jecnadesktop.data.Lesson;
import me.tomasan7.jecnadesktop.data.LessonHour;
import me.tomasan7.jecnadesktop.data.Timetable;
import me.tomasan7.jecnadesktop.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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

			/* The row (tr) containing all the LessonHours details.
			 * Each LessonHour is a 'th' with class 'period'. */
			Element lessonHoursEle = rowEles.get(0);

			/* All the LessonHour elements.
			 * Each element has an inner text with the index of the hour
			 * and a span (with class 'time') containing the time period. */
			Elements lessonHourEles = lessonHoursEle.select("th.period");

			/* Add all the LessonHours to the timetable. */
			lessonHourEles.forEach(lessonHourEle -> timetableBuilder.addLessonHour(LessonHour.fromString(lessonHourEle.selectFirst(".time").text())));

			/* Removes the row with the LessonHours, so it leaves all the subjects. */
			rowEles.remove(0);

			for (Element rowEle : rowEles)
			{
				String day = rowEle.selectFirst(".day").text();

				/* All the lessons in this day. */
				Elements lessonEles = rowEle.select("td");

				for (Element lessonEle : lessonEles)
				{
					/* Skip if the lesson is empty, this leaves the lesson variable to null -> indicating no lesson. */
					if (lessonEle.hasClass("empty")
							/* This means that the lesson is split into 2 or more groups and that isn't supported yet. */
						|| lessonEle.children().size() > 1)
					{
						timetableBuilder.addLesson(day, null);
						continue;
					}

					/* This is the first lesson out of x, but since split lessons aren't supported yet, this will be the only one. */
					Element lesson1Ele = lessonEle.child(0);

					timetableBuilder.addLesson(day, new Lesson(
							lesson1Ele.selectFirst(".subject").attr("title"),
							lesson1Ele.selectFirst(".subject").text(),
							lesson1Ele.selectFirst(".employee").attr("title"),
							lesson1Ele.selectFirst(".employee").text(),
							lesson1Ele.selectFirst(".room").text()
					));
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
