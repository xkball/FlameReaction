package com.xkball.flamereaction.util.translateutil;

import com.xkball.flamereaction.itemlike.block.commonblocks.ExhibitBlock;
import com.xkball.flamereaction.itemlike.block.materialblock.MaterialBlocks;
import com.xkball.flamereaction.itemlike.item.commonitem.tool.ExhibitBlockKey;
import com.xkball.flamereaction.itemlike.item.materialitem.ColoredFlameItem;
import com.xkball.flamereaction.part.material.BasicMaterial;
import com.xkball.flamereaction.part.material.FlammableChemicalMaterials;
import com.xkball.flamereaction.part.material.IMaterial;
import com.xkball.flamereaction.util.MaterialType;
import com.xkball.flamereaction.util.PeriodicTableOfElements;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;

import java.util.HashMap;
import java.util.Map;

public class TranslateUtil {
    public static final HashMap<String,String> CHINESE_TRANSLATE_MAP;
    public static final HashMap<String,String> ENGLISH_TRANSLATE_MAP;
    public static final String PREFIX = "flamereaction.translatable.";
    
    static {
        CHINESE_TRANSLATE_MAP = new HashMap<>();
        ENGLISH_TRANSLATE_MAP = new HashMap<>();
        CHINESE_TRANSLATE_MAP.putAll(Map.of(
                ExhibitBlockKey.TOOLTIP1.getKey(),"右键以锁定一个展览方块",
                ExhibitBlockKey.TOOLTIP2.getKey(),"shift+右键以改变展览方块的展示状态",
                ExhibitBlock.EXHIBIT_SUCCEED.getKey(),"设置展示物品",
                ExhibitBlock.EXHIBIT_CLEAR.getKey(),"清除展示物品",
                ExhibitBlock.EXHIBIT_LOCKED.getKey(),"锁定展览方块",
                ExhibitBlock.EXHIBIT_UNLOCKED.getKey(),"解锁展览方块",
                ExhibitBlock.EXHIBIT_SET_INFO_SUCCEED.getKey(),"设置介绍信息",
                ExhibitBlock.EXHIBIT_SET_INFO_CLEAR.getKey(),"清除介绍信息"));
        ENGLISH_TRANSLATE_MAP.putAll(Map.of(
                ExhibitBlockKey.TOOLTIP1.getKey(),"use in right hand to lock a exhibit_block",
                ExhibitBlockKey.TOOLTIP2.getKey(),"press shift and use to change display state of a exhibit_lock",
                ExhibitBlock.EXHIBIT_SUCCEED.getKey(),"exhibit_succeed",
                ExhibitBlock.EXHIBIT_CLEAR.getKey(),"exhibit_clear",
                ExhibitBlock.EXHIBIT_LOCKED.getKey(),"exhibit_block_locked",
                ExhibitBlock.EXHIBIT_UNLOCKED.getKey(),"exhibit_block_unlocked",
                ExhibitBlock.EXHIBIT_SET_INFO_SUCCEED.getKey(),"exhibit_set_info_succeed",
                ExhibitBlock.EXHIBIT_SET_INFO_CLEAR.getKey(),"exhibit_info_clear"));
    
        for(FlammableChemicalMaterials materials : FlammableChemicalMaterials.values()){
            create(MaterialType.CHEMICAL.getName()+"_"+materials.getSymbol(),
                    MaterialType.CHEMICAL.getChinese()+": "+materials.getSymbol(),
                    MaterialType.CHEMICAL.getName()+": "+materials.getSymbol());
        }
    
        for(IMaterial material : BasicMaterial.commonMaterials){
            if(material != PeriodicTableOfElements.Cu && material != PeriodicTableOfElements.Fe){
                create("material_"+material.getSymbol(),
                        "材料: "+material.getSymbol(),
                        "material: "+material.getSymbol());
            }
        }
        
    }
    
    public static TranslatableComponent create(String key,String chinese,String english){
        var translatableComponent = new TranslatableComponent(PREFIX+key);
        CHINESE_TRANSLATE_MAP.put(translatableComponent.getKey(),chinese);
        ENGLISH_TRANSLATE_MAP.put(translatableComponent.getKey(),english);
        return translatableComponent;
    }
    
}
