package com.xkball.flamereaction.itemlike.block.commonblocks;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.creativemodetab.CreativeModeTabs;
import com.xkball.flamereaction.itemlike.block.FRCBlock;
import com.xkball.flamereaction.itemlike.block.FRCInfo;
import com.xkball.flamereaction.itemlike.block.blockentity.DippingBlockEntity;
import com.xkball.flamereaction.util.ItemList;
import com.xkball.flamereaction.util.LevelUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DippingBlock extends BaseEntityBlock implements FRCBlock, FRCInfo {
    
    public static final String NAME = "dipping_block";
    public DippingBlock() {
        super(BlockBehaviour.Properties
                .of(Material.METAL)
                .strength(4f,8f)
                .noOcclusion()
        );
        this.setRegistryName(new ResourceLocation(FlameReaction.MOD_ID,NAME));
        add();
        regItemBlock();
    }
    
    public void regItemBlock(){
        var bi = new BlockItem(this,new Item.Properties().fireResistant().tab(CreativeModeTabs.FLAME_REACTION_GROUP));
        bi.setRegistryName(FlameReaction.MOD_ID, NAME);
        ItemList.addItem(bi);
    }
    
    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new DippingBlockEntity(pos,state);
    }
    
    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState p_49232_) {
        return RenderShape.MODEL;
    }
    
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level p_153212_, @NotNull BlockState p_153213_, @NotNull BlockEntityType<T> p_153214_) {
        return super.getTicker(p_153212_, p_153213_, p_153214_);
    }
    
    @Override
    @SuppressWarnings("deprecation")
    public @NotNull InteractionResult use(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos pos,
                                          @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult result) {
        if(!level.isClientSide){
            if(LevelUtil.ioWithBlock((ServerLevel) level,pos,player,player.getItemInHand(hand),null)){
                if(level.getBlockEntity(pos) instanceof DippingBlockEntity dp){
                    dp.dirty();
                }
            }
        }
        
        return InteractionResult.SUCCESS;
    }
    
    @Override
    public @NotNull List<String> getInfo(ServerLevel level, BlockPos pos) {
        var entity = level.getBlockEntity(pos);
        return List.of(NAME);
    }
    
    @Override
    public @NotNull String getChineseTranslate() {
        return "浸洗盆";
    }
}
