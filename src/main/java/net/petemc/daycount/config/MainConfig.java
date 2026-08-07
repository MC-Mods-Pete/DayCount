package net.petemc.daycount.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import net.petemc.daycount.DayCount;

@Config(name = DayCount.MOD_ID)
public class MainConfig implements ConfigData
{
    public static boolean isDayCountEnabled() { return INSTANCE.dayCountEnabled; }

    public static boolean getDisplayDayCountWhileShowingF3Info() { return INSTANCE.displayDayCountWhileShowingF3Info; }

    public static int getDayOffset() { return INSTANCE.dayOffset; }

    public static float getSizeX() { return INSTANCE.sizeX; }

    public static float getSizeY() { return INSTANCE.sizeY; }

    public static float getLocationX() { return INSTANCE.locationX; }

    public static float getLocationY() { return INSTANCE.locationY; }

    public static String getTextColorWithTransparency() { return INSTANCE.textColorWithTransparency; }

    public static String getDayCounterString() { return INSTANCE.dayCounterString; }

    public static boolean getUseLocaleFormatting() { return INSTANCE.useLocaleFormatting; }

    public static boolean isBoxEnabled() { return INSTANCE.boxEnabled; }

    public static String getBoxColorWithTransparency() { return INSTANCE.boxColorWithTransparency; }

    @ConfigEntry.Gui.Excluded
    public static MainConfig INSTANCE;

    public static void init() {
        AutoConfig.register(MainConfig.class, JanksonConfigSerializer::new);
        INSTANCE = AutoConfig.getConfigHolder(MainConfig.class).getConfig();
    }

    @ConfigEntry.Gui.Tooltip()
    @Comment("If true, the Day Count will be displayed | default: true")
    public boolean dayCountEnabled = true;

    @ConfigEntry.Gui.Tooltip()
    @Comment("If true, the Day Count will be displayed even when the debug info (F3) is shown | default: false")
    public boolean displayDayCountWhileShowingF3Info = false;

    @ConfigEntry.Gui.Tooltip()
    @Comment("Offset to add to the Day Count | default: 1")
    public int dayOffset = 1;

    @ConfigEntry.Gui.Tooltip()
    @Comment("Horizontal size of the Day Counter | default: 2.0")
    public float sizeX = 2.0f;

    @ConfigEntry.Gui.Tooltip()
    @Comment("Vertical size of the Day Counter | default: 2.0")
    public float sizeY = 2.0f;

    @ConfigEntry.Gui.Tooltip()
    @Comment("Horizontal position of the Day Counter | default: 2.0")
    public float locationX = 2.0f;

    @ConfigEntry.Gui.Tooltip()
    @Comment("Vertical position of the Day Counter | default: 2.0")
    public float locationY = 2.0f;

    @ConfigEntry.Gui.Tooltip()
    @Comment("Color of the Day Counter (with transparency) | default: FFFFFFFF (the first FFs are the transparency value)")
    public String textColorWithTransparency = "FFFFFFFF";

    @ConfigEntry.Gui.Tooltip()
    @Comment("DayCounter String | default: 'Day: '")
    public String dayCounterString = "Day: ";

    @ConfigEntry.Gui.Tooltip()
    @Comment("If true, the Day Count number will be formatted with locale-specific separators (e.g. 1,000) | default: false")
    public boolean useLocaleFormatting = false;

    @ConfigEntry.Gui.Tooltip()
    @Comment("Enable background box | default: false")
    public boolean boxEnabled = false;

    @ConfigEntry.Gui.Tooltip()
    @Comment("Color of the Box (with transparency) | default: 80000000")
    public String boxColorWithTransparency = "80000000";
}

