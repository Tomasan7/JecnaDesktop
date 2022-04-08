package me.tomasan7.jecnadesktop.repository;

import me.tomasan7.jecnadesktop.data.Grades;

import java.util.concurrent.CompletableFuture;

public interface GradesRepository
{
	Grades queryAttendances ();

	default CompletableFuture<Grades> queryAttendancesAsync ()
	{
		return CompletableFuture.supplyAsync(this::queryAttendances);
	}
}
