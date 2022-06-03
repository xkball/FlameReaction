package com.xkball.flamereaction.itemlike.itemblock;

import com.xkball.flamereaction.itemlike.block.materialblock.MetalScaffoldingBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class MetalScaffoldingBlockItem extends BlockItem {
    
    public MetalScaffoldingBlockItem(Block p_40565_, Properties p_40566_) {
        super(p_40565_, p_40566_);
    }
    
    @Override
    public BlockPlaceContext updatePlacementContext(BlockPlaceContext blockPlaceContext) {
        BlockPos blockpos = blockPlaceContext.getClickedPos();
        Level level = blockPlaceContext.getLevel();
        BlockState blockstate = level.getBlockState(blockpos);
        //指向一个脚手架
        if(!level.isClientSide){
            if (!(blockstate.getBlock() instanceof MetalScaffoldingBlock)) {
                return blockPlaceContext;
            }
            else {
                Direction direction;
                //副手使用
                if (blockPlaceContext.isSecondaryUseActive()) {
                    direction =
                            //人是不是在方块里面
                            blockPlaceContext.isInside()
                                    ? blockPlaceContext.getClickedFace().getOpposite()
                                    : blockPlaceContext.getClickedFace();
                }
                //主手使用
                else {
                    direction =
                            //向上点击则获得对应方向
                            blockPlaceContext.getClickedFace() == Direction.UP
                                    ? blockPlaceContext.getHorizontalDirection()
                                    : Direction.UP;
                }
        
                int i = 0;
                BlockPos.MutableBlockPos mbpos = blockpos.mutable().move(direction);
        
                while (i < 50) {
            
                    //如果放置位置超过高度上限
                    if (!level.isInWorldBounds(mbpos)) {
                        Player player = blockPlaceContext.getPlayer();
                        int j = level.getMaxBuildHeight();
                        //一定记得区分玩家
                        if (player instanceof ServerPlayer && mbpos.getY() >= j) {
                            ((ServerPlayer) player).sendMessage((new TranslatableComponent("build.tooHigh", j - 1)).withStyle(ChatFormatting.RED), ChatType.GAME_INFO, Util.NIL_UUID);
                        }
                        return null;
                    }
            
                    blockstate = level.getBlockState(mbpos);
            
                    if (!blockstate.is(this.getBlock())) {
                        if (blockstate.canBeReplaced(blockPlaceContext)) {
                            return BlockPlaceContext.at(blockPlaceContext, mbpos, direction);
                        }
                        return null;
                    }
            
                    mbpos.move(direction);
                    if (direction.getAxis().isHorizontal()) {
                        ++i;
                    }
                }
                return null;
        
            }
        }
        return null;
        
        
    }
}
