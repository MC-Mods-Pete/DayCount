package net.petemc.daycount.client;

import net.minecraft.client.render.RenderTickCounter;
import net.petemc.daycount.DayCount;
import net.petemc.daycount.config.MainConfig;
import net.petemc.daycount.util.ColorUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

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
    public void onHudRender(DrawContext drawContext, RenderTickCounter tickCounter) {
        MinecraftClient mcClient = MinecraftClient.getInstance();
        if (MainConfig.isDayCountEnabled() && !mcClient.options.hudHidden) {
            if (mcClient.player != null && mcClient.world != null) {
                assert mcClient.interactionManager != null;
                if ((mcClient.interactionManager.getCurrentGameMode().isSurvivalLike() || mcClient.interactionManager.getCurrentGameMode().isCreative()) &&
                        (!mcClient.getDebugHud().shouldShowDebugHud() || MainConfig.getDisplayDayCountWhileShowingF3Info())) {
                    TextRenderer textRenderer = mcClient.textRenderer;
                    MatrixStack matrixStack = drawContext.getMatrices();

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
                        drawContext.fill(textX - 2, textY - 2, textX + textWidth + 1, textY + textHeight + 1, boxColor);
                    }

                    // Text Rendering
                    drawContext.drawTextWithShadow(textRenderer, text, textX, textY, ColorUtil.parseArgb(MainConfig.getTextColor(), ColorUtil.DEFAULT_TEXT_COLOR));
                    matrixStack.pop();
                }
            }
        }
    }
}
