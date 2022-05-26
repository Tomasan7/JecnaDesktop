package me.tomasan7.jecnadesktop.repository

import me.tomasan7.jecnadesktop.data.Attendances

/**
 * Retrieves [Attendances] from any kind of data source.
 */
interface AttendancesRepository
{
    suspend fun queryAttendances(): Attendances
}