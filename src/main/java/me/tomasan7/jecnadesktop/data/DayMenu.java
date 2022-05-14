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

	/**
	 * Constructor used by {@link Builder}.
	 */
	private DayMenu (@NotNull LocalDate day, @NotNull List<MenuItem> items)
	{
		this.day = day;
		/* Encapsulation. */
		this.items = new ArrayList<>(items);
	}

	/**
	 * @return The day this menu is for.
	 */
	@NotNull
	public LocalDate getDay ()
	{
		return day;
	}

	/**
	 * @return {@link MenuItem} by it's number (index).
	 */
	@NotNull
	public MenuItem getMenuItem (int number)
	{
		return items.get(number);
	}

	/**
	 * Creates a new {@link Builder}.
	 * @param day The day the new menu will be for.
	 * @return The new {@link Builder}.
	 */
	public static Builder builder (LocalDate day)
	{
		return new Builder(day);
	}

	/**
	 * @return The number of {@link MenuItem MenuItems} in this {@link DayMenu}.
	 */
	public int itemsCount ()
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
