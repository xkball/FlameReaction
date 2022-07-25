package com.xkball.flamereaction.capability.heat;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CapabilityHeatHandler {
    
    public static final Capability<IHeatHandler> HEAT_HANDLER_CAPABILITY = CapabilityManager.get(new CapabilityToken<IHeatHandler>() {});
    
    @SubscribeEvent
    public static void register(RegisterCapabilitiesEvent event) {
        event.register(IItemHandler.class);
    }
}
