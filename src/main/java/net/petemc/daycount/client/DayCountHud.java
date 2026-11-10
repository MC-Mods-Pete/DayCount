package net.petemc.daycount.client;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.petemc.daycount.DayCount;
import net.petemc.daycount.config.MainConfig;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3x2fStack;

@OnlyIn(Dist.CLIENT)
public class DayCountHud implements Gui.RenderFunction {
    public static DayCountHud DAY_COUNT_HUD_INSTANCE;
    private static String currentTextColor = "FFFFFFFF";

    public static void setCurrentTextColor(String value) {
        currentTextColor = value;
    }

    public String getCurrentTextColor() {
        return currentTextColor;
    }

    public static void init() {
        DAY_COUNT_HUD_INSTANCE = new DayCountHud();
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, @NotNull DeltaTracker deltaTracker) {
        // RenderGuiEvent.Post also fires while the HUD is hidden (F1), so check hideGui here
        if (DayCount.dayCountEnabled && !Minecraft.getInstance().options.hideGui) {
            Minecraft mc = Minecraft.getInstance();
            assert mc.level != null;
            int currentDay = (int) (mc.level.getDayTime() / 24000L);
            assert mc.gameMode != null;
            if ((mc.gameMode.getPlayerMode().isSurvival() || mc.gameMode.getPlayerMode().isCreative()) &&
                    !mc.getDebugOverlay().showDebugScreen()) {
                Matrix3x2fStack matrixStack = guiGraphics.pose();
                matrixStack.pushMatrix();
                matrixStack.translate(MainConfig.getLocationX(), MainConfig.getLocationY(), matrixStack);
                matrixStack.scale(MainConfig.getSizeX(), MainConfig.getSizeY(), matrixStack);
                guiGraphics.drawString(mc.font, MainConfig.getDayCounterString() + (currentDay + MainConfig.getDayOffset()), 1, 1, (int) Long.parseLong(currentTextColor, 16));
                matrixStack.popMatrix();
            }
        }
    }
}
