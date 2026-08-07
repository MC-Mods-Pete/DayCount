package net.petemc.daycount.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.listener.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.petemc.daycount.*;

@Mod.EventBusSubscriber(modid = DayCount.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MainConfig
{
    public static boolean getDayCountEnabled() { return dayCountEnabled; }

    public static boolean getDisplayDayCountWhileShowingF3Info() { return displayDayCountWhileShowingF3Info; }

    public static int getDayOffset() {
        return dayOffset;
    }

    public static float getSizeX() {
        return sizeX;
    }

    public static float getSizeY() {
        return sizeY;
    }

    public static float getLocationX() {
        return locationX;
    }

    public static float getLocationY() {
        return locationY;
    }

    public static String getTextColorWithTransparency() { return textColorWithTransparency; }

    public static String getDayCounterString() { return dayCounterString; }

    public static boolean getUseLocaleFormatting() { return useLocaleFormatting; }

    public static boolean isBoxEnabled() { return isBoxEnabled; }

    public static String getBoxColorWithTransparency() { return boxColorWithTransparency; }

    // Server Config
    private static final ForgeConfigSpec.Builder BUILDER_SERVER = new ForgeConfigSpec.Builder();
    // no server config
    public static final ForgeConfigSpec SPEC_SERVER = BUILDER_SERVER.build();

    // Client Config
    private static final ForgeConfigSpec.Builder BUILDER_CLIENT = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue DAY_COUNT_ENABLED = BUILDER_CLIENT
            .comment("If true, the Day Count will be displayed | default: true")
            .define("dayCountEnabled", true);

    private static final ForgeConfigSpec.BooleanValue DISPLAY_DAY_COUNT_WHILE_SHOWING_F3 = BUILDER_CLIENT
            .comment("If true, the Day Count will be displayed even when the debug info (F3) is shown | default: false")
            .define("displayDayCountWhileShowingF3Info", false);

    private static final ForgeConfigSpec.IntValue DAY_OFFSET = BUILDER_CLIENT
            .comment("Offset to add to the Day Count | default: 1")
            .defineInRange("dayOffset", 1, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.DoubleValue SIZE_X = BUILDER_CLIENT
            .comment("Horizontal size of the Day Counter | default: 2.0")
            .defineInRange("sizeX", 2.0, 0.0, 20000.0);

    private static final ForgeConfigSpec.DoubleValue SIZE_Y = BUILDER_CLIENT
            .comment("Vertical size of the Day Counter | default: 2.0")
            .defineInRange("sizeY", 2.0, 0.0, 20000.0);

    private static final ForgeConfigSpec.DoubleValue LOCATION_X = BUILDER_CLIENT
            .comment("Horizontal position of the Day Counter | default: 2.0")
            .defineInRange("locationX", 2.0, 0.0, 20000.0);

    private static final ForgeConfigSpec.DoubleValue LOCATION_Y = BUILDER_CLIENT
            .comment("Vertical position of the Day Counter | default: 2.0")
            .defineInRange("locationY", 2.0, 0.0, 20000.0);

    private static final ForgeConfigSpec.ConfigValue<String> TEXT_COLOR_WITH_TRANSPARENCY = BUILDER_CLIENT
            .comment("Color of the Day Counter (with transparency) | default: FFFFFFFF (the first FFs are the transparency value)")
            .define("textColorWithTransparency", "FFFFFFFF");

    private static final ForgeConfigSpec.ConfigValue<String> DAY_COUNT_STRING = BUILDER_CLIENT
            .comment("DayCounter String | default: 'Day: '")
            .define("dayCounterString", "Day: ");

    private static final ForgeConfigSpec.BooleanValue USE_LOCALE_FORMATTING = BUILDER_CLIENT
            .comment("If true, the Day Count number will be formatted with locale-specific separators (e.g. 1,000) | default: false")
            .define("useLocaleFormatting", false);

    private static final ForgeConfigSpec.BooleanValue BOX_ENABLED = BUILDER_CLIENT
            .comment("If true, the box will be displayed | default: false")
            .define("isBoxEnabled", false);

    private static final ForgeConfigSpec.ConfigValue<String> BOX_COLOR_WITH_TRANSPARENCY = BUILDER_CLIENT
            .comment("Color of the box (with transparency) | default: 80000000")
            .define("boxColorWithTransparency", "80000000");

    public static final ForgeConfigSpec SPEC_CLIENT = BUILDER_CLIENT.build();

    private static boolean dayCountEnabled = true;
    private static boolean displayDayCountWhileShowingF3Info = false;
    private static int dayOffset = 1;
    private static float sizeX = 2.0f;
    private static float sizeY = 2.0f;
    private static float locationX = 2.0f;
    private static float locationY = 2.0f;
    private static String textColorWithTransparency = "FFFFFFFF";
    private static String dayCounterString = "Day: ";
    private static boolean useLocaleFormatting = false;
    private static boolean isBoxEnabled = false;
    private static String boxColorWithTransparency = "80000000";

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        if (SPEC_SERVER.isLoaded()) {
            DayCount.LOGGER.info("Loading {} server config", DayCount.MOD_ID);
            // no server config
        }
        if (SPEC_CLIENT.isLoaded()) {
            DayCount.LOGGER.info("Loading {} client config", DayCount.MOD_ID);
            dayCountEnabled = DAY_COUNT_ENABLED.get();
            displayDayCountWhileShowingF3Info = DISPLAY_DAY_COUNT_WHILE_SHOWING_F3.get();
            dayOffset = DAY_OFFSET.get();
            sizeX = SIZE_X.get().floatValue();
            sizeY = SIZE_Y.get().floatValue();
            locationX = LOCATION_X.get().floatValue();
            locationY = LOCATION_Y.get().floatValue();
            textColorWithTransparency = TEXT_COLOR_WITH_TRANSPARENCY.get();
            dayCounterString = DAY_COUNT_STRING.get();
            useLocaleFormatting = USE_LOCALE_FORMATTING.get();
            isBoxEnabled = BOX_ENABLED.get();
            boxColorWithTransparency = BOX_COLOR_WITH_TRANSPARENCY.get();
        }
    }
}
