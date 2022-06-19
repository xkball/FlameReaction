package com.xkball.flamereaction.itemlike.item;

import com.xkball.flamereaction.util.ItemList;
import net.minecraft.world.item.Item;

//所有类在设置完成名称后再加入物品列表
public interface FRCItem  {
    default void add(){
        ItemList.addItem((Item) this);
    }
}
