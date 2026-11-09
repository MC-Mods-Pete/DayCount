package net.petemc.daycount.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.petemc.daycount.config.MainConfig;
import net.petemc.daycount.util.ColorUtil;
import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;

@OnlyIn(Dist.CLIENT)
public class DayCountHud implements LayeredDraw.Layer {
    public static DayCountHud DAY_COUNT_HUD_INSTANCE;

    public static void init() { DAY_COUNT_HUD_INSTANCE = new DayCountHud(); }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, @NotNull DeltaTracker deltaTracker) {
        Minecraft mc = Minecraft.getInstance();
        if (MainConfig.getDayCountEnabled() && !mc.options.hideGui && mc.level != null && mc.gameMode != null) {
            int currentDay = (int) (mc.level.getDayTime() / 24000L);
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

                PoseStack matrixStack = guiGraphics.pose();
                matrixStack.pushPose();
                matrixStack.translate(MainConfig.getLocationX(), MainConfig.getLocationY(), 0);
                matrixStack.scale(MainConfig.getSizeX(), MainConfig.getSizeY(), 2.5f);


                // Box Rendering
                if (MainConfig.isBoxEnabled()) {
                    int boxColor = ColorUtil.parseArgb(MainConfig.getBoxColorWithTransparency(), ColorUtil.DEFAULT_BOX_COLOR);
                    // Draw a rectangle with padding around the text
                    guiGraphics.fill(textX - 2, textY - 2, textX + textWidth + 1, textY + textHeight + 1, boxColor);
                }

                // Text Rendering
                guiGraphics.drawString(mc.font, text, textX, textY, ColorUtil.parseArgb(MainConfig.getTextColor(), ColorUtil.DEFAULT_TEXT_COLOR));

                matrixStack.popPose();
            }
        }
    }
}
