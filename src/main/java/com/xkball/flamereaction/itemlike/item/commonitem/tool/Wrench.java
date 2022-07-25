package com.xkball.flamereaction.itemlike.item.commonitem.tool;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.creativemodetab.CreativeModeTabs;
import com.xkball.flamereaction.itemlike.block.FRCInfo;
import com.xkball.flamereaction.itemlike.block.blocktags.BlockTags;
import com.xkball.flamereaction.itemlike.item.FRCItem;
import com.xkball.flamereaction.util.translateutil.TranslateUtil;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Wrench extends Item implements FRCItem {
    
    public static final String NAME = "wrench";
    public static final TranslatableComponent tooltip1 = TranslateUtil.create("tooltip.wrench1","可以连锁拆解铁脚手架","use it to break iron scaffolding");
    public static final TranslatableComponent tooltip2 = TranslateUtil.create("tooltip.wrench2","右键焰色反应的方块以获得更多信息","right click flamereaction blocks for more information");
    
    public Wrench(){
        super(new Properties()
                .durability(200)
                .fireResistant()
                .tab(CreativeModeTabs.FLAME_REACTION_GROUP));
        this.setRegistryName(FlameReaction.MOD_ID,NAME);
        add();
    }
    
    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level p_41422_, @NotNull List<Component> components, @NotNull TooltipFlag flag) {
        components.add(tooltip1);
        components.add(tooltip2);
        super.appendHoverText(itemStack, p_41422_, components, flag);
    }
    
    @Override
    public boolean mineBlock(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull BlockState blockState, @NotNull BlockPos pos, @NotNull LivingEntity livingEntity) {
        if (!level.isClientSide && blockState.getDestroySpeed(level, pos) != 0.0F) {
            if(blockState.is(BlockTags.SCAFFOLDING))itemStack.hurtAndBreak(1, livingEntity, (p_40992_) -> {
                p_40992_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }
        return true;
    }
    
    @Override
    public float getDestroySpeed(@NotNull ItemStack p_41425_, @NotNull BlockState blockState) {
        return blockState.is(BlockTags.MINEABLE_WRENCH)? 8F:1F;
    }
    
    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return state.is(BlockTags.MINEABLE_WRENCH);
    }
    
    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        var level = context.getLevel();
        if(!level.isClientSide){
            var pos = context.getClickedPos();
            var block = level.getBlockState(pos);
            var player = context.getPlayer();
            if(block.getBlock() instanceof FRCInfo f && player != null){
                for(String s : f.getInfo((ServerLevel) level,pos)){
                    var c = Component.nullToEmpty(s);
                    ((ServerPlayer)player).sendMessage(c, ChatType.CHAT, Util.NIL_UUID);
                }
                
            }
        }
        return InteractionResult.SUCCESS;
    }
    
    @Override
    public @NotNull String getChineseTranslate() {
        return "扳手";
    }
}
