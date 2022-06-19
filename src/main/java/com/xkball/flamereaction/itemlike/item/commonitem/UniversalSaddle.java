package com.xkball.flamereaction.itemlike.item.commonitem;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.itemgroup.Groups;
import com.xkball.flamereaction.itemlike.item.FRCItem;
import com.xkball.flamereaction.util.translateutil.TranslateUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;

public class UniversalSaddle extends Item implements FRCItem {
    
    public static final String NAME = "universal_saddle";
    public static final TranslatableComponent UNIVERSAL_SADDLE_TOOLTIP = TranslateUtil.create("try left click any entity","试试左键实体");
    
    public UniversalSaddle() {
        super(new Item.Properties()
                .fireResistant()
                .setNoRepair()
                .tab(Groups.FLAME_REACTION_GROUP));
        this.setRegistryName(FlameReaction.MOD_ID,NAME);
        add();
    }
    
    
    @Override
    public void appendHoverText(@Nonnull ItemStack p_41421_, @Nullable Level p_41422_,
                                @Nonnull List<Component> components, @Nonnull TooltipFlag p_41424_) {
        components.add(UNIVERSAL_SADDLE_TOOLTIP.withStyle(ChatFormatting.GRAY));
    }
    
    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if(entity instanceof LivingEntity || entity instanceof AbstractMinecart) player.startRiding(entity,true);
        return true;
    }
}
