package com.xkball.flamereaction.itemlike.item.commonitem;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.itemgroup.Groups;
import com.xkball.flamereaction.itemlike.item.FRCItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

public class HammerItem extends Item implements FRCItem {
    
    public static final String NAME = "hammer";
    
    public HammerItem() {
        super(new Properties()
                .durability(200)
                .fireResistant()
                .tab(Groups.FLAME_REACTION_GROUP));
        this.setRegistryName(new ResourceLocation(FlameReaction.MOD_ID,NAME));
        add();
    }
    
}
