package me.tomasan7.jecnadesktop.data;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public record Attendances(Set<Attendance> attendances) implements Iterable<Attendance>
{
	/**
	 * Returns immutable iterator.
	 */
	@Override
	public Iterator<Attendance> iterator ()
	{
		return new HashSet<>(attendances).iterator();
	}
}