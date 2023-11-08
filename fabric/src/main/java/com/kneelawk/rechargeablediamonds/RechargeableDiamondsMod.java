package com.kneelawk.rechargeablediamonds;

import team.reborn.energy.api.EnergyStorage;

import net.fabricmc.api.ModInitializer;

import net.minecraft.world.item.Items;

public class RechargeableDiamondsMod implements ModInitializer {

    @Override
    public void onInitialize() {

        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
        Constants.LOG.info("Hello Fabric world!");
        CommonClass.init();

        // Register TechReborn energy item
        EnergyStorage.ITEM.registerForItems(
            (stack, ctx) -> DiamondComponentInitializer.DIAMOND_ENERGY_KEY.get(stack).getStorage(), Items.DIAMOND);
    }
}
