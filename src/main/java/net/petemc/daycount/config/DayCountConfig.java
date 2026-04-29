package net.petemc.daycount.config;

import net.petemc.daycount.DayCount;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = DayCount.MOD_ID)
public class DayCountConfig implements ConfigData
{
    @ConfigEntry.Gui.Excluded
    public static DayCountConfig INSTANCE;

    public static void init() {
        AutoConfig.register(DayCountConfig.class, JanksonConfigSerializer::new);
        INSTANCE = AutoConfig.getConfigHolder(DayCountConfig.class).getConfig();
    }

    @ConfigEntry.Gui.Tooltip()
    @Comment("If true, the Day Count will be displayed | default: true")
    public boolean dayCountEnabled = true;

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
    @Comment("Color of the Day Counter | default: 16777215 (for white)")
    public int color = 0xffffff;

    @ConfigEntry.Gui.Tooltip()
    @Comment("If true, the Day Count number will be formatted with locale-specific separators (e.g. 1,000) | default: false")
    public boolean formatNumber = false;
}
