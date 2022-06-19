package com.xkball.flamereaction.data;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.itemlike.item.materialitem.MaterialItem;
import com.xkball.flamereaction.util.ItemList;
import com.xkball.flamereaction.itemgroup.Groups;
import com.xkball.flamereaction.util.translateutil.MaterialChineseTranslate;
import com.xkball.flamereaction.util.translateutil.TranslateUtil;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

import static com.xkball.flamereaction.util.translateutil.TranslateUtil.CHINESE_TRANSLATE_MAP;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class LanguageGenerator extends LanguageProvider {
    
    
    
    private final String locale;
    public static final String EN_US = "en_us";
    public static final String ZH_CN = "zh_cn";
    
    public LanguageGenerator(DataGenerator gen, String locale) {
        super(gen, FlameReaction.MOD_ID, locale);
        this.locale = locale;
    }
    
    @Override
    protected void addTranslations() {
        for(Item item : ItemList.item_instance.values()){
            if(item instanceof MaterialItem mi){
                this.add(item,
                        Objects.equals(this.locale, EN_US) ?
                                Objects.requireNonNull(item.getRegistryName()).getPath() :
                                MaterialChineseTranslate.CHINESE_MAP.get((mi.getMaterial()))+mi.getMaterialKind().getChinese()
                );
            }
            else {
                this.add(item,
                        switch (this.locale){
                            default -> throw new IllegalStateException("Unexpected value: " + this.locale);
                            case EN_US -> Objects.requireNonNull(item.getRegistryName()).getPath();
                            case ZH_CN -> Objects.requireNonNull(item.getRegistryName()).getPath()+" need translate";
                        }
                );
            }
            
        }
        addItemGroups();
        addTranslatableComponent();
    }
    
    public void addTranslatableComponent(){
        for(TranslatableComponent translatableComponent:CHINESE_TRANSLATE_MAP.keySet()){
            this.add(translatableComponent.getKey(),
            Objects.equals(this.locale, EN_US) ?
                    TranslateUtil.keyToEN(translatableComponent.getKey()) : CHINESE_TRANSLATE_MAP.get(translatableComponent));
        }
    }
    
    public void addItemGroups(){
        this.add(Groups.MATERIAL_GROUP.getDisplayName().getString(),
                switch (this.locale){
                    default -> throw new IllegalStateException("Unexpected value: " + this.locale);
                    case EN_US -> "Flame_reaction_materials";
                    case ZH_CN -> "焰色反应——材料";
                });
        this.add(Groups.FLAME_REACTION_GROUP.getDisplayName().getString(),
                switch (this.locale){
                    default -> throw new IllegalStateException("Unexpected value: " + this.locale);
                    case EN_US -> "Flame_reaction";
                    case ZH_CN -> "焰色反应——主体";
                });
    }
    
    
}
