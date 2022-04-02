package me.tomasan7.jecnadesktop.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils
{
	/**
	 * Returns a {@link List<String>} of all matches inside the provided string.
	 * @param source String to search in.
	 * @param regex The regex to search with.
	 * @return The list of all matches.
	 */
	public static List<String> find (String source, Pattern regex)
	{
		Matcher matcher = regex.matcher(source);

		List<String> findings = new ArrayList<>();

		while (matcher.find())
			findings.add(matcher.group());

		return findings;
	}

	/**
	 * Returns a {@link List<String>} of all matches inside the provided string.
	 * @param source String to search in.
	 * @param regex The regex to search with.
	 * @return The list of all matches.
	 */
	public static List<String> find (String source, String regex)
	{
		return find(source, Pattern.compile(regex));
	}

	/**
	 * Returns first match of a regex inside the provided string.
	 * @param source String to search in.
	 * @param regex The regex to search with.
	 * @return The first match.
	 */
	public static Optional<String> findFirst (String source, Pattern regex)
	{
		Matcher matcher = regex.matcher(source);

		if (matcher.find())
			return Optional.of(matcher.group());
		else
			return Optional.empty();
	}

	/**
	 * Returns first match of a regex inside the provided string.
	 * @param source String to search in.
	 * @param regex The regex to search with.
	 * @return The first match.
	 */
	public static Optional<String> findFirst (String source, String regex)
	{
		return findFirst(source, Pattern.compile(regex));
	}
}
