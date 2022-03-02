package me.tomasan7.jecnadesktop.data;

public class Grade
{
	/**
	* Represents the grade's value. Is 0 for N.
	*/
	private final byte value;

	public Grade (byte value)
	{
		if (value < 0 || value > 5)
			throw new IllegalArgumentException("Grade value must be between 0 and 5. (got " + value + ")");

		this.value = value;
	}

	public byte getValue ()
	{
		return value;
	}
}
