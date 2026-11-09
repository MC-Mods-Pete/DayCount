package net.petemc.daycount.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;

public class KeyBinding {
    public static final String KEY_CATEGORY_DAY_COUNT = "key.category.day_count_keybind";
    public static final String KEY_DAY_COUNT = "key.daycount.day_count";

    public static final KeyMapping.Category DAYCOUNT_CATEGORY = new KeyMapping.Category(Identifier.parse(KEY_CATEGORY_DAY_COUNT));

    public static final KeyMapping DAYCOUNT_KEY = new KeyMapping(KEY_DAY_COUNT, InputConstants.KEYCODE_B, DAYCOUNT_CATEGORY);
}
