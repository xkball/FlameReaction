package com.xkball.flamereaction.itemlike.item.commonitem.tool;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.creativemodetab.CreativeModeTabs;
import com.xkball.flamereaction.itemlike.block.blockentity.ExhibitBlockEntity;
import com.xkball.flamereaction.itemlike.item.FRCItem;
import com.xkball.flamereaction.util.translateutil.TranslateUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionResult;
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
    
    public static final TranslatableComponent TOOLTIP1 = new TranslatableComponent(TranslateUtil.PREFIX+"exhibit_key_tooltip1");
    public static final TranslatableComponent TOOLTIP2 = new TranslatableComponent(TranslateUtil.PREFIX+"exhibit_key_tooltip2");
    public ExhibitBlockKey() {
        super(new Item.Properties()
                .fireResistant()
                .setNoRepair()
                .tab(CreativeModeTabs.FLAME_REACTION_GROUP));
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
    
    @Override
    public @NotNull String getChineseTranslate() {
        return "展览方块钥匙";
    }
}
