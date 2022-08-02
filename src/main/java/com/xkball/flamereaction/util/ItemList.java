package com.xkball.flamereaction.util;

import com.xkball.flamereaction.itemlike.item.materialitem.MaterialItem;
import com.xkball.flamereaction.part.material.IMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class ItemList {
    public static final Map<String, Item> item_instance = new LinkedHashMap<>();
    
    public static Item addItem(Item item){
        item_instance.put(Objects.requireNonNull(item.getRegistryName()).getPath(),item);
        return item;
    }
    
    public static Item getMaterialItem(IMaterial material,MaterialType type){
        var item = Items.AIR;
        if(type !=MaterialType.CHEMICAL){
            item = item_instance.get(material.getName()+"_"+type.getName());
        }
        else {
            item = item_instance.get(material.getName());
        }
        return item;
    }
}
