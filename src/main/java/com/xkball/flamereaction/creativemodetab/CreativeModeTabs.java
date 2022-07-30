package com.xkball.flamereaction.creativemodetab;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.util.ItemList;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class CreativeModeTabs {
    
    public static final CreativeModeTab MATERIAL_GROUP = new MaterialGroup("material_group");
    public static final CreativeModeTab FLAME_REACTION_GROUP = new CreativeModeTab("flame_reaction_group") {
        @Override
        public @Nonnull ItemStack makeIcon() {
            return new ItemStack(FlameReaction.ICON);
        }
    };
    
    static class MaterialGroup extends CreativeModeTab {
        public MaterialGroup(String label) {
            super(label);
        }
    
        @Override
        public void fillItemList(@NotNull NonNullList<ItemStack> itemStacks) {
            for(Item item : ItemList.item_instance.values()){
                if (item.getItemCategory() == this){
                    itemStacks.add(new ItemStack(item));
                }
            }
        }
    
        @Override
        public @Nonnull ItemStack makeIcon() {
            return new ItemStack(ItemList.item_instance.get("molybdenum_ingot"));
        }
    }
}