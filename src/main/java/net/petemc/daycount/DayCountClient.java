package net.petemc.daycount;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.petemc.daycount.client.DayCountHud;
import net.petemc.daycount.config.MainConfig;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = DayCount.MOD_ID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = DayCount.MOD_ID, value = Dist.CLIENT)
public class DayCountClient {

    public static boolean dayCountEnabled = false;

    public DayCountClient(ModContainer modContainer) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);

        modContainer.registerConfig(ModConfig.Type.CLIENT, MainConfig.SPEC_CLIENT);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        DayCount.LOGGER.info("Initializing DayCount mod for NeoForge");
        DayCountClient.dayCountEnabled = MainConfig.getDayCountEnabled();
        DayCountHud.setCurrentTextColor(MainConfig.getTextColorWithTransparency());
        DayCountHud.init();
    }
}
