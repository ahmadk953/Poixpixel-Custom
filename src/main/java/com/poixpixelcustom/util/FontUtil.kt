package com.poixpixelcustom.util

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.Style
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.map.MinecraftFont
import org.jetbrains.annotations.ApiStatus
import solar.squares.pixelwidth.DefaultCharacterWidthFunction
import solar.squares.pixelwidth.PixelWidthSource

object FontUtil {
    @ApiStatus.Internal
    val font = MinecraftFont()
    private val widthSource = PixelWidthSource
            .pixelWidth(object : DefaultCharacterWidthFunction() {
                override fun handleMissing(codepoint: Int, style: Style): Float {
                    // Use MinecraftFont as a backup
                    return try {
                        font.getWidth(
                                codepoint.toChar().toString() + if (style.hasDecoration(TextDecoration.BOLD)) 1 else 0).toFloat()
                    } catch (e: IllegalArgumentException) {
                        6.0f
                    }
                }
            })

    fun measureWidth(source: Component?): Float {
        return widthSource.width(source!!)
    }

    fun measureWidth(source: String?): Float {
        return widthSource.width(source!!, Style.empty())
    }

    fun measureWidth(source: String?, style: Style?): Float {
        return widthSource.width(source!!, style!!)
    }

    fun measureWidth(source: Char): Float {
        return widthSource.width(source, Style.empty())
    }

    fun measureWidth(source: Char, style: Style?): Float {
        return widthSource.width(source, style!!)
    }

    fun isValidMinecraftFont(text: String?): Boolean {
        return font.isValid(text!!)
    }
}