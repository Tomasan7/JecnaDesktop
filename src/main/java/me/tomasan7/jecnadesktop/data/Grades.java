package me.tomasan7.jecnadesktop.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record Grades(Map<String, List<Grade>> grades)
{
	public Grades ()
	{
		this(new HashMap<>());
	}

	public static class Builder
	{
		// TODO: Implement.
	}
}
