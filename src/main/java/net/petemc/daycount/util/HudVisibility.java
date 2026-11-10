package net.petemc.daycount.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.Gui;
import net.petemc.daycount.DayCount;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/**
 * Tells whether the HUD is hidden (F1). This build runs on Minecraft 26.1 and 26.2, which store that state
 * differently: 26.1 has {@code Options.hideGui}, 26.2 has {@code Gui.hud.isHidden()}. Neither exists in both,
 * so the matching one is looked up once at runtime (26.x is not obfuscated, the names are stable).
 */
public final class HudVisibility {
    private static final MethodHandle IS_HIDDEN = lookup();

    private HudVisibility() {
    }

    public static boolean isHidden(Minecraft mc) {
        try {
            return (boolean) IS_HIDDEN.invokeExact(mc);
        } catch (Throwable t) {
            return false;
        }
    }

    private static MethodHandle lookup() {
        MethodHandles.Lookup lookup = MethodHandles.publicLookup();
        try {
            // 26.1: mc.options.hideGui
            MethodHandle options = lookup.findGetter(Minecraft.class, "options", Options.class);
            MethodHandle hideGui = lookup.findGetter(Options.class, "hideGui", boolean.class);
            return MethodHandles.filterArguments(hideGui, 0, options);
        } catch (ReflectiveOperationException ignored) {
            // not 26.1, try 26.2
        }
        try {
            // 26.2+: mc.gui.hud.isHidden()
            Class<?> hudClass = Class.forName("net.minecraft.client.gui.Hud");
            MethodHandle gui = lookup.findGetter(Minecraft.class, "gui", Gui.class);
            MethodHandle hud = lookup.findGetter(Gui.class, "hud", hudClass);
            MethodHandle isHidden = lookup.findVirtual(hudClass, "isHidden", MethodType.methodType(boolean.class));
            return MethodHandles.filterArguments(MethodHandles.filterArguments(isHidden, 0, hud), 0, gui);
        } catch (ReflectiveOperationException e) {
            DayCount.LOGGER.warn("Could not find out whether the HUD is hidden (F1), the day counter stays visible with F1", e);
            return MethodHandles.dropArguments(MethodHandles.constant(boolean.class, false), 0, Minecraft.class);
        }
    }
}
