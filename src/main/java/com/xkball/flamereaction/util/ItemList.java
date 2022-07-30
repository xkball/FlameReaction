package com.xkball.flamereaction.util;

import net.minecraft.world.item.Item;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class ItemList {
    public static final Map<String, Item> item_instance = new LinkedHashMap<>();
    
    public static Item addItem(Item item){
        item_instance.put(Objects.requireNonNull(item.getRegistryName()).getPath(),item);
        return item;
    }
}
