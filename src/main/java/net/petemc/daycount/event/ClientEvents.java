package net.petemc.daycount.event;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.petemc.daycount.DayCount;
import net.petemc.daycount.config.MainConfig;
import net.petemc.daycount.util.KeyBinding;

public class ClientEvents {

    @Mod.EventBusSubscriber(modid = DayCount.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void onKeyInput(InputEvent.Key event) {
            if (KeyBinding.DAYCOUNT_KEY.consumeClick()) {
                MainConfig.toggleDayCountEnabled();
            }
        }
    }

    @Mod.EventBusSubscriber(modid = DayCount.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(KeyBinding.DAYCOUNT_KEY);
        }
    }
}
