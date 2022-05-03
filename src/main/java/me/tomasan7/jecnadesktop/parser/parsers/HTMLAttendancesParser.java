package me.tomasan7.jecnadesktop.parser.parsers;

import me.tomasan7.jecnadesktop.data.Attendance;
import me.tomasan7.jecnadesktop.data.Attendances;
import me.tomasan7.jecnadesktop.parser.ParseException;
import me.tomasan7.jecnadesktop.util.RegexUtils;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.regex.Pattern;

/**
 * Parses correct HTML to {@link Attendances} instance.
 */
public class HTMLAttendancesParser implements AttendancesParser
{
	/* Matches a date in 'dd.MM.' format. (for speed and simplicity, it also matches non-existing dates) */
	private static final Pattern DATE_REGEX = Pattern.compile("[0-3]?\\d\\.[0-1]?\\d\\.");

	/* Matches whole string if it contains "Odchod" => if it's a leave. */
	private static final Pattern LEAVE_REGEX = Pattern.compile(".*Odchod.*", Pattern.DOTALL);

	/* Matches time in hh:mm format. */
	private static final Pattern TIME_REGEX = Pattern.compile("([0-1]?[0-9]|2[0-3]):[0-5][0-9]");

	@Override
	@NotNull
	public Attendances parse (String source)
	{
		try
		{
			Attendances.Builder attendancesBuilder = Attendances.builder();

			Document document = Jsoup.parse(source);
			/* All the rows (tr) in the absence table. */
			Elements rowEles = document.select(".tab.absence-list > tbody > tr");

			for (Element rowEle : rowEles)
			{
				/* The first column in the row, which contains the day date. */
				Element dayEle = rowEle.selectFirst(".date");
				LocalDate day = parseDayDate(dayEle.text(), document);
				/* The second column in the row, which contains all the attendances in one text. */
				Elements attendancesEle = rowEle.select("td:not(.date)");
				/* A string containing all enters/leaves in a day. */
				String attendancesStr = attendancesEle.text();
				/* Represents a single entry/leave in the day. */
				String[] dayAttendances = attendancesStr.split(", ");

				for (String dayAttendanceStr : dayAttendances)
				{
					/* Don't save the days, which have no presence. */
					if (dayAttendanceStr.isBlank())
						continue;

					/* If the attendance matches regex, then it is an exit. */
					boolean exit = RegexUtils.findFirst(dayAttendanceStr, LEAVE_REGEX).isPresent();
					/* Find the time and parse it to the LocalTime object. */
					LocalTime time = LocalTime.parse(RegexUtils.findFirst(dayAttendanceStr, TIME_REGEX).get(), DateTimeFormatter.ofPattern("H:mm"));
					attendancesBuilder.addAttendance(day, new Attendance(exit, time));
				}
			}

			return attendancesBuilder.build();
		}
		catch (Exception e)
		{
			throw new ParseException(e);
		}
	}

	/**
	 * Parses a date from the string and then uses the data from a different part of the page to determine the year.
	 * @param dayEleText The text to parse the date from.
	 * @param document The document, because the method needs to find the year on the page.
	 * @return The parsed {@link LocalDate} object.
	 */
	private LocalDate parseDayDate (String dayEleText, Document document)
	{
		String date = RegexUtils.findFirst(dayEleText, DATE_REGEX).get();
		/* First index with the day and the second with the month. */
		String[] dateSplit = date.split("\\.");
		byte monthOfYear = Byte.parseByte(dateSplit[1]);

		/* First index with the first year and the second with the second. (eg. 2021/2022) */
		String[] schoolYear = document.selectFirst("#schoolYearId > option").text().split("/");
		short firstYear = Short.parseShort(schoolYear[0]);
		short secondYear = Short.parseShort(schoolYear[1]);
		short resultYear = -1;

		/* If the month is earlier than or same as August, use the second calendar year, the first otherwise.  */
		if (monthOfYear <= 8)
			resultYear = secondYear;
		else
			resultYear = firstYear;

		return LocalDate.parse(date, new DateTimeFormatterBuilder()
				.appendPattern("d.M.")
				.parseDefaulting(ChronoField.YEAR, resultYear)
				.toFormatter());
	}
}
