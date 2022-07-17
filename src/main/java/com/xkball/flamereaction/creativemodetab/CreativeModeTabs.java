package com.xkball.flamereaction.creativemodetab;

import com.xkball.flamereaction.util.ItemList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import javax.annotation.Nonnull;

public class CreativeModeTabs {
    
    public static final CreativeModeTab MATERIAL_GROUP = new MaterialGroup("material_group");
    public static final CreativeModeTab FLAME_REACTION_GROUP = new CreativeModeTab("flame_reaction_group") {
        @Override
        public @Nonnull ItemStack makeIcon() {
            return new ItemStack(Items.ACACIA_FENCE_GATE);
        }
    };
    
    static class MaterialGroup extends CreativeModeTab {
        public MaterialGroup(String label) {
            super(label);
        }
    
        @Override
        public @Nonnull ItemStack makeIcon() {
            return new ItemStack(ItemList.item_instance.get("molybdenum_ingot"));
        }
    }
}