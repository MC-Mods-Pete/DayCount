package net.petemc.daycount;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.petemc.daycount.config.MainConfig;
import net.petemc.daycount.handler.KeyInputHandler;

@Environment(EnvType.CLIENT)
public class DayCountClient implements ClientModInitializer {
    Identifier dayCountHudElement = Identifier.fromNamespaceAndPath(DayCount.MOD_ID, "daycount_hud");

    public static void setCurrentTextColor(String value) {
        currentTextColor = value;
    }
    public String getCurrentTextColor() {
        return currentTextColor;
    }
    private static String currentTextColor = "FFFFFFFF";

    @Override
    public void onInitializeClient() {
        DayCount.LOGGER.info("Initializing DayCount mod for Fabric");
        MainConfig.init();
        KeyInputHandler.register();
        setCurrentTextColor(MainConfig.getTextColorWithTransparency());

        HudElementRegistry.addLast(dayCountHudElement, (guiGraphics, tCounter) -> {
            if (MainConfig.isDayCountEnabled()) {
                Minecraft mc = Minecraft.getInstance();
                assert mc.level != null;
                int currentDay = (int) (Minecraft.getInstance().level.getOverworldClockTime() / 24000L);
                assert mc.gameMode != null;
                if ((mc.gameMode.getPlayerMode().isSurvival() || mc.gameMode.getPlayerMode().isCreative()) &&
                        (!mc.getDebugOverlay().showDebugScreen() || MainConfig.getDisplayDayCountWhileShowingF3Info())) {

                    String text = MainConfig.getDayCounterString() + (currentDay + MainConfig.getDayOffset());
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
                        int boxColor = (int) Long.parseLong(MainConfig.getBoxColorWithTransparency(), 16);
                        // Draw a rectangle with padding around the text
                        guiGraphics.fill(textX - 2, textY - 2, textX + textWidth + 1, textY + textHeight + 1, boxColor);
                    }

                    // Text Rendering
                    guiGraphics.text(mc.font, text, textX, textY, (int) Long.parseLong(currentTextColor, 16));

                    pose.popMatrix();
                }
            }
        });
    }
}
