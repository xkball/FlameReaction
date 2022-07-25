package com.xkball.flamereaction.itemlike.item;

import com.xkball.flamereaction.util.ItemList;
import com.xkball.flamereaction.util.translateutil.ChineseTranslatable;
import net.minecraft.world.item.Item;

//所有类在设置完成名称后再加入物品列表
public interface FRCItem extends ChineseTranslatable {
    default void add(){
        ItemList.addItem((Item) this);
    }
    
    default Item get(String name){
        return ItemList.item_instance.get(name);
    }
}
