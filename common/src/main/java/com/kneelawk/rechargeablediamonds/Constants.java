package com.kneelawk.rechargeablediamonds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

public class Constants {

    public static final String MOD_ID = "rechargeable_diamonds";
    public static final String MOD_NAME = "RechargeableDiamonds";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static final int DIAMOND_CAPACITY = 100000;
    public static final int MAX_INSERT = 10000;
    public static final int MAX_EXTRACT = 10000;

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static MutableComponent tt(String prefix, String path, Object... args) {
        return Component.translatable(prefix + "." + MOD_ID + "." + path, args);
    }

    public static MutableComponent tooltip(String path, Object... args) {
        return tt("tooltip", path, args);
    }
}
