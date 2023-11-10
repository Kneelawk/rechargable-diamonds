package com.kneelawk.rechargeablediamonds;

import org.jetbrains.annotations.Nullable;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.item.ItemComponent;

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;

import net.minecraft.world.item.ItemStack;

/**
 * Jank Cardinal Components to work with the Fabric Transfer API.
 *
 * @param <C> the implementing class.
 */
@SuppressWarnings("UnstableApiUsage")
public abstract class TransferApiItemComponent<C extends TransferApiItemComponent<C>> extends ItemComponent {
    protected @Nullable ContainerItemContext ctx;

    public TransferApiItemComponent(ItemStack stack) {
        super(stack);
    }

    public TransferApiItemComponent(ItemStack stack, ComponentKey<?> key) {
        super(stack, key);
    }

    /**
     * Apply a context to this component.
     * <p>
     * WARNING: this assumes that this component's {@link ItemStack} is a copy and can have its count changed to reflect
     * the provided {@link ContainerItemContext}.
     *
     * @param ctx the actual backing container item context for this component.
     * @return this component.
     */
    @SuppressWarnings("unchecked")
    public C withContext(ContainerItemContext ctx) {
        this.ctx = ctx;
        stack.setCount((int) ctx.getAmount());
        return (C) this;
    }

    public void applyNbt(@Nullable TransactionContext transaction) {
        if (ctx == null) return;

        long count = ctx.getAmount();
        ItemVariant oldVariant = ctx.getItemVariant();
        ItemVariant newVariant = ItemVariant.of(stack);

        try (Transaction nested = Transaction.openNested(transaction)) {
            if (ctx.extract(oldVariant, count, nested) == ctx.insert(newVariant, count, nested)) {
                nested.commit();
            }
        }
    }
}
