package me.tomasan7.jecnadesktop.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Menu
{
	private final Map<LocalDate, DayMenu> menu;

	/**
	 * Constructor used by {@link Builder}.
	 */
	private Menu (@NotNull Map<LocalDate, DayMenu> menu)
	{
		this.menu = new HashMap<>(menu);
	}

	/**
	 * @return {@link DayMenu} for the provided day.
	 */
	@Nullable
	public DayMenu getMenuForDay (LocalDate day)
	{
		return menu.get(day);
	}

	@NotNull
	public static Builder builder ()
	{
		return new Builder();
	}

	public static class Builder
	{
		private final Map<LocalDate, DayMenu> menu = new HashMap<>();

		/**
		 * Adds {@link DayMenu}.
		 * @param dayMenu The {@link DayMenu} to add.
		 * @return This {@link Builder builder's} instance back.
		 */
		@NotNull
		public Builder addDayMenu (@NotNull DayMenu dayMenu)
		{
			menu.put(dayMenu.getDay(), dayMenu);
			return this;
		}

		@NotNull
		public Menu build ()
		{
			return new Menu(menu);
		}
	}
}
