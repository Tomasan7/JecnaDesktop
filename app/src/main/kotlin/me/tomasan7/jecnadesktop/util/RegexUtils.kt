package me.tomasan7.jecnadesktop.util

import java.util.*
import java.util.regex.Pattern

object RegexUtils
{
    /**
     * Returns a [List] of all matches inside the provided string.
     * @param source String to search in.
     * @param regex The regex to search with.
     * @return The list of all matches.
     */
    @Deprecated("Only used by java source code.")
    @JvmStatic
    fun find(source: String?, regex: Pattern): List<String>
    {
        val matcher = regex.matcher(source)
        val findings: MutableList<String> = ArrayList()
        while (matcher.find()) findings.add(matcher.group())
        return findings
    }

    /**
     * Returns a [List] of all matches inside the provided string.
     * @param source String to search in.
     * @param regex The regex to search with.
     * @return The list of all matches.
     */
    @Deprecated("Only used by java source code.")
    @JvmStatic
    fun find(source: String?, regex: String?): List<String>
    {
        return find(source, Pattern.compile(regex))
    }

    /**
     * Returns first match of a regex inside the provided string.
     * @param source String to search in.
     * @param regex The regex to search with.
     * @return The first match.
     */
    @Deprecated("Only used by java source code.")
    @JvmStatic
    fun findFirst(source: String?, regex: Pattern): Optional<String>
    {
        val matcher = regex.matcher(source)
        return if (matcher.find()) Optional.of(matcher.group()) else Optional.empty()
    }

    /**
     * Returns first match of a regex inside the provided string.
     * @param source String to search in.
     * @param regex The regex to search with.
     * @return The first match.
     */
    @Deprecated("Only used by java source code.")
    @JvmStatic
    fun findFirst(source: String?, regex: String?): Optional<String>
    {
        return findFirst(source, Pattern.compile(regex))
    }
}