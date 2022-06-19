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
    
    private static TagKey<Item> tag(String name)
    {
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
    }
    
    public static void addMaterialTag(MaterialType materialType,IMaterial material){
        add(tag(materialType.getName()+"/"+material.getName()));
    }
    
    public static TagKey<Item> getMaterialTag(MaterialType materialType, IMaterial material){
        return itemTagsList.get(materialType.getName()+"/"+material.getName());
    }
    
    public static void add(TagKey<Item> tagKey){
        itemTagsList.put(tagKey.location().getPath(),tagKey);
    }

}
