package com.xkball.flamereaction.itemlike.item;

import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ItemList {
    public static Map<String, Item> item_instance = new HashMap<>();
    
    public static Item addItem(Item item){
        item_instance.put(Objects.requireNonNull(item.getRegistryName()).getPath(),item);
        return item;
    }
}
