package me.tomasan7.jecnadesktop.data;

import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents single pass by the school entrance.
 * It can be either enter, or exit.
 *
 * @param exit Whether the person exited or entered. {@code false} for enter, {@code true} for exit.
 * @param time The time, the person entered/exited.
 */
public record Attendance(boolean exit, @NotNull LocalTime time)
{
	@Override
	public String toString ()
	{
		return (exit ? "Odchod" : "Příchod") + " " + time.format(DateTimeFormatter.ofPattern("HH:mm"));
	}
}
