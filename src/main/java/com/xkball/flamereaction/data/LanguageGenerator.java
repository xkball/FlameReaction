package com.xkball.flamereaction.data;

import com.mojang.logging.LogUtils;
import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.itemlike.item.materialitem.MaterialItem;
import com.xkball.flamereaction.util.BlockList;
import com.xkball.flamereaction.util.ItemList;
import com.xkball.flamereaction.creativemodetab.CreativeModeTabs;
import com.xkball.flamereaction.util.translateutil.ChineseTranslatable;
import com.xkball.flamereaction.util.translateutil.MaterialChineseTranslate;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static com.xkball.flamereaction.util.translateutil.TranslateUtil.CHINESE_TRANSLATE_MAP;
import static com.xkball.flamereaction.util.translateutil.TranslateUtil.ENGLISH_TRANSLATE_MAP;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class LanguageGenerator extends LanguageProvider {
    
    private final List<String> keys = new LinkedList<>();
    private static final Logger LOGGER = LogUtils.getLogger();
    
    private final String locale;
    public static final String EN_US = "en_us";
    public static final String ZH_CN = "zh_cn";
    
    public LanguageGenerator(DataGenerator gen, String locale) {
        super(gen, FlameReaction.MOD_ID, locale);
        this.locale = locale;
    }
    
    @Override
    public void add(@NotNull String key, @NotNull String value) {
        if(keys.contains(key)){
            LOGGER.warn("焰色反应:语言文件生成:重复的键 这应该被处理(但是我懒");
            LOGGER.warn("键: "+key);
            LOGGER.warn("值: "+value);
        }
        else {
            keys.add(key);
            super.add(key, value);
        }
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
            
            else if(item instanceof ChineseTranslatable c){
                this.add(item,c.getChineseTranslate());
            }
            
            else {
                if(!(item instanceof BlockItem)) {
                    this.add(item,
                            switch (this.locale) {
                                default -> throw new IllegalStateException("Unexpected value: " + this.locale);
                                case EN_US -> Objects.requireNonNull(item.getRegistryName()).getPath();
                                case ZH_CN -> Objects.requireNonNull(item.getRegistryName()).getPath() + " need translate";
                            }
                    );
                }
            }
            
        }
        for(Block block: BlockList.block_instance.values()){
            if(block instanceof ChineseTranslatable c){
                this.add(block,c.getChineseTranslate());
            }
        }
        this.add("item.nocaet.flame_fire",switch (this.locale){
            default -> throw new IllegalStateException("Unexpected value: " + this.locale);
            case EN_US -> "flame_fire";
            case ZH_CN -> "彩色的焰火";
        });
        this.add("item.nocaet.flame_fire.tooltip",switch (this.locale){
            default -> throw new IllegalStateException("Unexpected value: " + this.locale);
            case EN_US -> "what reaction brought us the flamboyant fireworks!?";
            case ZH_CN -> "是什么反应给我们带来了绚丽的焰火!?";
        });
        addItemGroups();
        addTranslatableComponent();
    }
    
    public void addTranslatableComponent(){
        for(String key:CHINESE_TRANSLATE_MAP.keySet()){
            this.add(key,
            Objects.equals(this.locale, EN_US) ?
                   ENGLISH_TRANSLATE_MAP.get(key) : CHINESE_TRANSLATE_MAP.get(key));
        }
    }
    
    public void addItemGroups(){
        this.add(CreativeModeTabs.MATERIAL_GROUP.getDisplayName().getString(),
                switch (this.locale){
                    default -> throw new IllegalStateException("Unexpected value: " + this.locale);
                    case EN_US -> "Flame_reaction_materials";
                    case ZH_CN -> "焰色反应——材料";
                });
        this.add(CreativeModeTabs.FLAME_REACTION_GROUP.getDisplayName().getString(),
                switch (this.locale){
                    default -> throw new IllegalStateException("Unexpected value: " + this.locale);
                    case EN_US -> "Flame_reaction";
                    case ZH_CN -> "焰色反应——主体";
                });
    }
    
    
}
