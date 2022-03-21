package me.tomasan7.jecnadesktop.data;

import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;

/**
 * @param value       Grade's value. Is 0 for N.
 * @param small       Whether the grade is small or big. (it's weight)
 * @param subject     The subject th grade is from.
 * @param teacher     The teacher, who gave you the grade.
 * @param description Description of the grade.
 */
public record Grade(byte value,
					boolean small,
					@Nullable String subject,
					@Nullable String teacher,
					@Nullable String description,
					@Nullable LocalDate receiveDate)
{
	public Grade
	{
		if (value < 0 || value > 5)
			throw new IllegalArgumentException("Grade value must be between 0 and 5. (got " + value + ")");
	}

	/**
	 * Accepts value's representing char.
	 * @param valueChar The value char representation.
	 * @see #valueChar()
	 */
	public Grade (char valueChar,
				  boolean small,
				  @Nullable String subject,
				  @Nullable String teacher,
				  @Nullable String description,
				  @Nullable LocalDate receiveDate)
	{
		this(valueCharToValue(valueChar),
				small,
				subject,
				teacher,
				description,
				receiveDate);
	}

	/**
	 * Returns this grade's {@link #value} as char. Returns 'N' for value 0.
	 */
	public char valueChar ()
	{
		if (value == 0)
			return 'N';
		else
			return String.valueOf(value).charAt(0);
	}

	/**
	 * Converts value's char representation to the value.
	 * @param valueChar The value's char representation.
	 * @return It's value.
	 */
	private static byte valueCharToValue (char valueChar)
	{
		return valueChar == 'N' ? 0 : Byte.parseByte(String.valueOf(valueChar));
	}

	public static Builder builder (byte value, boolean small)
	{
		return new Builder(value, small);
	}

	public static Builder builder (char valueChar, boolean small)
	{
		return new Builder(valueCharToValue(valueChar), small);
	}

	public static class Builder
	{
		private final byte value;
		private final boolean small;

		private String subject = null;
		private String teacher = null;
		private String description = null;
		private LocalDate receiveDate = null;

		public Builder (byte value, boolean small)
		{
			this.value = value;
			this.small = small;
		}

		public Builder (char valueChar, boolean small)
		{
			this.value = valueCharToValue(valueChar);
			this.small = small;
		}

		public Grade build()
		{
			return new Grade(value, small, subject, teacher, description, receiveDate);
		}

		public Builder subject (String subject)
		{
			this.subject = subject;
			return this;
		}

		public Builder teacher (String teacher)
		{
			this.teacher = teacher;
			return this;
		}

		public Builder description (String description)
		{
			this.description = description;
			return this;
		}

		public Builder receiveDate (LocalDate receiveDate)
		{
			this.receiveDate = receiveDate;
			return this;
		}
	}
}
