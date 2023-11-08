package com.kneelawk.rechargeablediamonds.client;

import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Items;

import com.kneelawk.rechargeablediamonds.Constants;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RechargeableDiamondsModClient {
    @SubscribeEvent
    public static void onClientInit(final FMLClientSetupEvent event) {
        // TODO: remove this and replace with custom renderer via RegisterItemDecorationsEvent
        event.enqueueWork(() -> {
            Constants.LOG.info("Registering diamond damage properties...");
            ItemProperties.register(Items.DIAMOND, new ResourceLocation("damaged"), (stack, level, entity, entityId) -> {
                LazyOptional<IEnergyStorage> storage = stack.getCapability(ForgeCapabilities.ENERGY);
                if (storage.isPresent()) {
                    IEnergyStorage energy = storage.orElseThrow(AssertionError::new);
                    return energy.getEnergyStored() > 0 ? 1.0f : 0.0f;
                }
                return 0.0f;
            });

            ItemProperties.register(Items.DIAMOND, new ResourceLocation("damage"), (stack, level, entity, entityId) -> {
                LazyOptional<IEnergyStorage> storage = stack.getCapability(ForgeCapabilities.ENERGY);
                if (storage.isPresent()) {
                    IEnergyStorage energy = storage.orElseThrow(AssertionError::new);
                    return 1.0f - Mth.clamp((float) energy.getEnergyStored() / (float) Constants.DIAMOND_CAPACITY, 0.0f, 1.0f);
                }
                return 0.0f;
            });
        });
    }
}
