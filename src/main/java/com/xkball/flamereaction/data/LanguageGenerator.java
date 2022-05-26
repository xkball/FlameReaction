package com.xkball.flamereaction.data;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.itemlike.item.ItemList;
import com.xkball.flamereaction.itemgroup.Groups;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.common.Mod;

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
        this.add(Groups.MATERIAL_GROUP.getDisplayName().getString(),
                switch (this.locale){
                    default -> throw new IllegalStateException("Unexpected value: " + this.locale);
                    case EN_US -> "materials";
                    case ZH_CN -> "材料";
        });
        
//        for(Block block : BlockList.block_instance.values()){
//            this.add(block,
//                    switch (this.locale){
//                default -> throw new IllegalStateException("Unexpected value: " + this.locale);
//                case EN_US -> Objects.requireNonNull(block.getRegistryName()).getPath();
//                case ZH_CN -> Objects.requireNonNull(block.getRegistryName()).getPath()+"need translate";
//            }
//            );
//        }
    }
}
