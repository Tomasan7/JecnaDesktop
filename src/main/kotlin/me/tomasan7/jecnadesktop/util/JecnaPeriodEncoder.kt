package me.tomasan7.jecnadesktop.util

import me.tomasan7.jecnadesktop.data.SchoolYear

/**
 * Converts periods/times from Kotlin objects to a Jecna URL query parameters.
 */
object JecnaPeriodEncoder
{
    /**
     * School year, whose id is `0`.
     */
    const val ID_ZERO_YEAR = 2008
    /**
     * Id of the first school year half.
     */
    const val FIRST_HALF_ID = 21
    /**
     * Id of the second school year half.
     */
    const val SECOND_HALF_ID = 22
    /**
     * URL query parameter key for the school year.
     */
    const val SCHOOL_YEAR_ID_KEY = "schoolYearId"
    /**
     * URL query parameter key for the school year half.
     */
    const val SCHOOL_YEAR_HALF_ID_KEY = "schoolYearHalfId"
    /**
     * URL query parameter key for the month.
     */
    const val MONTH_ID_KEY = "schoolYearPartMonthId"

    /**
     * @param schoolYear The [SchoolYear] to encode.
     * @return School year URL query parameter as [Pair] of a key and a value.
     * @throws IllegalArgumentException when there is no id for the [schoolYear].
     */
    fun encodeSchoolYear(schoolYear: SchoolYear): Pair<String, Int>
    {
        require(schoolYear.firstCalendarYear >= 2008) { "Lowest supported school year is 2008/2009. (got $schoolYear)" }

        return SCHOOL_YEAR_ID_KEY to schoolYear.firstCalendarYear - ID_ZERO_YEAR
    }

    /**
     * @receiver The [SchoolYear] to encode.
     * @return School year URL query parameter as [Pair] of a key and a value.
     * @throws IllegalArgumentException when there is no id for this [SchoolYear].
     * @see encodeSchoolYear
     */
    fun SchoolYear.jecnaEncode() = encodeSchoolYear(this)

    /**
     * @param id The id of the school year.
     * @return [SchoolYear] by the id.
     * @throws IllegalArgumentException when the [id] is less than `0`.
     */
    fun decodeSchoolYear(id: Int): SchoolYear
    {
        require(id >= 0) { "Id cannot be less than 0. (got $id)" }

        return SchoolYear(ID_ZERO_YEAR + id)
    }

    /**
     * @param id The id of the school year.
     * @return [SchoolYear] by the id.
     * @throws IllegalArgumentException when the [id] is less than `0`.
     * @see decodeSchoolYear
     */
    fun SchoolYear.Companion.jecnaDecode(id: Int) = decodeSchoolYear(id)

    /**
     * @param firstHalf Is `true` for first half and `false` for second half.
     * @return School year half URL query parameter as [Pair] of a key and a value.
     */
    fun encodeSchoolYearHalf(firstHalf: Boolean) = SCHOOL_YEAR_HALF_ID_KEY to (if (firstHalf) FIRST_HALF_ID else SECOND_HALF_ID)

    /**
     * @param id The id of the school year half.
     * @return `true` for first half and `false` for second half.
     * @throws IllegalArgumentException when the [id] corresponds to neither half.
     */
    fun decodeSchoolYearHalf(id: Int) = when (id)
    {
        FIRST_HALF_ID  -> true
        SECOND_HALF_ID -> false
        else           -> throw IllegalArgumentException("Id doesn't correspond to any year half. (got $id)")
    }

    /**
     * @return Month URL query parameter as [Pair] of a key and a value.
     * @throws IllegalArgumentException when the [monthValue] isn't a valid month.
     */
    fun encodeMonth(monthValue: Int): Pair<String, Int>
    {
        require(monthValue in 1..12) { "Month must be between 1 and 12. (got $monthValue)" }

        return MONTH_ID_KEY to monthValue
    }
}