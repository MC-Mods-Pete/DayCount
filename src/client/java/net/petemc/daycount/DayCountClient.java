package net.petemc.daycount;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.petemc.daycount.config.MainConfig;
import net.petemc.daycount.handler.KeyInputHandler;
import net.petemc.daycount.util.ColorUtil;

import java.text.NumberFormat;

@Environment(EnvType.CLIENT)
public class DayCountClient implements ClientModInitializer {
    Identifier dayCountHudElement = Identifier.fromNamespaceAndPath(DayCount.MOD_ID, "daycount_hud");

    @Override
    public void onInitializeClient() {
        DayCount.LOGGER.info("Initializing DayCount mod for Fabric");
        MainConfig.init();
        KeyInputHandler.register();

        HudElementRegistry.addLast(dayCountHudElement, (guiGraphics, tCounter) -> {
            Minecraft mc = Minecraft.getInstance();
            if (MainConfig.isDayCountEnabled() && mc.level != null && mc.gameMode != null) {
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
                    pose.translate(MainConfig.getLocationX(), MainConfig.getLocationY(), pose);
                    pose.scale(MainConfig.getSizeX(), MainConfig.getSizeY(), pose);

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
        });
    }
}
