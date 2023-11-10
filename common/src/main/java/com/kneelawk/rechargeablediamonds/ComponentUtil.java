package com.kneelawk.rechargeablediamonds;

import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import static com.kneelawk.rechargeablediamonds.Constants.tooltip;

public class ComponentUtil {
    private static final String[] suffixes = {
        "metric.format.0", "metric.format.1", "metric.format.2", "metric.format.3", "metric.format.4",
        "metric.format.5", "metric.format.6", "metric.format.7", "metric.format.8", "metric.format.9"
    };

    public static Component metricNumber(int number) {
        int power = Mth.clamp((int) Math.log10(number), 0, 9) / 3 * 3;
        double chopped = (double) number / Math.pow(10, power);
        return tooltip(suffixes[power], chopped);
    }
}
