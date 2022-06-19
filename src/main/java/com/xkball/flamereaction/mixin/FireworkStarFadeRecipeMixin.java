package com.xkball.flamereaction.mixin;

import com.google.common.collect.Lists;
import com.xkball.flamereaction.itemlike.item.commonitem.FlameDyeItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.FireworkStarFadeRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.List;

@Mixin(FireworkStarFadeRecipe.class)
public abstract class FireworkStarFadeRecipeMixin extends CustomRecipe {
    
    private static final Ingredient STAR_INGREDIENT = Ingredient.of(Items.FIREWORK_STAR);
    
    protected FireworkStarFadeRecipeMixin(ResourceLocation p_43833_) {
        super(p_43833_);
    }
    
    
    /**
     * @author xkball
     * @reason 使得配方只能匹配焰色颜料
     */
    @Overwrite
    public boolean matches(CraftingContainer craftingContainer, @NotNull Level level) {
        boolean flag = false;
        boolean flag1 = false;
    
        for(int i = 0; i < craftingContainer.getContainerSize(); ++i) {
            ItemStack itemstack = craftingContainer.getItem(i);
            if (!itemstack.isEmpty()) {
                //!
                if (itemstack.getItem() instanceof FlameDyeItem) {
                    flag = true;
                } else {
                    if (!STAR_INGREDIENT.test(itemstack)) {
                        return false;
                    }
                
                    if (flag1) {
                        return false;
                    }
                
                    flag1 = true;
                }
            }
        }
    
        return flag1 && flag;
    }
    
    /**
     * @author xkball
     * @reason 改变配方
     */
    @Overwrite
    public @NotNull ItemStack assemble(CraftingContainer craftingContainer) {
        List<Integer> list = Lists.newArrayList();
        ItemStack itemstack = null;
    
        for(int i = 0; i < craftingContainer.getContainerSize(); ++i) {
            ItemStack itemstack1 = craftingContainer.getItem(i);
            Item item = itemstack1.getItem();
            if (item instanceof FlameDyeItem) {
                list.add(((DyeItem)item).getDyeColor().getFireworkColor());
            } else if (STAR_INGREDIENT.test(itemstack1)) {
                itemstack = itemstack1.copy();
                itemstack.setCount(1);
            }
        }
    
        if (itemstack != null && !list.isEmpty()) {
            itemstack.getOrCreateTagElement("Explosion").putIntArray("FadeColors", list);
            return itemstack;
        } else {
            return ItemStack.EMPTY;
        }
    }
}
