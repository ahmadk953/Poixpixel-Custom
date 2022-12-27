package com.poixpixelcustom.util

import com.poixpixelcustom.Object.PoixpixelCustomObject
import java.util.*

/**
 * Useful function for use with the Minecraft Server chatbox.
 *
 */
object ChatTools {
    private const val DEFAULT_CHAT_WIDTH = 320
    private val SPACE_WIDTH = FontUtil.measureWidth(' ')
    private val UNDERSCORE_WIDTH = FontUtil.measureWidth('_')

    // Padding used for the main title formatting
    private const val WIDGET = ".oOo."
    private val WIDGET_WIDTH = FontUtil.measureWidth(WIDGET)

    // Padding used for subtitle formatting
    private const val SUBWIDGET = " .]|[. "
    private val SUBWIDGET_WIDTH = FontUtil.measureWidth(SUBWIDGET)
    fun listArr(args: Array<String?>, prefix: String): String {
        return list(Arrays.asList(*args), prefix)
    }

    @JvmOverloads
    fun list(args: MutableList<String?>, prefix: String = ""): String {
        return if (args.isEmpty()) "" else prefix + java.lang.String.join(", ", args)
    }

    fun stripColour(s: String?): String? {
        return Colors.strip(s)
    }

    /**
     * Formats a title for a PoixpixelCustomObject, taking into account that on
     * servers with high max_name_length could end up breaking the math
     * @param object PoixpixelCustomObject
     * @return a title bar which won't exceed the allowed length.
     */
    fun formatTitle(`object`: PoixpixelCustomObject): String {
        var title = `object`.formattedName
        if (title!!.length > 51) title = `object`.name
        if (title!!.length > 51) title = title.substring(0, 51)
        return formatTitle(title)
    }

    fun formatTitle(title: String?): String {
        var title = title
        title = ".[ " + "status_title_secondary_colour" + title + "status_title_primary_colour" + " ]."
        if (!FontUtil.isValidMinecraftFont(title)) return legacyFormatTitle(title)
        val width = FontUtil.measureWidth(PoixpixelCustomComponents.miniMessage(title))

        // Max width - widgetx2 (already padded with an extra 1px) - title - 2 (1px before and after the title.)
        val remainder = DEFAULT_CHAT_WIDTH - WIDGET_WIDTH * 2 - width - 2
        if (remainder < 1) return "status_title_primary_colour$title"
        if (remainder < 14) return "status_title_primary_colour" + WIDGET + title + WIDGET
        val times = Math.floor((remainder / (UNDERSCORE_WIDTH * 2)).toDouble()).toInt()
        return "status_title_primary_colour" + WIDGET + repeatChar(times, "_") + title + repeatChar(times, "_") + WIDGET
    }

    private fun legacyFormatTitle(title: String): String {
        var title = title
        val line = ".oOo.__________________________________________________.oOo."
        if (title.length > line.length) title = title.substring(0, line.length)
        val pivot = line.length / 2
        val center = title
        var out = "status_title_primary_colour" + line.substring(0, Math.max(0, pivot - center.length / 2))
        out += center + line.substring(pivot + center.length / 2)
        return out
    }

    fun formatSubTitle(subtitle: String): String {
        if (!FontUtil.isValidMinecraftFont(subtitle)) return legacyFormatSubtitle(subtitle)
        val width = FontUtil.measureWidth(PoixpixelCustomComponents.miniMessage(subtitle))

        // Max width - widgetx2 (already padded with an extra 1px) - title - 2 (1px before and after the title.)
        val remainder = DEFAULT_CHAT_WIDTH - SUBWIDGET_WIDTH * 2 - width - 2
        if (remainder < 1) return "status_title_primary_colour$subtitle"
        if (remainder < 10) return "status_title_primary_colour" + SUBWIDGET + subtitle + "status_title_primary_colour" + SUBWIDGET
        val times = Math.floor((remainder / (SPACE_WIDTH * 2)).toDouble()).toInt()
        return "status_title_primary_colour" + SUBWIDGET + repeatChar(times, " ") + subtitle + repeatChar(times, " ") + "status_title_primary_colour" + SUBWIDGET
    }

    private fun legacyFormatSubtitle(subtitle: String): String {
        val line = " .]|[.                                                                     .]|[."
        val pivot = line.length / 2
        val center = subtitle + "status_title_primary_colour"
        var out = "status_title_primary_colour" + line.substring(0, Math.max(0, pivot - center.length / 2))
        out += center + line.substring(pivot + center.length / 2)
        return out
    }

    private fun repeatChar(num: Int, character: String): String {
        var output = ""
        for (i in 0 until num) output += character
        return output
    }

    fun formatCommand(command: String, subCommand: String, help: String): String {
        return formatCommand("", command, subCommand, help)
    }

    fun formatCommand(requirement: String, command: String, subCommand: String, help: String): String {
        var out = "  "
        if (requirement.length > 0) out += Colors.Rose + requirement + ": "
        out += Colors.Blue + command
        if (subCommand.length > 0) out += " " + Colors.LightBlue + subCommand
        if (help.length > 0) out += " " + Colors.LightGray + " : " + help
        return out
    }

    /**
     * @param title   - Title of the list,
     * @param subject - Subject of the listing.
     * @param list    - Any list that is in an order of ranking.
     * @param page    - Already formatted PoixpixelCustom.getListPageMsg(page,total) handler.
     * @return - Fully formatted output which should be sent to the player.
     * @author - Articdive
     */
    fun formatList(title: String?, subject: String, list: List<String>?, page: String): Array<String> {
        val output: MutableList<String> = ArrayList()
        output.add(0, formatTitle(title))
        output.add(1, subject)
        output.addAll(list!!)
        output.add(page)
        return output.toTypedArray()
    }
}