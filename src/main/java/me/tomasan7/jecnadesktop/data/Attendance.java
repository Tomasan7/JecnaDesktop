package me.tomasan7.jecnadesktop.data;

import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @param exit Whether the person exited or entered. False for enter, true for exit.
 * @param time When the person entered/exited.
 */
public record Attendance(boolean exit, @NotNull LocalTime time)
{
	@Override
	public String toString ()
	{
		return (exit ? "Odchod" : "Příchod") + " " + time.format(DateTimeFormatter.ofPattern("HH:mm"));
	}
}
