package me.tomasan7.jecnadesktop.data;

import java.time.LocalDateTime;

public class Attendance
{
	/**
	 * Determines whether the person exited or entered.
	 * False for enter.
	 * True for exit.
	 */
	private final boolean isExit;

	/**
	 * When the person entered/exited.
	 */
	private final LocalDateTime time;

	public Attendance (boolean isExit, LocalDateTime time)
	{
		this.isExit = isExit;
		this.time = time;
	}

	public boolean isExit ()
	{
		return isExit;
	}

	public LocalDateTime getTime ()
	{
		return time;
	}
}
