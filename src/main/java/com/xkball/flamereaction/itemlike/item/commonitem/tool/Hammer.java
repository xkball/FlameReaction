package com.xkball.flamereaction.itemlike.item.commonitem.tool;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.creativemodetab.CreativeModeTabs;
import com.xkball.flamereaction.itemlike.item.FRCItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class Hammer extends Item implements FRCItem {
    
    public static final String NAME = "hammer";
    
    public Hammer() {
        super(new Properties()
                .durability(200)
                .fireResistant()
                .tab(CreativeModeTabs.FLAME_REACTION_GROUP));
        this.setRegistryName(new ResourceLocation(FlameReaction.MOD_ID,NAME));
        add();
    }
    
}
