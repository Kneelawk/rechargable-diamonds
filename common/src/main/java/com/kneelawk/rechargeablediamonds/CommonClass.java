package com.kneelawk.rechargeablediamonds;

import net.minecraft.network.chat.Component;

import com.kneelawk.rechargeablediamonds.platform.Services;

public class CommonClass {
    public static void init() {
        Constants.LOG.info("Hello from Common init on {}! we are currently in a {} environment!",
            Services.PLATFORM.getPlatformName(), Services.PLATFORM.getEnvironmentName());
    }

    public static Component diamondTooltip(int energy, int maxEnergy) {
        return Constants.tooltip("diamond_energy", ComponentUtil.metricNumber(energy),
            ComponentUtil.metricNumber(maxEnergy),
            Constants.tooltip("units." + Services.PLATFORM.getUnitsTranslatableSuffix()));
    }
}
