package net.petemc.daycount.client;

import net.petemc.daycount.config.DayCountConfig;
import java.text.NumberFormat;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class DayCountClient implements ClientModInitializer, HudRenderCallback {
    private int cachedDayTime = -1;

    @Override
    public void onInitializeClient() {
        KeyInputHandler.register();

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                int currentDayTime = (int) (MinecraftClient.getInstance().world.getTimeOfDay() / 24000L);
                if (currentDayTime != cachedDayTime) {
                    // Day time has changed, update the cached value and do any necessary updates
                    cachedDayTime = currentDayTime;
                    // Update the GUI or other elements as needed
                    MinecraftClient.getInstance().getFramebuffer().beginWrite(false);
                    HudRenderCallback.EVENT.register(this);
                }
            }
        });
    }

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        if (DayCountConfig.INSTANCE.dayCountEnabled) {
            int currentDay = (int) (MinecraftClient.getInstance().world.getTimeOfDay() / 24000L);
            TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
            MatrixStack matrixStack = drawContext.getMatrices();

            matrixStack.push();
            matrixStack.translate(DayCountConfig.INSTANCE.locationX, DayCountConfig.INSTANCE.locationY, 0);
            matrixStack.scale(DayCountConfig.INSTANCE.sizeX, DayCountConfig.INSTANCE.sizeY, 2.5f);
            int dayNumber = currentDay + DayCountConfig.INSTANCE.dayOffset;
            String formatted = DayCountConfig.INSTANCE.formatNumber
                    ? NumberFormat.getInstance().format(dayNumber)
                    : String.valueOf(dayNumber);
            drawContext.drawTextWithShadow(textRenderer, "Day: " + formatted, 2, 2, DayCountConfig.INSTANCE.color);
            matrixStack.pop();
        }
    }
}
