package com.poixpixelcustom.util

import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.ChatColor
import java.util.function.Function
import java.util.regex.Pattern

object Colors {
    private val legacyLookupMap: MutableMap<String, String> = HashMap()
    private val legacyPattern = Pattern.compile("[§&][0-9a-fk-or]")

    /*
     * Legacy colors
     */
    const val Black = "\u00A70"
    const val Navy = "\u00A71"
    const val Green = "\u00A72"
    const val Blue = "\u00A73"
    const val Red = "\u00A74"
    const val Purple = "\u00A75"
    const val Gold = "\u00A76"
    const val LightGray = "\u00A77"
    const val Gray = "\u00A78"
    const val DarkPurple = "\u00A79"
    const val LightGreen = "\u00A7a"
    const val LightBlue = "\u00A7b"
    const val Rose = "\u00A7c"
    const val LightPurple = "\u00A7d"
    const val Yellow = "\u00A7e"
    const val White = "\u00A7f"

    /*
     * Minimessage colors
     */
    const val DARK_RED = "<dark_red>"
    const val RED = "<red>"
    const val GOLD = "<gold>"
    const val YELLOW = "<yellow>"
    const val DARK_GREEN = "<dark_green>"
    const val GREEN = "<green>"
    const val DARK_AQUA = "<dark_aqua>"
    const val AQUA = "<aqua>"
    const val DARK_BLUE = "<dark_blue>"
    const val BLUE = "<blue>"
    const val LIGHT_PURPLE = "<light_purple>"
    const val DARK_PURPLE = "<dark_purple>"
    const val WHITE = "<white>"
    const val GRAY = "<gray>"
    const val DARK_GRAY = "<dark_gray>"
    const val BLACK = "<black>"
    fun strip(line: String?): String {
        return PoixpixelCustomComponents.stripTags(ChatColor.stripColor(line)!!)
    }

    fun translateColorCodes(str: String?): String {
        return StringMgmt.translateHexColors(ChatColor.translateAlternateColorCodes('&', str!!))
    }

    fun translateLegacyCharacters(input: String?): String? {
        var input = input
        val matcher = input?.let { legacyPattern.matcher(it) }
        if (matcher != null) {
            while (matcher.find()) {
                val legacy = matcher.group()
                input = input!!.replace(legacy, legacyLookupMap.getOrDefault(legacy.substring(1), legacy))
            }
        }
        return input
    }

    private val modernHexFunction = Function { hex: String -> "<#$hex>" }

    /**
     * Converts non-minimessage hex formats to minimessage.
     * @param input The input that may or may not contain hex.
     * @return The input, with the minimessage hex format.
     */
    fun translateLegacyHex(input: String): String {
        return StringMgmt.translateHexColors(input)
    }

    /**
     * @param colorCode A legacy or MiniMessage color code.
     * @return the [NamedTextColor] for the entered color string, or null if it is invalid.
     */
    fun toNamedTextColor(colorCode: String?): NamedTextColor? {
        return when (colorCode) {
            "\u00A70", BLACK -> NamedTextColor.BLACK
            "\u00A71", DARK_BLUE -> NamedTextColor.DARK_BLUE
            "\u00A72", DARK_GREEN -> NamedTextColor.DARK_GREEN
            "\u00A73", DARK_AQUA -> NamedTextColor.DARK_AQUA
            "\u00A74", DARK_RED -> NamedTextColor.DARK_RED
            "\u00A75", DARK_PURPLE -> NamedTextColor.DARK_PURPLE
            "\u00A76", GOLD -> NamedTextColor.GOLD
            "\u00A77", GRAY -> NamedTextColor.GRAY
            "\u00A78", DARK_GRAY -> NamedTextColor.DARK_GRAY
            "\u00A79", BLUE -> NamedTextColor.BLUE
            "\u00A7a", GREEN -> NamedTextColor.GREEN
            "\u00A7b", AQUA -> NamedTextColor.AQUA
            "\u00A7c", RED -> NamedTextColor.RED
            "\u00A7d", LIGHT_PURPLE -> NamedTextColor.LIGHT_PURPLE
            "\u00A7e", YELLOW -> NamedTextColor.YELLOW
            "\u00A7f", WHITE -> NamedTextColor.WHITE
            else -> null
        }
    }

    init {
        legacyLookupMap["4"] = DARK_RED
        legacyLookupMap["c"] = RED
        legacyLookupMap["6"] = GOLD
        legacyLookupMap["e"] = YELLOW
        legacyLookupMap["2"] = DARK_GREEN
        legacyLookupMap["a"] = GREEN
        legacyLookupMap["3"] = DARK_AQUA
        legacyLookupMap["b"] = AQUA
        legacyLookupMap["1"] = DARK_BLUE
        legacyLookupMap["9"] = BLUE
        legacyLookupMap["d"] = LIGHT_PURPLE
        legacyLookupMap["5"] = DARK_PURPLE
        legacyLookupMap["f"] = WHITE
        legacyLookupMap["7"] = GRAY
        legacyLookupMap["8"] = DARK_GRAY
        legacyLookupMap["0"] = BLACK
        legacyLookupMap["k"] = "<obfuscated>"
        legacyLookupMap["l"] = "<bold>"
        legacyLookupMap["m"] = "<strikethrough>"
        legacyLookupMap["n"] = "<underline>"
        legacyLookupMap["o"] = "<italic>"
        legacyLookupMap["r"] = "<reset>"
    }
}