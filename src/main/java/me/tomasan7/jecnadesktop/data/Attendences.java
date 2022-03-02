package me.tomasan7.jecnadesktop.data;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Attendences implements Iterable<Attendance>
{
	private final Set<Attendance> attendances;

	public Attendences (Set<Attendance> attendances)
	{
		this.attendances = attendances;
	}

	/**
	 * Returns immutable iterator.
	 * @return The iterator.
	 */
	@Override
	public Iterator<Attendance> iterator ()
	{
		return new HashSet<>(attendances).iterator();
	}
}