package me.tomasan7.jecnadesktop.repository;

import me.tomasan7.jecnadesktop.data.Menu;
import me.tomasan7.jecnadesktop.data.MenuItem;

import java.util.concurrent.CompletableFuture;

public interface ICanteenRepository
{
	Menu queryMenu ();

	default CompletableFuture<Menu> queryMenuAsync ()
	{
		return CompletableFuture.supplyAsync(this::queryMenu);
	}

	boolean orderMenuItem (MenuItem menuItem);

	default CompletableFuture<Boolean> orderMenuItemAsync (MenuItem menuItem)
	{
		return CompletableFuture.supplyAsync(() -> orderMenuItem(menuItem));
	}
}
