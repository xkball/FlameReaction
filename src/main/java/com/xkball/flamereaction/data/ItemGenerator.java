package com.xkball.flamereaction.data;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.material.BasicMaterial;
import com.xkball.flamereaction.material.IMaterial;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemGenerator extends LanguageProvider {
    private final String locale;
    public static final String EN_US = "en_us";
    public static final String ZH_CN = "zh_cn";
    
    public ItemGenerator(DataGenerator gen, String locale) {
        super(gen, FlameReaction.MOD_ID, locale);
        this.locale = locale;
    }
    
    @SubscribeEvent
    public static void dataGen(GatherDataEvent event){
        event.getGenerator().addProvider(new ItemGenerator(event.getGenerator(),EN_US));
        event.getGenerator().addProvider(new ItemGenerator(event.getGenerator(),ZH_CN));
    }
    
    @Override
    protected void addTranslations() {
        for(IMaterial material : BasicMaterial.materials){
            this.add(FlameReaction.getItem(material.getName()).get(),
                    switch (this.locale){
                        default -> throw new IllegalStateException("Unexpected value: " + this.locale);
                        case EN_US -> material.getName();
                        case ZH_CN -> material.getName()+"need translate";
                    }
            );
        }
        
    }
}
