package com.xkball.flamereaction.itemgroup;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class Groups {
    
    public static final CreativeModeTab MATERIAL_GROUP = new MaterialGroup("material_group");
    
    static class MaterialGroup extends CreativeModeTab {
        public MaterialGroup(String label) {
            super(label);
        }
    
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.BAMBOO);
        }
    }
}
