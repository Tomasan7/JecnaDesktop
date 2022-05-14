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

	public static Builder builder (LocalDate day)
	{
		return new Builder(day);
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

	public static class Builder
	{
		private final LocalDate day;
		private final List<MenuItem> items = new LinkedList<>();

		public Builder (@NotNull  LocalDate day)
		{
			this.day = day;
		}

		/**
		 * Adds {@link MenuItem}.
		 * @param item The {@link MenuItem} to add.
		 * @return This {@link Builder builder's} instance back.
		 */
		@NotNull
		public Builder addItem (@NotNull MenuItem item)
		{
			items.add(item);
			return this;
		}

		@NotNull
		public DayMenu build ()
		{
			return new DayMenu(day, items);
		}
	}
}
