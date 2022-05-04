package me.tomasan7.jecnadesktop.repository;

import me.tomasan7.jecnadesktop.data.DayMenu;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

// https://objednavky.jidelnasokolska.cz/faces/secured/main.jsp?terminal=false&keyboard=false&printer=false&day=2022-05-07

public interface ICanteenRepository
{
	Optional<DayMenu> queryDay (LocalDate date);

	default CompletableFuture<Optional<DayMenu>> queryDayAsync (LocalDate date)
	{
		return CompletableFuture.supplyAsync(() -> queryDay(date));
	}
}
