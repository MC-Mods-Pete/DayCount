package net.petemc.daycount;

import net.fabricmc.api.ModInitializer;
import net.petemc.daycount.config.MainConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DayCount implements ModInitializer {
    public static final String MOD_ID = "daycount";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("client side mod, ignoring server side");
        //MainConfig.init();
    }
}
