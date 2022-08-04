package com.xkball.flamereaction.itemlike.item.itemtags;

import com.xkball.flamereaction.part.material.BasicMaterial;
import com.xkball.flamereaction.part.material.IMaterial;
import com.xkball.flamereaction.util.MaterialType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.Arrays;
import java.util.HashMap;

public class ItemTags {
    
    public static final HashMap<String,TagKey<Item>> itemTagsList = new HashMap<>();
    
    public static final TagKey<Item> HAMMER = tag("tool/hammer");
    public static final TagKey<Item> WRENCH = tag("tool/wrench");
    public static final TagKey<Item> PLIERS = tag("tool/pliers");
    public static final TagKey<Item> FLAME_DYES = tag("flame_dyes");
    public static final TagKey<Item> FLAMES =  net.minecraft.tags.ItemTags.create(new ResourceLocation("nocaet","flames"));
    
    private static TagKey<Item> tag(String name) {
        return net.minecraft.tags.ItemTags.create(new ResourceLocation("forge", name));
    }
    
    public static void init(){
        if(itemTagsList.size() == 0){
            var typeList = MaterialType.values();
            var list = Arrays.stream(typeList).filter(materialType -> materialType != MaterialType.UNKNOW).toList();
            for(IMaterial material : BasicMaterial.commonMaterials){
                for(MaterialType materialType : list){
                    addMaterialTag(materialType,material);
                }
            }
        }
        add(HAMMER);
        add(PLIERS);
        add(WRENCH);
        add(FLAMES);
        add(FLAME_DYES);
    }
    
    public static void addMaterialTag(MaterialType materialType,IMaterial material){
        add(tag(materialType.getName()+"/"+material.getName()));
    }
    
    public static TagKey<Item> getMaterialTag(MaterialType materialType, IMaterial material){
        return itemTagsList.get(materialType.getName()+"/"+material.getName());
    }
    
    public static TagKey<Item> add(TagKey<Item> tagKey){
        itemTagsList.put(tagKey.location().getPath(),tagKey);
        return tagKey;
    }

}
