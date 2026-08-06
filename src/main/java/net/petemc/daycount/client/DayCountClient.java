package net.petemc.daycount.client;

import net.petemc.daycount.DayCount;
import net.petemc.daycount.config.DayCountConfig;
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
    private int currentDayTime = -1;
    private int cachedDayTime = -1;

    @Override
    public void onInitializeClient() {
        DayCount.LOGGER.info("Initializing Day Count Mod");
        DayCountConfig.init();
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
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        MinecraftClient mcClient = MinecraftClient.getInstance();
        if (DayCountConfig.INSTANCE.dayCountEnabled) {
            if (mcClient.player != null) {
                if ((!mcClient.player.isSpectator() && !mcClient.options.debugEnabled)) {
                    TextRenderer textRenderer = mcClient.textRenderer;
                    MatrixStack matrixStack = drawContext.getMatrices();

                    matrixStack.push();
                    matrixStack.translate(DayCountConfig.INSTANCE.locationX, DayCountConfig.INSTANCE.locationY, 0);
                    matrixStack.scale(DayCountConfig.INSTANCE.sizeX, DayCountConfig.INSTANCE.sizeY, 2.5f);
                    drawContext.drawTextWithShadow(textRenderer, "Day: " + (currentDayTime + DayCountConfig.INSTANCE.dayOffset), 2, 2, DayCountConfig.INSTANCE.color);
                    matrixStack.pop();
                }
            }
        }
    }
}
