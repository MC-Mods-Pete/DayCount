package net.petemc.daycount.client;

import net.petemc.daycount.DayCount;
import net.petemc.daycount.config.MainConfig;
import net.petemc.daycount.util.ColorUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import org.joml.Matrix3x2fStack;

import java.text.NumberFormat;

@Environment(EnvType.CLIENT)
public class DayCountClient implements ClientModInitializer {
    private static final Identifier DAY_COUNT_HUD_ELEMENT = Identifier.of(DayCount.MOD_ID, "daycount_hud");

    @Override
    public void onInitializeClient() {
        DayCount.LOGGER.info("Initializing Day Count Mod");
        MainConfig.init();
        KeyInputHandler.register();
        HudElementRegistry.addLast(DAY_COUNT_HUD_ELEMENT, (drawContext, tickCounter) -> onHudRender(drawContext));
    }

    private void onHudRender(DrawContext drawContext) {
        MinecraftClient mcClient = MinecraftClient.getInstance();
        if (MainConfig.isDayCountEnabled() && !mcClient.options.hudHidden) {
            if (mcClient.player != null && mcClient.world != null) {
                assert mcClient.interactionManager != null;
                if ((mcClient.interactionManager.getCurrentGameMode().isSurvivalLike() || mcClient.interactionManager.getCurrentGameMode().isCreative()) &&
                        (!mcClient.getDebugHud().shouldShowDebugHud() || MainConfig.getDisplayDayCountWhileShowingF3Info())) {
                    TextRenderer textRenderer = mcClient.textRenderer;
                    Matrix3x2fStack matrixStack = drawContext.getMatrices();

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

                    matrixStack.pushMatrix();
                    matrixStack.translate(MainConfig.getLocationX(), MainConfig.getLocationY());
                    matrixStack.scale(MainConfig.getSizeX(), MainConfig.getSizeY());

                    // Box Rendering
                    if (MainConfig.isBoxEnabled()) {
                        int boxColor = ColorUtil.parseArgb(MainConfig.getBoxColorWithTransparency(), ColorUtil.DEFAULT_BOX_COLOR);
                        // Draw a rectangle with padding around the text
                        drawContext.fill(textX - 2, textY - 2, textX + textWidth + 1, textY + textHeight + 1, boxColor);
                    }

                    // Text Rendering
                    drawContext.drawTextWithShadow(textRenderer, text, textX, textY, ColorUtil.parseArgb(MainConfig.getTextColorWithTransparency(), ColorUtil.DEFAULT_TEXT_COLOR));
                    matrixStack.popMatrix();
                }
            }
        }
    }
}
