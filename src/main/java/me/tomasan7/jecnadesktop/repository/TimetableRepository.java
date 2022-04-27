package me.tomasan7.jecnadesktop.repository;

import me.tomasan7.jecnadesktop.data.Timetable;

import java.util.concurrent.CompletableFuture;

/**
 * Retrieves {@link Timetable} from any kind of data source.
 */
public interface TimetableRepository
{
	Timetable queryTimetable ();

	default CompletableFuture<Timetable> queryTimetableAsync ()
	{
		return CompletableFuture.supplyAsync(this::queryTimetable);
	}
}
