package com.xkball.flamereaction.data;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGen {
    @SubscribeEvent
    public static void dataGen(GatherDataEvent event){
        var gen = event.getGenerator();
        event.getGenerator().addProvider(new LanguageGenerator(gen,LanguageGenerator.EN_US));
        event.getGenerator().addProvider(new LanguageGenerator(gen,LanguageGenerator.ZH_CN));
        event.getGenerator().addProvider(new ItemModelGenerator(gen,event.getExistingFileHelper()));
        event.getGenerator().addProvider(new BlockModelGenerator(gen,event.getExistingFileHelper()));
    }
}
