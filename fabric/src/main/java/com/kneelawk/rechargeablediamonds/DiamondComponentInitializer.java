package com.kneelawk.rechargeablediamonds;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.item.ItemComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.item.ItemComponentInitializer;

import net.minecraft.world.item.Items;

public class DiamondComponentInitializer implements ItemComponentInitializer {
    public static final ComponentKey<DiamondEnergyComponent> DIAMOND_ENERGY_KEY =
        ComponentRegistry.getOrCreate(Constants.id("diamond_energy_component"), DiamondEnergyComponent.class);

    @Override
    public void registerItemComponentFactories(ItemComponentFactoryRegistry registry) {
        registry.register(Items.DIAMOND, DIAMOND_ENERGY_KEY, DiamondEnergyComponent::new);
    }
}
