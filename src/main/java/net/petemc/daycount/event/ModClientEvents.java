package net.petemc.daycount.event;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.petemc.daycount.DayCount;
import net.petemc.daycount.DayCountClient;
import net.petemc.daycount.client.DayCountHud;
import net.petemc.daycount.util.KeyBinding;

@EventBusSubscriber (modid = DayCount.MOD_ID, value = Dist.CLIENT)
public class ModClientEvents {
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (KeyBinding.DAYCOUNT_KEY.consumeClick()) {
            DayCountClient.dayCountEnabled = !DayCountClient.dayCountEnabled;
        }
    }

    @SubscribeEvent
    public static void registerGuiOverlays(RenderGuiEvent.Post event) {
        DayCountHud.DAY_COUNT_HUD_INSTANCE.render(event.getGuiGraphics(), event.getPartialTick());
    }

    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {
        event.registerCategory(KeyBinding.DAYCOUNT_CATEGORY);
        event.register(KeyBinding.DAYCOUNT_KEY);
    }
}
