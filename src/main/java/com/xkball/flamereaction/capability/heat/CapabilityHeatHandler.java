package com.xkball.flamereaction.capability.heat;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CapabilityHeatHandler  {
    
    public static final Capability<IHeatHandler> HEAT_HANDLER_CAPABILITY =
            CapabilityManager.get(new CapabilityToken<>() {
            });
    
    @SubscribeEvent
    public static void register(RegisterCapabilitiesEvent event) {
        event.register(IHeatHandler.class);
    }
    
}
