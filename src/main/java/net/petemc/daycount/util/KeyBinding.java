package net.petemc.daycount.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import net.minecraftforge.client.settings.KeyConflictContext;

public class KeyBinding {
    public static final Identifier KEY_CATEGORY_DAY_COUNT = Identifier.withDefaultNamespace("key.category.day_count_keybind");
    public static final String KEY_DAY_COUNT = "key.daycount.day_count";

    public static final KeyMapping DAYCOUNT_KEY = new KeyMapping(
            KEY_DAY_COUNT,
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYBOARD,
            InputConstants.KEY_B,
            KeyMapping.Category.register(KEY_CATEGORY_DAY_COUNT),
            0);
}
