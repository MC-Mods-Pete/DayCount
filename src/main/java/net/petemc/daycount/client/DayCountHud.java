package net.petemc.daycount.client;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.neoforged.neoforge.client.gui.GuiLayer;
import net.petemc.daycount.DayCountClient;
import net.petemc.daycount.config.MainConfig;
import org.jetbrains.annotations.NotNull;

public class DayCountHud implements GuiLayer {
    public static DayCountHud DAY_COUNT_HUD_INSTANCE;

    public static void setCurrentTextColor(String value) {
        currentTextColor = value;
    }
    public String getCurrentTextColor() {
        return currentTextColor;
    }
    private static String currentTextColor = "FFFFFFFF";

    public static void init() {
        DAY_COUNT_HUD_INSTANCE = new DayCountHud();
    }

    @Override
    public void render(@NotNull GuiGraphicsExtractor guiGraphics, @NotNull DeltaTracker deltaTracker) {
        if (DayCountClient.dayCountEnabled) {
            Minecraft mc = Minecraft.getInstance();
            assert mc.level != null;
            int currentDay = (int) (mc.level.getOverworldClockTime() / 24000L);
            assert mc.gameMode != null;
            if ((mc.gameMode.getPlayerMode().isSurvival() || mc.gameMode.getPlayerMode().isCreative()) &&
                    (!mc.getDebugOverlay().showDebugScreen() || MainConfig.getDisplayDayCountWhileShowingF3Info())) {
                var pose = guiGraphics.pose();
                pose.pushMatrix();
                pose.translate(MainConfig.getLocationX(), MainConfig.getLocationY());
                pose.scale(MainConfig.getSizeX(), MainConfig.getSizeY());
                guiGraphics.text(mc.font, MainConfig.getDayCounterString() + (currentDay + MainConfig.getDayOffset()), 1, 1, (int) Long.parseLong(currentTextColor, 16));
                pose.popMatrix();
            }
        }
    }
}
