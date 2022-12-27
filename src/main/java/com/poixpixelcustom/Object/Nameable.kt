package com.poixpixelcustom.Object;

public interface Nameable {
    /**
     * Get the name of the specified object
     *
     * @return A String representing the name of the object.
     */
    String getName();

    /**
     * Gets the formatted name of the object.
     *
     * @return The formatted name.
     */
    default String getFormattedName() {
        return getName().replace('_', ' ');
    }
}
