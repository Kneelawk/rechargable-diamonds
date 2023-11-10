package com.kneelawk.rechargeablediamonds.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import team.reborn.energy.api.EnergyStorage;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import com.kneelawk.rechargeablediamonds.DiamondComponentInitializer;
import com.kneelawk.rechargeablediamonds.DiamondEnergyComponent;
import com.kneelawk.rechargeablediamonds.client.CommonClient;

@Mixin(GuiGraphics.class)
public class MixinGuiGraphics {
    @Inject(
        method = "renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V",
        at = @At("RETURN"))
    public void onRenderItemDecorations(Font font, ItemStack itemStack, int i, int j, String string, CallbackInfo ci) {
        if (!itemStack.is(Items.DIAMOND)) return;
        DiamondEnergyComponent component = DiamondComponentInitializer.DIAMOND_ENERGY_KEY.getNullable(itemStack);
        if (component == null) return;
        EnergyStorage energy = component.getStorage();
        CommonClient.renderDiamondBar((GuiGraphics) (Object) this, i, j, (int) energy.getAmount(),
            (int) energy.getCapacity());
    }
}
