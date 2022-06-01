package me.tomasan7.jecnadesktop.data

import java.time.LocalDate
import java.util.*
import kotlin.collections.HashMap

/**
 * Holds all [attendances][Attendance] for each day.
 */
class Attendances private constructor(private val attendances: Map<LocalDate, List<Attendance>>)
{
    /**
     * All days, this [Attendances] has data for.
     */
    val days = attendances.keys

    /**
     * @return All [attendances][Attendance] for the provided day. Returns an empty list when no data for [day] is present.
     */
    fun getAttendancesForDay(day: LocalDate) = attendances.getOrDefault(day, listOf())

    class Builder
    {
        private val attendances: MutableMap<LocalDate, MutableList<Attendance>> = HashMap()

        /**
         * Adds [Attendance].
         *
         * @param day        The day to add this attendance to.
         * @param attendance The [Attendance] to add.
         */
        fun addAttendance(day: LocalDate, attendance: Attendance): Builder
        {
            /* Gets the list for the day, if none is present, creates a new list and puts it into the map. Then the attendance is added to that list. */
            attendances.computeIfAbsent(day) { LinkedList() }
                .add(attendance)

            return this
        }

        fun build() = Attendances(attendances)
    }

    companion object
    {
        /**
         * Represents an [Attendance] [List] as a [String].
         * Does so by joining all [Attendance.toString]'s with comma.
         *
         * @param attendanceList The [Attendance] [List] to represent.
         * @return The [String] representation.
         */
        fun attendanceListAsString(attendanceList: List<Attendance>) = attendanceList.joinToString()

        fun builder() = Builder()
    }
}