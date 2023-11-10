package com.kneelawk.rechargeablediamonds;

import net.minecraftforge.energy.EnergyStorage;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.Mth;
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
            energy = Mth.clamp(tag.getInt(ENERGY_KEY), 0, capacity);
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
        if (!canReceive()) return 0;

        int count = stack.getCount();
        int maxReceivePerCount = maxReceive / count;
        int currentEnergyPerCount = getEnergyStored() / count;
        int receivedPerCount =
            Math.min(this.maxReceive, Math.min(maxReceivePerCount, capacity - currentEnergyPerCount));

        if (!simulate && receivedPerCount > 0) {
            energy = currentEnergyPerCount + receivedPerCount;
            writeEnergy();
        }

        return receivedPerCount * count;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        if (!canExtract()) return 0;

        int count = stack.getCount();
        int maxExtractPerCount = maxExtract / count;
        int currentEnergyPerCount = getEnergyStored() / count;
        int extractedPerCount = Math.min(this.maxExtract, Math.min(maxExtractPerCount, currentEnergyPerCount));

        if (!simulate && extractedPerCount > 0) {
            energy = currentEnergyPerCount - extractedPerCount;
            writeEnergy();
        }

        return extractedPerCount * count;
    }

    @Override
    public int getEnergyStored() {
        return energy * stack.getCount();
    }

    @Override
    public int getMaxEnergyStored() {
        return capacity * stack.getCount();
    }
}
