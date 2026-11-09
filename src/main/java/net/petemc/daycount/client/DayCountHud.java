package net.petemc.daycount.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.petemc.daycount.DayCount;
import net.petemc.daycount.config.MainConfig;

import java.text.NumberFormat;

@OnlyIn(Dist.CLIENT)
public class DayCountHud {
    private static String currentTextColor = "FFFFFF";

    public static void setCurrentTextColor(String value) {
        currentTextColor = value;
    }

    public String getCurrentTextColor() {
        return currentTextColor;
    }

    public static final IGuiOverlay HUD_DAY_COUNTER = ((gui, guiGraphics, partialTick, width, height) -> {
        if (DayCount.dayCountEnabled) {
            Minecraft mc = Minecraft.getInstance();
            assert mc.level != null;
            int currentDay = (int) (mc.level.getDayTime() / 24000L);
            assert mc.gameMode != null;
            if (((mc.gameMode.getPlayerMode().isSurvival() || mc.gameMode.getPlayerMode().isCreative()) &&
                    !mc.options.renderDebug)) {

                int dayCount = (currentDay + MainConfig.getDayOffset());
                String formattedDayCount = MainConfig.getUseLocaleFormatting()
                        ? NumberFormat.getInstance().format(dayCount)
                        : String.valueOf(dayCount);

                String text = MainConfig.getDayCounterString() + formattedDayCount;
                int textWidth = mc.font.width(text);
                int textHeight = mc.font.lineHeight;
                int textX = 0;
                int textY = 0;

                PoseStack matrixStack = guiGraphics.pose();
                matrixStack.pushPose();
                matrixStack.translate(MainConfig.getLocationX(), MainConfig.getLocationY(), 0);
                matrixStack.scale(MainConfig.getSizeX(), MainConfig.getSizeY(), 2.5f);

                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

                // Box Rendering
                if (MainConfig.isBoxEnabled()) {
                    int boxColor = (int) Long.parseLong(MainConfig.getBoxColorWithTransparency(), 16);
                    // Draw a rectangle with padding around the text
                    guiGraphics.fill(textX - 2, textY - 2, textX + textWidth + 1, textY + textHeight + 1, boxColor);
                }

                // Text Rendering
                guiGraphics.drawString(mc.font, text, textX, textY, (int) Long.parseLong(currentTextColor, 16));

                matrixStack.popPose();
            }
        }
    });
}
