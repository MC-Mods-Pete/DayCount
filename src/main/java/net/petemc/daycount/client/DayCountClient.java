package net.petemc.daycount.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.gui.DrawableHelper;
import net.petemc.daycount.DayCount;
import net.petemc.daycount.config.MainConfig;
import net.petemc.daycount.util.ColorUtil;

import java.text.NumberFormat;

@Environment(EnvType.CLIENT)
public class DayCountClient implements ClientModInitializer, HudRenderCallback {
    @Override
    public void onInitializeClient() {
        DayCount.LOGGER.info("Initializing Day Count Mod");
        MainConfig.init();
        KeyInputHandler.register();
        HudRenderCallback.EVENT.register(this);
    }

    @Override
    public void onHudRender(MatrixStack matrixStack, float v) {
        MinecraftClient mcClient = MinecraftClient.getInstance();
        if (MainConfig.isDayCountEnabled() && !mcClient.options.hudHidden) {
            if (mcClient.player != null && mcClient.world != null) {
                if ((!mcClient.player.isSpectator() && (!mcClient.options.debugEnabled || MainConfig.getDisplayDayCountWhileShowingF3Info()))) {
                    TextRenderer textRenderer = mcClient.textRenderer;

                    int currentDay = (int) (mcClient.world.getTimeOfDay() / 24000L);
                    int dayCount = (currentDay + MainConfig.getDayOffset());
                    String formattedDayCount = MainConfig.getUseLocaleFormatting()
                            ? NumberFormat.getInstance().format(dayCount)
                            : String.valueOf(dayCount);

                    String text = MainConfig.getDayCounterString() + formattedDayCount;
                    int textWidth = textRenderer.getWidth(text);
                    int textHeight = textRenderer.fontHeight;
                    int textX = 0;
                    int textY = 0;

                    matrixStack.push();
                    matrixStack.translate(MainConfig.getLocationX(), MainConfig.getLocationY(), 0);
                    matrixStack.scale(MainConfig.getSizeX(), MainConfig.getSizeY(), 2.5f);

                    // Box Rendering
                    if (MainConfig.isBoxEnabled()) {
                        int boxColor = ColorUtil.parseArgb(MainConfig.getBoxColorWithTransparency(), ColorUtil.DEFAULT_BOX_COLOR);
                        // Draw a rectangle with padding around the text
                        DrawableHelper.fill(matrixStack, textX - 2, textY - 2, textX + textWidth + 1, textY + textHeight + 1, boxColor);
                    }

                    // Text Rendering
                    textRenderer.drawWithShadow(matrixStack, text, textX, textY, ColorUtil.parseArgb(MainConfig.getTextColor(), ColorUtil.DEFAULT_TEXT_COLOR));
                    matrixStack.pop();
                }
            }
        }
    }
}
