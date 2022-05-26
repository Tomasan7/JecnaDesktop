package me.tomasan7.jecnadesktop.repository

import me.tomasan7.jecnadesktop.data.Grades

/**
 * Retrieves [Grades] from any kind of data source.
 */
interface GradesRepository
{
    suspend fun queryGrades(): Grades
}