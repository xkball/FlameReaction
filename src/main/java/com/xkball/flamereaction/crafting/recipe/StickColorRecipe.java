package com.xkball.flamereaction.crafting.recipe;

import com.xkball.flamereaction.eventhandler.register.RecipeRegister;
import com.xkball.flamereaction.itemlike.item.materialitem.ColoredFlameItem;
import com.xkball.flamereaction.util.ItemList;
import net.minecraft.nbt.IntTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class StickColorRecipe extends CustomRecipe {
    public static final Ingredient iStick = Ingredient.of(ItemList.item_instance.get("iron_stick"));
    public static final Ingredient pStick = Ingredient.of(ItemList.item_instance.get("platinum_stick"));
    
    public StickColorRecipe(ResourceLocation pId) {
        super(pId);
    }
    
    @Override
    public boolean matches(@NotNull CraftingContainer pContainer, @NotNull Level pLevel) {
        var c = pContainer.getContainerSize();
        if(c > 2) return false;
        
        boolean b1 = false;
        boolean b2 = false;
        
        for(int i=0;i<c;i++){
            var item = pContainer.getItem(i);
            if(b1) return false;
            
            if(iStick.test(item) || pStick.test(item)){
                b1 = true;
            }
            else {
                  b2 = !b2 && item.getItem() instanceof ColoredFlameItem;
            }
        }
        return b1 && b2;
    }
    
    @Override
    public @NotNull ItemStack assemble(@NotNull CraftingContainer pContainer) {
        var c = pContainer.getContainerSize();
    
        boolean b1 = false;
        boolean b2 = false;
        int color = 0;
        ItemStack result = ItemStack.EMPTY.copy();
    
        for(int i=0;i<c;i++){
            var item = pContainer.getItem(i);
            if(item.getItem() instanceof ColoredFlameItem cfi){
                color = cfi.getFlamedColor().getRGB();
            }
            else if(iStick.test(item)){
                result = iStick.getItems()[0];
            }
            else if (pStick.test(item)){
                result = pStick.getItems()[0];
            }
        }
        result.addTagElement("color", IntTag.valueOf(color));
        return result;
    }
    
    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }
    
    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return RecipeRegister.STICK_COLOR_SERIALIZER.get();
    }
}
