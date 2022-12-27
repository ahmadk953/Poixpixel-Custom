package com.poixpixelcustom.util

import java.util.*
import java.util.regex.Pattern

/**
 * Represents a numbering system of a string format.
 */
class Version private constructor(private val version: String) : Comparable<Version?> {
    /**
     * Returns the components of the version separated by .'s
     *
     * @return A string array of the components.
     */
    val components: Array<String>

    init {
        components = version.split(SEPARATOR.pattern().toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    }

    override fun compareTo(other: Version?): Int {
        TODO("Not yet implemented")
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o !is Version) return false
        return version == o.version
    }

    override fun hashCode(): Int {
        return Objects.hash(version)
    }

    override fun toString(): String {
        return version
    }

    val isPreRelease: Boolean
        get() = try {
            components[components.size - 1].toInt() != 0
        } catch (e: NumberFormatException) {
            false
        }

    companion object {
        private val SEPARATOR = Pattern.compile("\\.")
        private val VERSION_PATTERN = Pattern.compile("[0-9]+(" + SEPARATOR + "[0-9]+)*")

        /**
         * Constructs a Version object from the given string.
         *
         *
         * This method will truncate any extraneous characters found
         * after it matches the first qualified version string.
         *
         * @param version A string that contains a formatted version.
         * @return A new Version instance from the given string.
         */
        fun fromString(version: String?): Version {
            requireNotNull(version) { "Version can not be null" }
            val matcher = VERSION_PATTERN.matcher(version)
            require(matcher.find()) { "Invalid version format: $version" }
            return Version(matcher.group(0))
        }
    }
}