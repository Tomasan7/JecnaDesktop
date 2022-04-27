package me.tomasan7.jecnadesktop.repository;

import me.tomasan7.jecnadesktop.data.Grades;

import java.util.concurrent.CompletableFuture;

/**
 * Retrieves {@link Grades} from any kind of data source.
 */
public interface GradesRepository
{
	Grades queryGrades ();

	default CompletableFuture<Grades> queryGradesAsync ()
	{
		return CompletableFuture.supplyAsync(this::queryGrades);
	}
}
