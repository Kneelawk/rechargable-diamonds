package com.kneelawk.rechargeablediamonds;

import team.reborn.energy.api.base.SimpleEnergyStorage;

import net.fabricmc.fabric.api.transfer.v1.storage.StoragePreconditions;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;

import net.minecraft.world.item.ItemStack;

@SuppressWarnings("UnstableApiUsage")
public abstract class StackEnergyStorage extends SimpleEnergyStorage {
    private final ItemStack stack;

    public StackEnergyStorage(ItemStack stack, long capacity, long maxInsert, long maxExtract) {
        super(capacity, maxInsert, maxExtract);
        this.stack = stack;
    }

    @Override
    public long insert(long maxAmount, TransactionContext transaction) {
        StoragePreconditions.notNegative(maxAmount);

        long count = stack.getCount();

        long maxAmountPerCount = maxAmount / count;
        long currentAmountPerCount = getAmount() / count;
        long insertedPerCount = Math.min(maxInsert, Math.min(maxAmountPerCount, capacity - currentAmountPerCount));

        if (insertedPerCount > 0) {
            updateSnapshots(transaction);
            amount = currentAmountPerCount + insertedPerCount;
        }

        return insertedPerCount * count;
    }

    @Override
    public long extract(long maxAmount, TransactionContext transaction) {
        StoragePreconditions.notNegative(maxAmount);

        long count = stack.getCount();

        long maxAmountPerCount = maxAmount / count;
        long currentAmountPerCount = getAmount() / count;
        long extractedPerCount = Math.min(maxExtract, Math.min(maxAmountPerCount, currentAmountPerCount));

        if (extractedPerCount > 0) {
            updateSnapshots(transaction);
            amount = currentAmountPerCount - extractedPerCount;
        }

        return extractedPerCount * count;
    }

    @Override
    public long getAmount() {
        return stack.getCount() * amount;
    }

    @Override
    public long getCapacity() {
        return stack.getCount() * capacity;
    }
}
