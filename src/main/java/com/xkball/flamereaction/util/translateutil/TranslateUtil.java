package com.xkball.flamereaction.util.translateutil;

import com.xkball.flamereaction.itemlike.block.commonblocks.ExhibitBlock;
import com.xkball.flamereaction.itemlike.item.commonitem.ExhibitBlockKey;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.HashMap;
import java.util.Map;

public class TranslateUtil {
    public static final HashMap<TranslatableComponent,String> CHINESE_TRANSLATE_MAP;
    public static final String PREFIX = "flamereaction.tooltip.";
    
    static {
        CHINESE_TRANSLATE_MAP = new HashMap<>();
        CHINESE_TRANSLATE_MAP.putAll(Map.of(
                ExhibitBlockKey.TOOLTIP1,"右键以锁定一个展览方块",
                ExhibitBlockKey.TOOLTIP2,"shift+右键以改变展览方块的展示状态",
                ExhibitBlock.EXHIBIT_SUCCEED,"设置展示物品",
                ExhibitBlock.EXHIBIT_CLEAR,"清除展示物品",
                ExhibitBlock.EXHIBIT_LOCKED,"锁定展览方块",
                ExhibitBlock.EXHIBIT_UNLOCKED,"解锁展览方块",
                ExhibitBlock.EXHIBIT_SET_INFO_SUCCEED,"设置介绍信息",
                ExhibitBlock.EXHIBIT_SET_INFO_CLEAR,"清除介绍信息"));
        
        
    }
    
    public static TranslatableComponent create(String key,String chinese){
        var translatableComponent = new TranslatableComponent(PREFIX+key);
        CHINESE_TRANSLATE_MAP.put(translatableComponent,chinese);
        return translatableComponent;
    }
    
    public static String keyToEN(String key){
        return key.split("\\.")[2];
    }
    
}
