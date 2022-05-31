package me.tomasan7.jecnadesktop.data

import java.time.LocalDate

data class SchoolYear(val firstCalendarYear: Int)
{
    val secondCalendarYear: Int = firstCalendarYear + 1

    /**
     * Constructs a [SchoolYear] this [date] belongs to. Considers the summer holidays as a new year.
     */
    constructor(date: LocalDate) : this(if (SCHOOL_YEAR_LAST_MONTH <= date.monthValue) date.year else date.year + 1)

    /**
     * @return Whether the passed date is inside this [SchoolYear]. Considers the summer holidays as a new year.
     */
    fun contains(date: LocalDate): Boolean
    {
        return (date.year == firstCalendarYear
                && SCHOOL_YEAR_LAST_MONTH <= date.monthValue)
               ||
               (date.year == secondCalendarYear
                && date.monthValue <= SCHOOL_YEAR_LAST_MONTH)
    }

    override fun toString() = "$firstCalendarYear/$secondCalendarYear"

    companion object
    {
        /** Last month in a school year - June. */
        private const val SCHOOL_YEAR_LAST_MONTH = 6

        /** First month in a school year - August. */
        private const val SCHOOL_YEAR_FIRST_MONTH = 9

        /**
         * @return [SchoolYear] represented in [String]. The [string] **must be in 'firstYear/secondYear' format.** (eg. 2021/2022)
         * @throws IllegalArgumentException When the [string] [String] is not in the correct format.
         * @see [SchoolYear.toString]
         */
        fun fromString (string: String): SchoolYear
        {
            val split = string.split("/")

            val firstYear: Int
            val secondYear: Int

            try
            {
                firstYear = split[0].toInt()
                secondYear = split[1].toInt()
            }
            catch (e: Exception)
            {
                throw IllegalArgumentException("SchoolYear String wasn't in correct format. (firstYear/secondYear)")
            }

            if (firstYear + 1 != secondYear)
                throw IllegalArgumentException("SchoolYear String wasn't in correct format. SecondYear must be equal to firstYear + 1.")

            return SchoolYear(firstYear)
        }
    }
}