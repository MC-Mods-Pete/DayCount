package net.petemc.daycount.event;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.petemc.daycount.DayCount;
import net.petemc.daycount.config.MainConfig;
import net.petemc.daycount.client.DayCountHud;
import net.petemc.daycount.util.KeyBinding;

public class ClientEvents {

    @EventBusSubscriber (modid = DayCount.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.GAME)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if (KeyBinding.DAYCOUNT_KEY.consumeClick()) {
                MainConfig.toggleDayCountEnabled();
            }
        }

        @SubscribeEvent
        public static void registerGuiOverlays(RenderGuiEvent.Post event) {
            DayCountHud.DAY_COUNT_HUD_INSTANCE.render(event.getGuiGraphics(), event.getPartialTick());
        }
    }

    @EventBusSubscriber(modid = DayCount.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.DAYCOUNT_KEY);
        }
    }
}
