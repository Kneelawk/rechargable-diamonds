package com.kneelawk.rechargeablediamonds.client;

import team.reborn.energy.api.EnergyStorage;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;

import net.minecraft.world.item.Items;

import com.kneelawk.rechargeablediamonds.CommonClass;
import com.kneelawk.rechargeablediamonds.DiamondComponentInitializer;
import com.kneelawk.rechargeablediamonds.DiamondEnergyComponent;

public class RechargeableDiamondsClientMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ItemTooltipCallback.EVENT.register((stack, context, lines) -> {
            if (!stack.is(Items.DIAMOND)) return;
            DiamondEnergyComponent component = DiamondComponentInitializer.DIAMOND_ENERGY_KEY.getNullable(stack);
            if (component == null) return;
            EnergyStorage energy = component.getStorage();
            lines.add(1, CommonClass.diamondTooltip((int) energy.getAmount(), (int) energy.getCapacity()));
        });
    }
}
