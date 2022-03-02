package me.tomasan7.jecnadesktop.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grades
{
	private final Map<String, List<Grade>> grades;

	public Grades (Map<String, List<Grade>> grades)
	{
		this.grades = grades;
	}

	public Grades ()
	{
		this.grades = new HashMap<>();
	}

	public static class Builder
	{
		// TODO: Implement.
	}
}
