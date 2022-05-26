package me.tomasan7.jecnadesktop.repository

import me.tomasan7.jecnadesktop.data.Timetable

/**
 * Retrieves [Timetable] from any kind of data source.
 */
interface TimetableRepository
{
    suspend fun queryTimetable(): Timetable
}