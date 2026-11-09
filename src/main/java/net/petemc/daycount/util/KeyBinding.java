package net.petemc.daycount.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import net.petemc.daycount.DayCount;

public class KeyBinding {
    public static final String KEY_DAY_COUNT = "key.daycount.day_count";

    public static final KeyMapping.Category DAYCOUNT_CATEGORY = new KeyMapping.Category(Identifier.fromNamespaceAndPath(DayCount.MOD_ID, "day_count"));

    public static final KeyMapping DAYCOUNT_KEY = new KeyMapping(KEY_DAY_COUNT, InputConstants.KEYCODE_B, DAYCOUNT_CATEGORY);
}
