package net.petemc.daycount.client;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.petemc.daycount.config.MainConfig;
import net.petemc.daycount.util.ColorUtil;

import java.text.NumberFormat;

public class DayCountHud {
    public static DayCountHud DAY_COUNT_HUD_INSTANCE;

    public static void init() {
        DAY_COUNT_HUD_INSTANCE = new DayCountHud();
    }

    public void render(GuiGraphicsExtractor guiGraphics, DeltaTracker pDeltaTracker) {
        Minecraft mc = Minecraft.getInstance();
        if (MainConfig.getDayCountEnabled() && mc.level != null && mc.gameMode != null) {
            int currentDay = (int) (mc.level.getOverworldClockTime() / 24000L);
            if ((mc.gameMode.getPlayerMode().isSurvival() || mc.gameMode.getPlayerMode().isCreative()) &&
                    (!mc.getDebugOverlay().showDebugScreen() || MainConfig.getDisplayDayCountWhileShowingF3Info())) {

                int dayCount = (currentDay + MainConfig.getDayOffset());
                String formattedDayCount = MainConfig.getUseLocaleFormatting()
                        ? NumberFormat.getInstance().format(dayCount)
                        : String.valueOf(dayCount);

                String text = MainConfig.getDayCounterString() + formattedDayCount;
                int textWidth = mc.font.width(text);
                int textHeight = mc.font.lineHeight;
                int textX = 0;
                int textY = 0;

                var pose = guiGraphics.pose();
                pose.pushMatrix();
                pose.translate(MainConfig.getLocationX(), MainConfig.getLocationY());
                pose.scale(MainConfig.getSizeX(), MainConfig.getSizeY());

                // Box Rendering
                if (MainConfig.isBoxEnabled()) {
                    int boxColor = ColorUtil.parseArgb(MainConfig.getBoxColorWithTransparency(), ColorUtil.DEFAULT_BOX_COLOR);
                    // Draw a rectangle with padding around the text
                    guiGraphics.fill(textX - 2, textY - 2, textX + textWidth + 1, textY + textHeight + 1, boxColor);
                }

                // Text Rendering
                guiGraphics.text(mc.font, text, textX, textY, ColorUtil.parseArgb(MainConfig.getTextColorWithTransparency(), ColorUtil.DEFAULT_TEXT_COLOR));

                pose.popMatrix();
            }
        }
    }
}
