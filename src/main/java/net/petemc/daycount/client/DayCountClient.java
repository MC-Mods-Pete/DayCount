package net.petemc.daycount.client;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.petemc.daycount.config.MainConfig;
import net.petemc.daycount.util.ColorUtil;

import java.text.NumberFormat;

@OnlyIn(Dist.CLIENT)
public class DayCountClient {

    public static final IGuiOverlay HUD_DAY_COUNTER = ((gui, poseStack, partialTick, width, height) -> {
        Minecraft mc = Minecraft.getInstance();
        if (MainConfig.getDayCountEnabled() && !mc.options.hideGui && mc.level != null && mc.gameMode != null) {
            int currentDay = (int) (mc.level.getDayTime() / 24000L);
            if ((mc.gameMode.getPlayerMode().isSurvival() || mc.gameMode.getPlayerMode().isCreative()) &&
                    (!mc.options.renderDebug || MainConfig.getDisplayDayCountWhileShowingF3Info())) {

                int dayCount = (currentDay + MainConfig.getDayOffset());
                String formattedDayCount = MainConfig.getUseLocaleFormatting()
                        ? NumberFormat.getInstance().format(dayCount)
                        : String.valueOf(dayCount);

                String text = MainConfig.getDayCounterString() + formattedDayCount;
                int textWidth = mc.font.width(text);
                int textHeight = mc.font.lineHeight;
                int textX = 0;
                int textY = 0;

                poseStack.pushPose();
                poseStack.translate(MainConfig.getLocationX(), MainConfig.getLocationY(), 0);
                poseStack.scale(MainConfig.getSizeX(), MainConfig.getSizeY(), 2.5f);
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

                // Box Rendering
                if (MainConfig.isBoxEnabled()) {
                    int boxColor = ColorUtil.parseArgb(MainConfig.getBoxColorWithTransparency(), ColorUtil.DEFAULT_BOX_COLOR);
                    // Draw a rectangle with padding around the text
                    GuiComponent.fill(poseStack, textX - 2, textY - 2, textX + textWidth + 1, textY + textHeight + 1, boxColor);
                }

                // Text Rendering
                GuiComponent.drawString(poseStack, mc.font, text, textX, textY, ColorUtil.parseArgb(MainConfig.getTextColor(), ColorUtil.DEFAULT_TEXT_COLOR));
                poseStack.popPose();
            }
        }
    });
}
