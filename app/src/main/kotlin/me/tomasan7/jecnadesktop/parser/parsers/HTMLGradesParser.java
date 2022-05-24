package me.tomasan7.jecnadesktop.parser.parsers;

import me.tomasan7.jecnadesktop.data.Grade;
import me.tomasan7.jecnadesktop.data.Grades;
import me.tomasan7.jecnadesktop.parser.ParseException;
import me.tomasan7.jecnadesktop.util.RegexUtils;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Parses correct HTML to {@link Grades} instance.
 * <b>Beware: The grade's subject is taken from the table's row name, not from the grade's page!</b>
 */
public class HTMLGradesParser implements GradesParser
{
	/* Matches everything before last '(' preceded by a space. */
	private static final Pattern DESCRIPTION_REGEX = Pattern.compile(".*(?= \\((?!.*\\())", Pattern.DOTALL);

	/* Matches everything between last '(' and first ',' after it. */
	private static final Pattern DATE_REGEX = Pattern.compile("(?<=\\((?!.*\\())[^,]*(?=,)", Pattern.DOTALL);

	/* Matches everything between the first ',' followed by a space after last '(' and ending ')' */
	private static final Pattern TEACHER_REGEX = Pattern.compile("(?<=(?<=\\((?!.*\\()[^,]*), ).*(?=\\)$)", Pattern.DOTALL);

	@Override
	@NotNull
	public Grades parse (String source)
	{
		try
		{
			Grades.Builder gradesBuilder = Grades.builder();

			Document document = Jsoup.parse(source);

			/* All the rows (tr) in the grades table. */
			Elements rowEles = document.select(".score > tbody > tr");

			for (Element rowEle : rowEles)
			{
				/* The first column in the row, which contains the subject name. */
				Element subjectEle = rowEle.selectFirst("th");
				/* All the grade elements in the second column of the row. (not finalGrades) */
				Elements gradeEles = rowEle.select("td > a.score:not(.scoreFinal)");

				for (Element gradeEle : gradeEles)
				{
					String valueString = gradeEle.selectFirst(".value").text();
					char value = valueString.charAt(0);
					boolean small = gradeEle.classNames().contains("scoreSmall");

					/* The title attribute of the grade element, which contains all the details. (description, date and teacher) */
					String titleAttr = gradeEle.attr("title");

					String subject = subjectEle.text();
					String teacher = RegexUtils.findFirst(titleAttr, TEACHER_REGEX).orElse(null);
					String description = RegexUtils.findFirst(titleAttr, DESCRIPTION_REGEX).orElse(null);
					LocalDate receiveDate = RegexUtils.findFirst(titleAttr, DATE_REGEX).map(s -> LocalDate.parse(s, DateTimeFormatter.ofPattern("dd.MM.yyyy"))).orElse(null);

					gradesBuilder.addGrade(subjectEle.text(), new Grade(value, small, subject, teacher, description, receiveDate));
				}
			}

			return gradesBuilder.build();
		}
		catch (Exception e)
		{
			throw new ParseException(e);
		}
	}
}
