package com.poixpixelcustom.constants;

import com.poixpixelcustom.PoixpixelCustom;
import org.bukkit.NamespacedKey;

public class Keys {

    private Keys() {
        throw new IllegalStateException("Utility class");
    }

    public static final NamespacedKey CUSTOM_ENTITY = new NamespacedKey(PoixpixelCustom.getInstance(), "CustomEntity");
    public static final NamespacedKey CUSTOM_BUCKET = new NamespacedKey(PoixpixelCustom.getInstance(), "CustomBucket");
}
