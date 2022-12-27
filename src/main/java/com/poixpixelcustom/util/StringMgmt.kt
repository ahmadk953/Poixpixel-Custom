package com.poixpixelcustom.util

import co.aikar.util.LoadingIntMap
import com.google.common.base.Strings
import net.md_5.bungee.api.ChatColor
import org.jetbrains.annotations.ApiStatus
import java.util.*
import java.util.function.Function
import java.util.regex.Pattern
import java.util.stream.Collectors
import java.util.stream.Stream

/**
 * Useful functions related to strings, or arrays of them.
 *
 * @author Shade (Chris H)
 * @version 1.4
 */
object StringMgmt {
    val hexPattern = Pattern.compile("((&|\\{|<|)(#|§x))([a-fA-F0-9]|§[a-fA-F0-9]){6}(}|>|)")
    val hexReplacePattern = Pattern.compile("(§x|[&{}<>§#])")

    @Deprecated("")
    val ampersandPattern = Pattern.compile("(?<!\\\\)(&#[a-fA-F0-9]{6})")

    @Deprecated("")
    val bracketPattern = Pattern.compile("(?<!\\\\)\\{(#[a-fA-F0-9]{6})}")
    private val legacyHexFunction = Function { hex: String -> ChatColor.of("#$hex").toString() }
    @ApiStatus.Internal
    fun translateHexColors(string: String): String {
        var string = string
        val hexMatcher = hexPattern.matcher(string)
        while (hexMatcher.find()) {
            val hex = hexMatcher.group()
            val hexFunction: LoadingIntMap.Feeder<Any>? = null
        }
        return string
    }

    @JvmOverloads
    fun join(args: Collection<*>, separator: String? = " "): String {
        val joiner = StringJoiner(separator)
        for (o in args) {
            joiner.add(o.toString())
        }
        return joiner.toString()
    }

    @JvmOverloads
    fun join(arr: Array<Any>, separator: String = " "): String {
        if (arr.size == 0) return ""
        var out = arr[0].toString()
        for (i in 1 until arr.size) out += separator + arr[i]
        return out
    }

    fun join(map: Map<*, *>, keyValSeparator: String?, tokenSeparator: String?): String {
        if (map.size == 0) return ""
        val sb = StringBuilder()
        for ((key, value) in map) sb.append(key).append(keyValSeparator).append(value.toString()).append(tokenSeparator)
        return sb.toString()
    }

    fun repeat(sequence: String?, repetitions: Int): String {
        return Strings.repeat(sequence, repetitions)
    }

    fun remFirstArg(arr: Array<String?>): Array<String?> {
        return remArgs(arr, 1)
    }

    fun remLastArg(arr: Array<String?>): Array<String?> {
        return subArray(arr, 0, arr.size - 1)
    }

    fun remArgs(arr: Array<String?>, startFromIndex: Int): Array<String?> {
        return if (arr.size == 0) arr else if (arr.size < startFromIndex) arrayOfNulls(0) else {
            val newSplit = arrayOfNulls<String>(arr.size - startFromIndex)
            System.arraycopy(arr, startFromIndex, newSplit, 0, arr.size - startFromIndex)
            newSplit
        }
    }

    fun subArray(arr: Array<String?>, start: Int, end: Int): Array<String?> {

        //assert start > end;
        //assert start >= 0;
        //assert end < args.length;
        return if (arr.size == 0) arr else if (end < start) arrayOfNulls(0) else {
            val length = end - start
            val newSplit = arrayOfNulls<String>(length)
            System.arraycopy(arr, start, newSplit, 0, length)
            newSplit
        }
    }

    /**
     * Shortens the string to fit in the specified size.
     *
     * @param str - [String] to trim
     * @param length - length to trim to
     * @return the shortened string
     */
    fun trimMaxLength(str: String, length: Int): String {
        return if (str.length < length) str else if (length > 3) str.substring(0, length) else throw UnsupportedOperationException("Minimum length of 3 characters.")
    }

    /**
     * Shortens the string to fit in the specified size with an ellipse "..." at
     * the end.
     *
     * @param str - [String] to fit
     * @param length - Length of the string, before shortening
     * @return the shortened string, followed with ellipses
     */
    fun maxLength(str: String, length: Int): String {
        return if (str.length < length) str else if (length > 3) str.substring(0, length - 3) + "..." else throw UnsupportedOperationException("Minimum length of 3 characters.")
    }

    fun containsIgnoreCase(arr: List<String>, str: String?): Boolean {
        for (s in arr) if (s.equals(str, ignoreCase = true)) return true
        return false
    }

    /**
     * Replaces underscores with spaces.
     *
     * @param str - the string to change.
     * @return the string with spaces replacing underscores.
     */
    fun remUnderscore(str: String): String {
        return str.replace("_".toRegex(), " ")
    }

    fun capitalize(str: String?): String? {
        return if (str == null || str.isEmpty()) str else str.substring(0, 1).uppercase(Locale.getDefault()) + str.substring(1)
    }

    /**
     * Capitalizes the beginning of each word, accounting for underscores separating those words
     * @param string String to capitalize.
     * @return String with the beginning letter of each word capitalized.
     */
    fun capitalizeStrings(string: String): String {
        return Stream.of(*string.split("_".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()).map { str: String -> str.substring(0, 1).uppercase(Locale.getDefault()) + str.substring(1) }.collect(Collectors.joining("_"))
    }

    @Throws(Exception::class)
    fun parseOnOff(s: String): Boolean {
        return if (s.equals("on", ignoreCase = true)) true else if (s.equals("off", ignoreCase = true)) false else throw Exception("msg_err_invalid_input")
    }

    fun isAllUpperCase(string: String): Boolean {
        if (string.isEmpty()) return false
        for (i in 0 until string.length) {
            if (!Character.isUpperCase(string[i])) return false
        }
        return true
    }

    fun isAllUpperCase(collection: Collection<String>): Boolean {
        if (collection.isEmpty()) return false
        for (string in collection) if (!isAllUpperCase(string)) return false
        return true
    }

    fun addToList(list: List<String>?, addition: String): List<String> {
        val out: MutableList<String> = ArrayList(list)
        out.add(addition)
        return out
    }

    fun wrap(string: String?, wrapLength: Int, newlineString: String): String {
        var index = 0
        val stringBuilder = StringBuilder(string)
        while (index + wrapLength < stringBuilder.length && stringBuilder.lastIndexOf(" ", index + wrapLength).also { index = it } != -1) {
            stringBuilder.replace(index, index + 1, newlineString)
            index += newlineString.length
        }
        return stringBuilder.toString()
    }
}
