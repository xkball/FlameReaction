package com.xkball.flamereaction.mixin;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.config.FireworkStarConfig;
import com.xkball.flamereaction.util.translateutil.TranslateUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.FireworkStarItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(FireworkStarItem.class)
public abstract class FireworkStarItemMixin extends Item {
    
    
    
    public FireworkStarItemMixin(Properties pProperties) {
        super(pProperties);
    }
    
    @Inject(method = "appendHoverText(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Ljava/util/List;Lnet/minecraft/world/item/TooltipFlag;)V",
            at = @At("TAIL"))
    public void onAppendTooltip(ItemStack pStack, Level pLevel, List<Component> pTooltip, TooltipFlag pFlag, CallbackInfo ci){
        //boolean b = FlameReaction.b;
        pTooltip.add(FlameReaction.tooltip1.withStyle(ChatFormatting.UNDERLINE));
        //pTooltip.add(FlameReaction.tooltip2.append(String.valueOf(b)).withStyle(ChatFormatting.UNDERLINE));
    }
}
