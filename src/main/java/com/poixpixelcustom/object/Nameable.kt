package com.poixpixelcustom.`object`

interface Nameable {
    /**
     * Get the name of the specified object
     *
     * @return A String representing the name of the object.
     */
    val name: String
    val formattedName: String?
        /**
         * Gets the formatted name of the object.
         *
         * @return The formatted name.
         */
        get() = name.replace('_', ' ')
}