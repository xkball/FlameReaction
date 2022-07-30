package com.xkball.flamereaction.mixin;

import com.google.common.collect.Maps;
import com.xkball.flamereaction.itemlike.item.commonitem.FlameDyeItem;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.FireworkStarRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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
    
    //使得配方只能匹配焰色染料
    @Inject(method = "matches(Lnet/minecraft/world/inventory/CraftingContainer;Lnet/minecraft/world/level/Level;)Z",
        at = @At("HEAD"),
        cancellable = true)
    public void onMatches(CraftingContainer craftingContainer, Level pLevel, CallbackInfoReturnable<Boolean> cir) {
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
                        cir.setReturnValue(false);
                        cir.cancel();
                        return;
                    }
                
                    flag2 = true;
                } else if (FLICKER_INGREDIENT.test(itemstack)) {
                    if (flag4) {
                        cir.setReturnValue(false);
                        cir.cancel();
                        return;
                    }
                
                    flag4 = true;
                } else if (TRAIL_INGREDIENT.test(itemstack)) {
                    if (flag3) {
                        cir.setReturnValue(false);
                        cir.cancel();
                        return;
                    }
                
                    flag3 = true;
                } else if (GUNPOWDER_INGREDIENT.test(itemstack)) {
                    if (flag) {
                        cir.setReturnValue(false);
                        cir.cancel();
                        return;
                    }
                
                    flag = true;
                } else {
                    if (!(itemstack.getItem() instanceof FlameDyeItem)) {
                        cir.setReturnValue(false);
                        cir.cancel();
                        return;
                    }
                
                    flag1 = true;
                }
            }
        }
        cir.setReturnValue(flag && flag1);
    }
}
