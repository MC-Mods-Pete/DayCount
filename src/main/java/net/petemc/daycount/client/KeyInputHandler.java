package net.petemc.daycount.client;

import net.petemc.daycount.config.MainConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_DAY_COUNT = "key.category.day_count_keybind";
    public static final String KEY_DAY_COUNT = "key.daycount.day_count";

    public static KeyBinding dayCountKey;
    private static int debounce = 5;

    public static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (dayCountKey.isPressed() && (debounce == 5)) {
                debounce--;
                MainConfig.INSTANCE.dayCountEnabled = !MainConfig.INSTANCE.dayCountEnabled;
            }
            if (debounce < 5) {
                debounce--;
            }
            if (debounce == 0) {
                debounce = 5;
            }
        });
    }

    public static void register(){
        dayCountKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(KEY_DAY_COUNT, InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_B, KEY_CATEGORY_DAY_COUNT));
        registerKeyInputs();
    }
}
