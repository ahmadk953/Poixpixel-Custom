package com.poixpixelcustom.Object.Metadata

import com.poixpixelcustom.util.PoixpixelCustomComponents
import net.kyori.adventure.text.Component

abstract class CustomDataField<T> @JvmOverloads constructor(key: String, value: T? = null, label: String? = null) : Cloneable {
    var value: T? = null
    val key: String
    protected var label: String

    init {
        this.value = value
        this.key = key
        this.label = label!!
    }

    constructor(key: String, label: String?) : this(key, null, label)

    /**
     * Gets the type id for the given CustomDataField class.
     * This value is attached to the class, and not a specific instance.
     * Used for serialization purposes.
     *
     * @return type id of the given CustomDataField class.
     */
    abstract val typeID: String

    /**
     * Sets the value based on the given input.
     * Used when admins want to edit metadata in-game.
     *
     * @param strValue input.
     */
    abstract fun setValueFromString(strValue: String?)

    /**
     * Serializes the current value to a string.
     * Used for saving the CustomDataField object.
     *
     * @return serialized string
     */
    protected fun serializeValueToString(): String? {
        return value.toString()
    }

    fun shouldDisplayInStatus(): Boolean {
        return hasLabel()
    }

    fun hasLabel(): Boolean {
        return label != null
    }

    // Not used for serialization anymore. Just for human readable format.
    override fun toString(): String {
        var out = ""

        // Type
        out += typeID

        // Key
        out += "," + key

        // Value
        out += "," + value

        return out
    }

    /**
     * Determines whether the given input can be parsed to the appropriate value.
     * Used to parse admin input for in-game metadata editing.
     *
     * @param strValue admin input
     * @return whether the string can be parsed or not
     */
    // Overridable validation function
    protected fun canParseFromString(strValue: String?): Boolean {
        return true
    }

    /**
     * Formats and colors the value of the custom data field object.
     * @return the formatted value of this data field.
     */
    protected abstract fun displayFormattedValue(): String

    /**
     * Get the value as a formatted component.
     *
     * This function is intentionally overridable by child classes.
     *
     * @return formatted component of value.
     */
    fun formatValueAsComp(): Component? {
        return PoixpixelCustomComponents.miniMessage(displayFormattedValue())
    }

    override fun equals(rhs: Any?): Boolean {
        return if (rhs is CustomDataField<*>) rhs.key == key else false
    }

    override fun hashCode(): Int {
        // Use the key as a unique id
        return key.hashCode()
    }

    abstract override fun clone(): CustomDataField<T>
}