package me.tomasan7.jecnadesktop.data;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public record Grades(Map<String, List<Grade>> grades)
{
	public Grades ()
	{
		this(new HashMap<>());
	}

	public static Builder builder ()
	{
		return new Builder();
	}

	public static class Builder
	{
		private final Map<String, List<Grade>> grades = new HashMap<>();

		public void addGrade (@NotNull String subject, @NotNull Grade grade)
		{
			/* Gets the list for the subject, if none is present, creates a new list and puts it into the map. Then the grade is added to that list. */
			grades.computeIfAbsent(subject, k -> new LinkedList<>()).add(grade);
		}

		public Grades build ()
		{
			return new Grades(grades);
		}
	}
}
