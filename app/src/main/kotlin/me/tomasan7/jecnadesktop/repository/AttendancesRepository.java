package me.tomasan7.jecnadesktop.repository;

import me.tomasan7.jecnadesktop.data.Attendances;

import java.util.concurrent.CompletableFuture;

/**
 * Retrieves {@link Attendances} from any kind of data source.
 */
public interface AttendancesRepository
{
	Attendances queryAttendances ();

	default CompletableFuture<Attendances> queryAttendancesAsync ()
	{
		return CompletableFuture.supplyAsync(this::queryAttendances);
	}
}
