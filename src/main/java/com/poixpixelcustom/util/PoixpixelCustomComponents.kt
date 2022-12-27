package com.poixpixelcustom.util

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.jetbrains.annotations.ApiStatus

/**
 * Internal utility class for common interactions with adventure components.
 */
@ApiStatus.Internal
object PoixpixelCustomComponents {
    // A minimessage instance with no tags
    private val EMPTY = MiniMessage.builder().tags(TagResolver.empty()).build()
    fun miniMessage(string: String): Component {
        return MiniMessage.miniMessage().deserialize(Colors.translateLegacyCharacters(Colors.translateLegacyHex(string))!!)
    }

    fun plain(component: Component): String {
        return PlainTextComponentSerializer.plainText().serialize(component)
    }

    /**
     * Converts legacy text to a component
     * @param string The input string with legacy ampersand/section characters
     * @return The component equivalent
     */
    fun legacy(string: String): Component {
        return LegacyComponentSerializer.legacySection().deserialize(Colors.translateColorCodes(string))
    }

    /**
     * Converts a component to a legacy string using the legacy serializer
     * @param component The component to convert
     * @return A string with legacy section characters
     */
    fun toLegacy(component: Component): String {
        return LegacyComponentSerializer.legacySection().serialize(component)
    }

    /**
     * Strips all tags known to the default minimessage instance.
     * @param input The input
     * @return The stripped output
     */
    fun stripTags(input: String): String {
        return MiniMessage.miniMessage().stripTags(input)
    }

    /**
     * Strips the specified tags from the input
     * @param input The input that may contain tags
     * @param resolvers The resolver(s) for tags to strip from the input
     * @return The stripped output
     */

    fun joinList(components: List<Component?>, delimiter: Component?): Component {
        var full: Component = Component.empty()
        for (i in components.indices) {
            full = Component.empty().append(full).append(components[i]!!)
            if (i != components.size - 1) full = Component.empty().append(full).append(delimiter!!)
        }
        return full
    }
}
