package net.petemc.daycount.handler;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import net.petemc.daycount.DayCount;
import net.petemc.daycount.config.MainConfig;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final KeyMapping.Category DAY_COUNT_CATEGORY =
            KeyMapping.Category.register(Identifier.fromNamespaceAndPath(DayCount.MOD_ID, "category"));
    public static final String KEY_DAY_COUNT = "key.daycount.day_count";

    public static KeyMapping dayCountKey;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (dayCountKey.consumeClick()) {
                MainConfig.INSTANCE.dayCountEnabled = !MainConfig.INSTANCE.dayCountEnabled;
            }
        });
    }

    public static void register() {
        dayCountKey = KeyMappingHelper.registerKeyMapping(
                new KeyMapping(KEY_DAY_COUNT, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_B, DAY_COUNT_CATEGORY)
        );
        registerKeyInputs();
    }
}
