package com.kneelawk.rechargeablediamonds.client;

import net.minecraftforge.client.IItemDecorator;
import net.minecraftforge.client.event.RegisterItemDecorationsEvent;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import com.kneelawk.rechargeablediamonds.Constants;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DiamondItemDecorator implements IItemDecorator {
    private static final DiamondItemDecorator INSTANCE = new DiamondItemDecorator();

    @SubscribeEvent
    public static void onRegisterItemDecorations(final RegisterItemDecorationsEvent event) {
        Constants.LOG.info("Registering item decorator...");
        event.register(Items.DIAMOND, INSTANCE);
    }

    @Override
    public boolean render(GuiGraphics guiGraphics, Font font, ItemStack stack, int xOffset, int yOffset) {
        LazyOptional<IEnergyStorage> storage = stack.getCapability(ForgeCapabilities.ENERGY);
        storage.ifPresent(
            energy -> CommonClient.renderDiamondBar(guiGraphics, xOffset, yOffset, energy.getEnergyStored()));
        return false;
    }
}
