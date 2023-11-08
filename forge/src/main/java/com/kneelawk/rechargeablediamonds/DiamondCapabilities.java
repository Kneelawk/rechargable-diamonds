package com.kneelawk.rechargeablediamonds;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.nbt.IntTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

@Mod.EventBusSubscriber
public class DiamondCapabilities {
    private static final ResourceLocation CAPABILITY_ID = Constants.id("diamond_energy_capability");
    private static final String CAPABILITY_KEY = CAPABILITY_ID.toString();

    @SubscribeEvent
    public static void onAttatchingCapabilities(final AttachCapabilitiesEvent<ItemStack> event) {
        ItemStack stack = event.getObject();
        if (stack.getItem() != Items.DIAMOND) return;

        StackEnergyStorage storage = new StackEnergyStorage(stack, CAPABILITY_KEY, Constants.DIAMOND_CAPACITY, Constants.MAX_INSERT, Constants.MAX_EXTRACT);
        LazyOptional<IEnergyStorage> optionalStorage = LazyOptional.of(() -> storage);
        Capability<IEnergyStorage> capability = ForgeCapabilities.ENERGY;

        ICapabilityProvider provider = new ICapabilityProvider() {
            @Override
            public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
                if (cap == capability) return optionalStorage.cast();
                return LazyOptional.empty();
            }
        };

        event.addCapability(CAPABILITY_ID, provider);
    }

    @SubscribeEvent
    public static void onRenderDiamondTooltip(final ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.getItem() != Items.DIAMOND) return;

        // not sure how to cache this :/
        LazyOptional<IEnergyStorage> storage = stack.getCapability(ForgeCapabilities.ENERGY);

        storage.ifPresent(energy -> event.getToolTip()
            .add(Constants.tooltip("diamond_energy", energy.getEnergyStored())));
    }
}
