package me.tomasan7.jecnadesktop.parser.parsers;

import me.tomasan7.jecnadesktop.data.Grade;
import me.tomasan7.jecnadesktop.data.Grades;
import me.tomasan7.jecnadesktop.parser.ParseException;
import me.tomasan7.jecnadesktop.parser.Parser;
import me.tomasan7.jecnadesktop.util.RegexUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class GradesParserHTML implements Parser<Grades>
{
	/* Matches everything before last '(' preceded by a space. */
	private static final Pattern DESCRIPTION_REGEX = Pattern.compile("(.*)(?=( \\((?!\\()))"); // (.*)(?=( \((?!\()))

	/* Matches everything between last '(' and first ',' after it. */
	private static final Pattern DATE_REGEX = Pattern.compile("(?<=(\\((?!.*\\()))(.*(?=(?<!,.*)))"); // (?<=(\((?!.*\()))(.*(?=(?<!,.*)))

	/* Next regexes WIP: regexr.com/6i0j4 */

	@Override
	public Grades parse (String source)
	{
		try
		{
			Grades.Builder gradesBuilder = Grades.builder();

			Document document = Jsoup.parse(source);

			Elements rowEles = document.select(".score > tbody > tr");

			for (Element rowEle : rowEles)
			{
				Element subjectEle = rowEle.selectFirst("th");
				Elements gradeEles = rowEle.select("td > a.score:not(.scoreFinal)");

				for (Element gradeEle : gradeEles)
				{
					String valueString = gradeEle.selectFirst(".value").text();
					char value = valueString.charAt(0);
					boolean small = gradeEle.classNames().contains("scoreSmall");

					String titleAttr = gradeEle.attr("title");

					Grade.Builder gradeBuilder = Grade.builder(value, small);

					RegexUtils.findFirst(titleAttr, DESCRIPTION_REGEX).ifPresent(gradeBuilder::description);
					RegexUtils.findFirst(titleAttr, DATE_REGEX).ifPresent(dateStr -> gradeBuilder.receiveDate(LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd.MM.yyyy"))));
					// TODO: Parse teacher.

					gradesBuilder.addGrade(subjectEle.text(), gradeBuilder.build());
				}
			}

			return gradesBuilder.build();
		}
		catch (Exception e)
		{
			throw new ParseException();
		}
	}
}
