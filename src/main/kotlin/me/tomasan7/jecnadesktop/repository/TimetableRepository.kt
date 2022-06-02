package me.tomasan7.jecnadesktop.repository

import me.tomasan7.jecnadesktop.data.SchoolYear
import me.tomasan7.jecnadesktop.data.Timetable

/**
 * Retrieves [Timetable] from any kind of data source.
 */
interface TimetableRepository
{
    suspend fun queryTimetable(): Timetable

    /**
     * @param schoolYear The [SchoolYear] to get the [Timetable] for.
     */
    suspend fun queryTimetable(schoolYear: SchoolYear): Timetable
}