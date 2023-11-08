package com.kneelawk.rechargeablediamonds;

import net.minecraftforge.energy.EnergyStorage;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

public class StackEnergyStorage extends EnergyStorage {
    private static final String ENERGY_KEY = "energy";

    private final ItemStack stack;
    private final String key;

    public StackEnergyStorage(ItemStack stack, String key, int capacity) {
        super(capacity);
        this.stack = stack;
        this.key = key;
        applyTagEnergy();
    }

    public StackEnergyStorage(ItemStack stack, String key, int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
        this.stack = stack;
        this.key = key;
        applyTagEnergy();
    }

    public StackEnergyStorage(ItemStack stack, String key, int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
        this.stack = stack;
        this.key = key;
        applyTagEnergy();
    }

    public StackEnergyStorage(ItemStack stack, String key, int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
        this.stack = stack;
        this.key = key;
        writeEnergy();
    }

    private void applyTagEnergy() {
        CompoundTag tag = stack.getTagElement(key);
        if (tag != null && tag.contains(ENERGY_KEY, Tag.TAG_INT)) {
            energy = tag.getInt(ENERGY_KEY);
        }
    }

    private void writeEnergy() {
        if (energy == 0) {
            stack.removeTagKey(key);
        } else {
            stack.getOrCreateTagElement(key).putInt(ENERGY_KEY, energy);
        }
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int res = super.receiveEnergy(maxReceive, simulate);
        writeEnergy();
        return res;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int res = super.extractEnergy(maxExtract, simulate);
        writeEnergy();
        return res;
    }
}
