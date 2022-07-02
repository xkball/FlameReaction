package com.xkball.flamereaction.data;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGen {
    @SubscribeEvent
    public static void dataGen(GatherDataEvent event){
        var gen = event.getGenerator();
        var efh = event.getExistingFileHelper();
        event.getGenerator().addProvider(new LanguageGenerator(gen,LanguageGenerator.EN_US));
        event.getGenerator().addProvider(new LanguageGenerator(gen,LanguageGenerator.ZH_CN));
        event.getGenerator().addProvider(new ItemModelGenerator(gen,efh));
        event.getGenerator().addProvider(new BlockModelGenerator(gen,efh));
        var btg = new BlockTagGenerator(gen,efh);
        event.getGenerator().addProvider(btg);
        event.getGenerator().addProvider(new BlockLootTableGenerator(gen) );
        event.getGenerator().addProvider(new ItemTagGenerator(gen,btg,efh));
        event.getGenerator().addProvider(new RecipeGenerator(gen));
        event.getGenerator().addProvider(new FluidTagGenerator(gen,efh));
    }
}
