package com.kneelawk.rechargeablediamonds;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import team.reborn.energy.api.EnergyStorage;

import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;

import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

@SuppressWarnings("UnstableApiUsage")
public class DiamondEnergyComponent extends TransferApiItemComponent<DiamondEnergyComponent> {
    private static final String ENERGY_KEY = "energy";

    public DiamondEnergyComponent(ItemStack stack) {
        super(stack);
    }

    public DiamondEnergyComponent(ItemStack stack, ComponentKey<?> key) {
        super(stack, key);
    }

    private SyncingEnergyStorage storage = null;

    public EnergyStorage getStorage() {
        if (storage == null) {
            storage = new SyncingEnergyStorage();
            storage.amount =
                Mth.clamp(getInt(ENERGY_KEY) / Constants.FABRIC_ENERGY_FACTOR, 0, Constants.FABRIC_DIAMOND_CAPACITY);
        }
        return storage;
    }

    @Override
    public void onTagInvalidated() {
        super.onTagInvalidated();
        storage = null;
    }

    private class SyncingEnergyStorage extends StackEnergyStorage {
        public SyncingEnergyStorage() {
            super(stack, Constants.FABRIC_DIAMOND_CAPACITY, Constants.FABRIC_MAX_INSERT, Constants.FABRIC_MAX_EXTRACT);
        }

        @Override
        protected void readSnapshot(Long snapshot) {
            super.readSnapshot(snapshot);
            putInt(ENERGY_KEY, (int) amount * Constants.FABRIC_ENERGY_FACTOR);
            // applying NBT is handled by the transaction that triggered this snapshot-read
        }

        @Override
        public long insert(long maxAmount, TransactionContext transaction) {
            long res = super.insert(maxAmount, transaction);
            if (res > 0) {
                putInt(ENERGY_KEY, (int) amount * Constants.FABRIC_ENERGY_FACTOR);
                applyNbt(transaction);
            }
            return res;
        }

        @Override
        public long extract(long maxAmount, TransactionContext transaction) {
            long res = super.extract(maxAmount, transaction);
            if (res > 0) {
                putInt(ENERGY_KEY, (int) amount * Constants.FABRIC_ENERGY_FACTOR);
                applyNbt(transaction);
            }
            return res;
        }
    }
}
