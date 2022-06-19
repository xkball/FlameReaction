package com.xkball.flamereaction.mixin;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xkball.flamereaction.itemlike.item.commonitem.FlameDyeItem;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.FireworkStarRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.List;
import java.util.Map;


@Mixin(FireworkStarRecipe.class)
public abstract class FireworkStarRecipeMixin extends CustomRecipe {
    
    private static final Ingredient SHAPE_INGREDIENT = Ingredient.of(Items.FIRE_CHARGE, Items.FEATHER, Items.GOLD_NUGGET, Items.SKELETON_SKULL, Items.WITHER_SKELETON_SKULL, Items.CREEPER_HEAD, Items.PLAYER_HEAD, Items.DRAGON_HEAD, Items.ZOMBIE_HEAD);
    private static final Ingredient TRAIL_INGREDIENT = Ingredient.of(Items.DIAMOND);
    private static final Ingredient FLICKER_INGREDIENT = Ingredient.of(Items.GLOWSTONE_DUST);
    private static final Map<Item, FireworkRocketItem.Shape> SHAPE_BY_ITEM = Util.make(Maps.newHashMap(), (p_43898_) -> {
        p_43898_.put(Items.FIRE_CHARGE, FireworkRocketItem.Shape.LARGE_BALL);
        p_43898_.put(Items.FEATHER, FireworkRocketItem.Shape.BURST);
        p_43898_.put(Items.GOLD_NUGGET, FireworkRocketItem.Shape.STAR);
        p_43898_.put(Items.SKELETON_SKULL, FireworkRocketItem.Shape.CREEPER);
        p_43898_.put(Items.WITHER_SKELETON_SKULL, FireworkRocketItem.Shape.CREEPER);
        p_43898_.put(Items.CREEPER_HEAD, FireworkRocketItem.Shape.CREEPER);
        p_43898_.put(Items.PLAYER_HEAD, FireworkRocketItem.Shape.CREEPER);
        p_43898_.put(Items.DRAGON_HEAD, FireworkRocketItem.Shape.CREEPER);
        p_43898_.put(Items.ZOMBIE_HEAD, FireworkRocketItem.Shape.CREEPER);
    });
    private static final Ingredient GUNPOWDER_INGREDIENT = Ingredient.of(Items.GUNPOWDER);
    
    
    
    public FireworkStarRecipeMixin(ResourceLocation p_43833_) {
        super(p_43833_);
    }
    
    /**
     * @author xkball
     * @reason 使得配方只能匹配焰色颜料
     */
    @Overwrite
    public boolean matches(@NotNull CraftingContainer craftingContainer, @NotNull Level p_44003_) {
        boolean flag = false;
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        boolean flag4 = false;
    
        for(int i = 0; i < craftingContainer.getContainerSize(); ++i) {
            ItemStack itemstack = craftingContainer.getItem(i);
            if (!itemstack.isEmpty()) {
                if (SHAPE_INGREDIENT.test(itemstack)) {
                    if (flag2) {
                        return false;
                    }
                
                    flag2 = true;
                } else if (FLICKER_INGREDIENT.test(itemstack)) {
                    if (flag4) {
                        return false;
                    }
                
                    flag4 = true;
                } else if (TRAIL_INGREDIENT.test(itemstack)) {
                    if (flag3) {
                        return false;
                    }
                
                    flag3 = true;
                } else if (GUNPOWDER_INGREDIENT.test(itemstack)) {
                    if (flag) {
                        return false;
                    }
                
                    flag = true;
                } else {
                    if (!(itemstack.getItem() instanceof FlameDyeItem)) {
                        return false;
                    }
                
                    flag1 = true;
                }
            }
        }
    
        return flag && flag1;
    }
    
    /**
     * @author xkball
     * @reason 使得配方只能匹配焰色颜料
     */
    @Overwrite
    public @NotNull ItemStack assemble(@NotNull CraftingContainer craftingContainer) {
        ItemStack itemstack = new ItemStack(Items.FIREWORK_STAR);
        CompoundTag compoundtag = itemstack.getOrCreateTagElement("Explosion");
        FireworkRocketItem.Shape fireworkrocketitem$shape = FireworkRocketItem.Shape.SMALL_BALL;
        List<Integer> list = Lists.newArrayList();
    
        for(int i = 0; i < craftingContainer.getContainerSize(); ++i) {
            ItemStack itemstack1 = craftingContainer.getItem(i);
            if (!itemstack1.isEmpty()) {
                if (SHAPE_INGREDIENT.test(itemstack1)) {
                    fireworkrocketitem$shape = SHAPE_BY_ITEM.get(itemstack1.getItem());
                } else if (FLICKER_INGREDIENT.test(itemstack1)) {
                    compoundtag.putBoolean("Flicker", true);
                } else if (TRAIL_INGREDIENT.test(itemstack1)) {
                    compoundtag.putBoolean("Trail", true);
                } else if (itemstack1.getItem() instanceof FlameDyeItem) {
                    list.add(((DyeItem)itemstack1.getItem()).getDyeColor().getFireworkColor());
                }
            }
        }
    
        compoundtag.putIntArray("Colors", list);
        compoundtag.putByte("Type", (byte)fireworkrocketitem$shape.getId());
        return itemstack;
    }
}
