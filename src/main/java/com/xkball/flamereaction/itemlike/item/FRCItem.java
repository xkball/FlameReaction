package com.xkball.flamereaction.itemlike.item;

import net.minecraft.world.item.Item;

//所有类在设置完成名称后再加入物品列表
public class FRCItem extends Item {
    public FRCItem(Properties p_41383_) {
        super(p_41383_);
        
    }
    public void add(){
        ItemList.addItem(this);
    }
}
