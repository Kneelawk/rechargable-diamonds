package com.kneelawk.rechargeablediamonds;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.item.ItemComponent;
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.base.SimpleEnergyStorage;

import net.minecraft.world.item.ItemStack;

public class DiamondEnergyComponent extends ItemComponent {
    private static final String ENERGY_KEY = "energy";

    public DiamondEnergyComponent(ItemStack stack) {
        super(stack);
    }

    public DiamondEnergyComponent(ItemStack stack, ComponentKey<?> key) {
        super(stack, key);
    }

    private EnergyStorage storage = null;

    public EnergyStorage getStorage() {
        if (storage == null) {
            storage = new SyncingEnergyStorage();
        }
        return storage;
    }

    @Override
    public void onTagInvalidated() {
        super.onTagInvalidated();
        storage = null;
    }

    private class SyncingEnergyStorage extends SimpleEnergyStorage {
        public SyncingEnergyStorage() {
            super(Constants.DIAMOND_CAPACITY, Constants.MAX_INSERT, Constants.MAX_EXTRACT);
        }

        @Override
        protected void onFinalCommit() {
            putInt(ENERGY_KEY, (int) amount);
        }
    }
}
