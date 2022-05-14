package me.tomasan7.jecnadesktop.data;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class DayMenu implements Iterable<MenuItem>
{
	private final LocalDate day;
	private final List<MenuItem> items;

	private DayMenu (@NotNull LocalDate day, @NotNull List<MenuItem> items)
	{
		this.day = day;
		/* Encapsulation. */
		this.items = new ArrayList<>(items);
	}

	@NotNull
	public LocalDate getDay ()
	{
		return day;
	}

	@NotNull
	public MenuItem getMenuItem (int index)
	{
		return items.get(index);
	}

	public int size ()
	{
		return items.size();
	}

	@NotNull
	@Override
	public Iterator<MenuItem> iterator ()
	{
		/* Encapsulation. */
		return new LinkedList<>(items).iterator();
	}
}
