package net.petemc.daycount.client;

import net.minecraft.client.render.RenderTickCounter;
import net.petemc.daycount.DayCount;
import net.petemc.daycount.config.MainConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

import java.text.NumberFormat;

@Environment(EnvType.CLIENT)
public class DayCountClient implements ClientModInitializer, HudRenderCallback {
    private int currentDayTime = -1;
    private int cachedDayTime = -1;

    @Override
    public void onInitializeClient() {
        DayCount.LOGGER.info("Initializing Day Count Mod");
        MainConfig.init();
        KeyInputHandler.register();

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if ((client.player != null) && (client.world != null)) {
                currentDayTime = (int) (client.world.getTimeOfDay() / 24000L);
                if (currentDayTime != cachedDayTime) {
                    // Day time has changed, update the cached value and do any necessary updates
                    cachedDayTime = currentDayTime;
                    // Update the GUI or other elements as needed
                    client.getFramebuffer().beginWrite(false);
                    HudRenderCallback.EVENT.register(this);
                }
            }
        });
    }

    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter tickCounter) {
        MinecraftClient mcClient = MinecraftClient.getInstance();
        if (MainConfig.isDayCountEnabled()) {
            if (mcClient.player != null) {
                assert mcClient.interactionManager != null;
                if ((mcClient.interactionManager.getCurrentGameMode().isSurvivalLike() || mcClient.interactionManager.getCurrentGameMode().isCreative()) &&
                        !mcClient.getDebugHud().shouldShowDebugHud()) {
                    TextRenderer textRenderer = mcClient.textRenderer;
                    MatrixStack matrixStack = drawContext.getMatrices();

                    int dayCount = (currentDayTime + MainConfig.getDayOffset());
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
                        int boxColor = (int) Long.parseLong(MainConfig.getBoxColorWithTransparency(), 16);
                        // Draw a rectangle with padding around the text
                        drawContext.fill(textX - 2, textY - 2, textX + textWidth + 1, textY + textHeight + 1, boxColor);
                    }

                    // Text Rendering
                    drawContext.drawTextWithShadow(textRenderer, text, textX, textY, (int) Long.parseLong(MainConfig.getTextColor(), 16));
                    matrixStack.pop();
                }
            }
        }
    }
}
