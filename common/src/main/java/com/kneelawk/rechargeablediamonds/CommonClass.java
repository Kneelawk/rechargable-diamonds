package com.kneelawk.rechargeablediamonds;

import com.kneelawk.rechargeablediamonds.platform.Services;

public class CommonClass {
    public static void init() {
        Constants.LOG.info("Hello from Common init on {}! we are currently in a {} environment!",
            Services.PLATFORM.getPlatformName(), Services.PLATFORM.getEnvironmentName());
    }
}
