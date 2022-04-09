package me.tomasan7.jecnadesktop.repository;

import me.tomasan7.jecnadesktop.data.Grades;

import java.util.concurrent.CompletableFuture;

public interface GradesRepository
{
	Grades queryGrades ();

	default CompletableFuture<Grades> queryGradesAsync ()
	{
		return CompletableFuture.supplyAsync(this::queryGrades);
	}
}
