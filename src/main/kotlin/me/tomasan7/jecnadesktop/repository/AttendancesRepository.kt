package me.tomasan7.jecnadesktop.repository

import me.tomasan7.jecnadesktop.data.Attendances
import me.tomasan7.jecnadesktop.data.SchoolYear

/**
 * Retrieves [Attendances] from any kind of data source.
 */
interface AttendancesRepository
{
    suspend fun queryAttendances(): Attendances

    /**
     * @param schoolYear The [SchoolYear] to get the [Attendances] for.
     * @param month The month to get the [Attendances] for. (`1` = January)
     */
    suspend fun queryAttendances(schoolYear: SchoolYear, month: Int): Attendances
}