package me.tomasan7.jecnadesktop.repository

import me.tomasan7.jecnadesktop.data.Grades
import me.tomasan7.jecnadesktop.data.SchoolYear

/**
 * Retrieves [Grades] from any kind of data source.
 */
interface GradesRepository
{
    suspend fun queryGrades(): Grades

    /**
     * @param schoolYear The [SchoolYear] to get the [Grades] for.
     * @param firstHalf The school year half to get the [Grades] for. `true` for first half and `false` for second half.
     */
    suspend fun queryGrades(schoolYear: SchoolYear, firstHalf: Boolean): Grades
}