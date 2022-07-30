package com.xkball.flamereaction.capability.heat;

import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


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
