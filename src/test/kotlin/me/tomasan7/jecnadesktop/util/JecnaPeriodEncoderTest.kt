package me.tomasan7.jecnadesktop.util

import me.tomasan7.jecnadesktop.data.SchoolYear
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

internal class JecnaPeriodEncoderTest
{
    @Test
    fun testEncodeSchoolYear()
    {
        assertEquals(JecnaPeriodEncoder.SCHOOL_YEAR_ID_KEY to 13, JecnaPeriodEncoder.encodeSchoolYear(SchoolYear(2021)))
        assertEquals(JecnaPeriodEncoder.SCHOOL_YEAR_ID_KEY to 11, JecnaPeriodEncoder.encodeSchoolYear(SchoolYear(2019)))

        val schoolYear = SchoolYear(2007)
        assertThrows<IllegalArgumentException>("Lowest supported school year is 2008/2009. (got $schoolYear)") {
            JecnaPeriodEncoder.encodeSchoolYear(schoolYear)
        }
    }

    @Test
    fun testDecodeSchoolYear()
    {
        assertEquals(SchoolYear(2021), JecnaPeriodEncoder.decodeSchoolYear(13))
        assertEquals(SchoolYear(2019), JecnaPeriodEncoder.decodeSchoolYear(11))

        val id = -1
        assertThrows<IllegalArgumentException>("Id cannot be less than 0. (got $id)") {
            JecnaPeriodEncoder.decodeSchoolYear(id)
        }
    }

    @Test
    fun testEncodeSchoolYearHalf()
    {
        assertEquals(JecnaPeriodEncoder.SCHOOL_YEAR_HALF_ID_KEY to JecnaPeriodEncoder.FIRST_HALF_ID, JecnaPeriodEncoder.encodeSchoolYearHalf(true))
        assertEquals(JecnaPeriodEncoder.SCHOOL_YEAR_HALF_ID_KEY to JecnaPeriodEncoder.SECOND_HALF_ID, JecnaPeriodEncoder.encodeSchoolYearHalf(false))
    }

    @Test
    fun testDecodeSchoolYearHalf()
    {
        assertEquals(true, JecnaPeriodEncoder.decodeSchoolYearHalf(JecnaPeriodEncoder.FIRST_HALF_ID))
        assertEquals(false, JecnaPeriodEncoder.decodeSchoolYearHalf(JecnaPeriodEncoder.SECOND_HALF_ID))

        val id = 20
        assertThrows<IllegalArgumentException>("Id doesn't correspond to any year half. (got $id)") {
            JecnaPeriodEncoder.decodeSchoolYearHalf(id)
        }
    }
}