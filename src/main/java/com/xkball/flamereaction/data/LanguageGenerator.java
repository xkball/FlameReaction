package com.xkball.flamereaction.data;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.item.ItemList;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

import java.util.Objects;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class LanguageGenerator extends LanguageProvider {
    private final String locale;
    public static final String EN_US = "en_us";
    public static final String ZH_CN = "zh_cn";
    
    public LanguageGenerator(DataGenerator gen, String locale) {
        super(gen, FlameReaction.MOD_ID, locale);
        this.locale = locale;
    }
    
    @SubscribeEvent
    public static void dataGen(GatherDataEvent event){
        event.getGenerator().addProvider(new LanguageGenerator(event.getGenerator(),EN_US));
        event.getGenerator().addProvider(new LanguageGenerator(event.getGenerator(),ZH_CN));
    }
    
    @Override
    protected void addTranslations() {
        for(Item item : ItemList.item_instance.values()){
            this.add(item,
                    switch (this.locale){
                        default -> throw new IllegalStateException("Unexpected value: " + this.locale);
                        case EN_US -> Objects.requireNonNull(item.getRegistryName()).getPath();
                        case ZH_CN -> Objects.requireNonNull(item.getRegistryName()).getPath()+"need translate";
                    }
            );
        }
    }
}
