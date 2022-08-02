package com.xkball.flamereaction.itemlike.item.commonitem.tool;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.creativemodetab.CreativeModeTabs;
import com.xkball.flamereaction.itemlike.block.blockentity.burningblockentity.AbstractBurningBlockEntity;
import com.xkball.flamereaction.itemlike.block.blocktags.BlockTags;
import com.xkball.flamereaction.itemlike.block.commonblocks.burningblock.AbstractBurningBlock;
import com.xkball.flamereaction.itemlike.item.FRCItem;
import com.xkball.flamereaction.network.NetworkHandler;
import com.xkball.flamereaction.network.message.OpenGlassCraftingGUIMessage;
import com.xkball.flamereaction.util.LevelUtil;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

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
            itemStack.hurtAndBreak(1, livingEntity, (p_40992_) -> p_40992_.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
        return true;
    }
    
    @Override
    public float getDestroySpeed(@NotNull ItemStack p_41425_, @NotNull BlockState blockState) {
        return blockState.is(BlockTags.MINEABLE_PLIERS)? 8F:1F;
    }
    
    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext pContext) {
        var level = pContext.getLevel();
        var player = pContext.getPlayer();
        var item = pContext.getItemInHand();
        var bs = level.getBlockState(pContext.getClickedPos());
        if(!level.isClientSide && player != null){
            if (item.hasTag() && Objects.requireNonNull(item.getTag()).contains("has_item") && LevelUtil.hasBoolean(item,"has_item")){
                if(item.getTag().contains("list")){
                    var list = item.getTag().getIntArray("list");
                    NetworkHandler.sendToClientPlayer(new OpenGlassCraftingGUIMessage(player.getUUID(), IntList.of(list)), player);
                }
                else {
                    int[] list = new int[25];
                    for(int i=0;i<25;i++){
                        list[i] = 0;
                    }
                    NetworkHandler.sendToClientPlayer(new OpenGlassCraftingGUIMessage(player.getUUID(), IntList.of(list)), player);
                }
                
                return InteractionResult.SUCCESS;
            }
            else if(!LevelUtil.hasBoolean(item,"has_item")){
                if(bs.is(Tags.Blocks.GLASS)){
                    var bp = pContext.getClickedPos().mutable().move(Direction.DOWN);
                    var bsd = level.getBlockState(bp);
                    var be = level.getBlockEntity(bp);
                    if(bsd.getBlock() instanceof AbstractBurningBlock &&
                            be instanceof AbstractBurningBlockEntity &&
                            bsd.getValue(AbstractBurningBlock.FIRED)){
                        LevelUtil.addBooleanTagToItem(item,"has_item",true);
                        level.setBlock(pContext.getClickedPos(),Blocks.AIR.defaultBlockState(),Block.UPDATE_ALL);
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }
        return InteractionResult.PASS;
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
