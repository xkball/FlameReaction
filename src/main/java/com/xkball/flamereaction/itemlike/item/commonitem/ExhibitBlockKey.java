package com.xkball.flamereaction.itemlike.item.commonitem;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.itemgroup.Groups;
import com.xkball.flamereaction.itemlike.block.blockentity.ExhibitBlockEntity;
import com.xkball.flamereaction.itemlike.item.FRCItem;
import com.xkball.flamereaction.util.translateutil.TranslateUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;


public class ExhibitBlockKey extends Item implements FRCItem {
    
    public static final String NAME = "exhibit_block_key";
    
    public static final TranslatableComponent TOOLTIP1 = new TranslatableComponent(TranslateUtil.PREFIX+"use in right hand to lock a exhibit_block");
    public static final TranslatableComponent TOOLTIP2 = new TranslatableComponent(TranslateUtil.PREFIX+"press shift and use to change display state of a exhibit_lock");
    public ExhibitBlockKey() {
        super(new Item.Properties()
                .fireResistant()
                .setNoRepair()
                .tab(Groups.FLAME_REACTION_GROUP));
        this.setRegistryName(FlameReaction.MOD_ID,NAME);
        add();
    }
    
    @Override
    public void appendHoverText(@Nonnull ItemStack p_41421_, @Nullable Level p_41422_,
                                @Nonnull List<Component> components,@Nonnull TooltipFlag p_41424_) {
        components.add(TOOLTIP1.withStyle(ChatFormatting.GRAY));
        components.add(TOOLTIP2.withStyle(ChatFormatting.GRAY));
    }
    
    
    
    @Override
    public @Nonnull InteractionResult useOn(@Nonnull UseOnContext useOnContext) {
        if(!useOnContext.getLevel().isClientSide){
            if(Objects.requireNonNull(useOnContext.getPlayer()).isShiftKeyDown()){
                var entity = useOnContext.getLevel().getBlockEntity(useOnContext.getClickedPos());
                if(entity instanceof ExhibitBlockEntity e){
                    e.setLikeItemEntity(!e.isLikeItemEntity());
                }
            }
        }
        return super.useOn(useOnContext);
    }
}
