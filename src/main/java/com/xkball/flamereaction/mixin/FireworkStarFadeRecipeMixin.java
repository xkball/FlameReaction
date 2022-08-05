package com.xkball.flamereaction.mixin;

import com.xkball.flamereaction.config.FireworkStarConfig;
import com.xkball.flamereaction.itemlike.item.commonitem.FlameDyeItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.FireworkStarFadeRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FireworkStarFadeRecipe.class)
public abstract class FireworkStarFadeRecipeMixin extends CustomRecipe {
    
    private static final Ingredient STAR_INGREDIENT = Ingredient.of(Items.FIREWORK_STAR);
    
    protected FireworkStarFadeRecipeMixin(ResourceLocation p_43833_) {
        super(p_43833_);
    }
    
    //使得配方只能匹配焰色染料
    @Inject(method = "matches(Lnet/minecraft/world/inventory/CraftingContainer;Lnet/minecraft/world/level/Level;)Z",
        at = @At("HEAD"),
        cancellable = true)
    public void onMatches(CraftingContainer craftingContainer, Level pLevel, CallbackInfoReturnable<Boolean> cir) {
        if( FireworkStarConfig.SP_FIREWORK_RECIPE.get()) {
            boolean flag = false;
            boolean flag1 = false;
    
            for (int i = 0; i < craftingContainer.getContainerSize(); ++i) {
                ItemStack itemstack = craftingContainer.getItem(i);
                if (!itemstack.isEmpty()) {
                    //!
                    if (itemstack.getItem() instanceof FlameDyeItem) {
                        flag = true;
                    } else {
                        if (!STAR_INGREDIENT.test(itemstack)) {
                            cir.setReturnValue(false);
                            cir.cancel();
                            return;
                        }
                
                        if (flag1) {
                            cir.setReturnValue(false);
                            cir.cancel();
                            return;
                        }
                
                        flag1 = true;
                    }
                }
            }
            cir.setReturnValue(flag1 && flag);
        }
    }
}
