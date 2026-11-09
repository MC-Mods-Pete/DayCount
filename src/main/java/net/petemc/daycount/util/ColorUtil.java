package net.petemc.daycount.util;

import net.petemc.daycount.DayCount;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public final class ColorUtil {
    public static final int DEFAULT_TEXT_COLOR = 0xFFFFFFFF;
    public static final int DEFAULT_BOX_COLOR = 0x80000000;

    private static final Pattern HEX_COLOR = Pattern.compile("[0-9a-fA-F]{6}|[0-9a-fA-F]{8}");
    private static final Set<String> REPORTED_INVALID_VALUES = ConcurrentHashMap.newKeySet();

    private ColorUtil() {
    }

    /**
     * Parses a color from the config. Accepts AARRGGBB or RRGGBB (treated as fully opaque),
     * optionally prefixed with '#' or '0x'. Invalid values fall back to the given default
     * instead of throwing during rendering.
     */
    public static int parseArgb(String value, int fallback) {
        String hex = value == null ? "" : value.trim();
        if (hex.startsWith("#")) {
            hex = hex.substring(1);
        } else if (hex.startsWith("0x") || hex.startsWith("0X")) {
            hex = hex.substring(2);
        }

        if (HEX_COLOR.matcher(hex).matches()) {
            long color = Long.parseLong(hex, 16);
            if (hex.length() == 6) {
                color |= 0xFF000000L;
            }
            return (int) color;
        }

        if (REPORTED_INVALID_VALUES.add(String.valueOf(value))) {
            DayCount.LOGGER.warn("Invalid color '{}' in the {} config, expected AARRGGBB or RRGGBB hex, using default", value, DayCount.MOD_ID);
        }
        return fallback;
    }
}
