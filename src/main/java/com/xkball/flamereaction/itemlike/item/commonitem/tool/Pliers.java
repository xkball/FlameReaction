package com.xkball.flamereaction.itemlike.item.commonitem.tool;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.creativemodetab.CreativeModeTabs;
import com.xkball.flamereaction.gui.GlassCraftingScreen;
import com.xkball.flamereaction.itemlike.block.blocktags.BlockTags;
import com.xkball.flamereaction.itemlike.item.FRCItem;
import com.xkball.flamereaction.network.NetworkHandler;
import com.xkball.flamereaction.network.message.OpenGlassCraftingGUIMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import org.jetbrains.annotations.NotNull;

public class Pliers extends Item implements FRCItem {
    
    public static final String NAME = "pliers";
    public Pliers() {
        super(new Properties()
                .fireResistant()
                .tab(CreativeModeTabs.FLAME_REACTION_GROUP)
                .durability(200));
        this.setRegistryName(new ResourceLocation(FlameReaction.MOD_ID,NAME));
        add();
    }
    
    @Override
    public boolean mineBlock(@NotNull ItemStack itemStack, @NotNull Level level, @NotNull BlockState blockState, @NotNull BlockPos pos, @NotNull LivingEntity livingEntity) {
        if (!level.isClientSide && blockState.getDestroySpeed(level, pos) != 0.0F) {
            itemStack.hurtAndBreak(1, livingEntity, (p_40992_) -> {
                p_40992_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
            });
        }
        return true;
    }
    
    @Override
    public float getDestroySpeed(@NotNull ItemStack p_41425_, @NotNull BlockState blockState) {
        return blockState.is(BlockTags.MINEABLE_PLIERS)? 8F:1F;
    }
    
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        if(!level.isClientSide){
            NetworkHandler.sendToClientPlayer(new OpenGlassCraftingGUIMessage(player.getUUID()), player);
        }
        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
    
    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return state.is(BlockTags.MINEABLE_PLIERS);
    }
    
    @Override
    public @NotNull String getChineseTranslate() {
        return "钳子";
    }
}
