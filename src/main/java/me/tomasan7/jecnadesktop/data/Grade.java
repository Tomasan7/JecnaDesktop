package me.tomasan7.jecnadesktop.data;

import java.time.LocalDate;

/**
 * @param value Grade's value. Is 0 for N.
 * @param small Whether the grade is small or big. (it's weight)
 * @param subject The subject th grade is from.
 * @param teacher The teacher, who gave you the grade.
 * @param description Description of the grade.
 */
public record Grade(byte value,
					boolean small,
					String subject,
					String teacher,
					String description,
					LocalDate receiveDate)
{
	public Grade
	{
		if (value < 0 || value > 5)
			throw new IllegalArgumentException("Grade value must be between 0 and 5. (got " + value + ")");
	}

	public static class Builder
	{
		// TODO: Implement.
	}
}
