package net.petemc.daycount.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import net.petemc.daycount.DayCount;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {
    public static final Identifier KEY_CATEGORY_DAY_COUNT = Identifier.fromNamespaceAndPath(DayCount.MOD_ID, "day_count");
    public static final String KEY_DAY_COUNT = "key.daycount.day_count";

    public static final KeyMapping DAYCOUNT_KEY = new KeyMapping(
            KEY_DAY_COUNT,
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_B,
            KeyMapping.Category.register(KEY_CATEGORY_DAY_COUNT),
            0);
}
