package com.xkball.flamereaction.itemlike.block.commonblocks;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.itemlike.block.FRCBlock;
import com.xkball.flamereaction.itemlike.block.blocktags.BlockTags;
import com.xkball.flamereaction.itemlike.item.materialitem.ColoredFlameItem;
import com.xkball.flamereaction.part.material.FlammableChemicalMaterials;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Random;

public class FlameFireBlock extends BaseFireBlock implements FRCBlock {
    
    public static final String NAME = "flame_fire_block";
    
    public static final BooleanProperty IS_OBVIOUS_COLORED = BooleanProperty.create("is_obvious_colored");
    public static final EnumProperty<FlammableChemicalMaterials> MATERIAL = EnumProperty.create("material",FlammableChemicalMaterials.class);
    
    public FlameFireBlock() {
        super(BlockBehaviour.Properties.of(Material.FIRE)
                .noCollission()
                .instabreak()
                .lightLevel((blockState) -> 15)
                .sound(SoundType.WOOL),0F);
        this.setRegistryName(FlameReaction.MOD_ID,NAME);
        this.registerDefaultState(this.getStateDefinition().any()
                .setValue(MATERIAL,FlammableChemicalMaterials.SODIUM_CHLORIDE)
                .setValue(IS_OBVIOUS_COLORED,Boolean.FALSE));
        add();
    }
    
    @Override
    protected boolean canBurn(BlockState blockState) {
        return blockState.is(BlockTags.COLORED_FLAMMABLE);
    }
    
    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(
                MATERIAL,
                IS_OBVIOUS_COLORED
        );
    }
    
    @Override
    public void onPlace(@NotNull BlockState p_49279_, Level level, @NotNull BlockPos blockPos, @NotNull BlockState p_49282_, boolean p_49283_) {
        level.scheduleTick(blockPos,this,1);
        super.onPlace(p_49279_, level, blockPos, p_49282_, p_49283_);
    }
//
//    @Override
//    public BlockEntity newBlockEntity(@Nonnull BlockPos blockPos, @Nonnull BlockState blockState){
//        return new FlameFireBlockEntity(blockPos,blockState);
//    }
    
    
    @Override
    @SuppressWarnings("deprecation")
    public void tick(@NotNull BlockState p_60462_, @NotNull ServerLevel level, @NotNull BlockPos blockPos, @NotNull Random p_60465_) {
       var bp = blockPos.mutable().move(Direction.DOWN);
       var bs = level.getBlockState(bp);
       if(! bs.is(BlockTags.COLORED_FLAMMABLE)) level.destroyBlock(blockPos,false);
       
    }
    
    @Override
    @SuppressWarnings("deprecation")
    public @Nonnull
    InteractionResult use(@Nonnull BlockState blockState, @Nonnull Level level, @Nonnull BlockPos blockPos,
                          @Nonnull Player player, @Nonnull InteractionHand interactionHand, @Nonnull BlockHitResult blockHitResult) {
        if(!level.isClientSide){
            //var block_entity = (FlameFireBlockEntity)level.getBlockEntity(blockPos);
            var item_input = player.getMainHandItem();
//            if(item_input.isEmpty()){
//                //Objects.requireNonNull(block_entity).setItem(item_input.getItem());
//            }
            if(item_input.getItem() instanceof ColoredFlameItem cfi){
                var mic = (FlammableChemicalMaterials)cfi.getMaterial();
                blockState = blockState.setValue(MATERIAL,mic);
                blockState = blockState.setValue(IS_OBVIOUS_COLORED,mic.isObvious());
                
                level.setBlock(blockPos,blockState,Block.UPDATE_CLIENTS);
                level.scheduleTick(blockPos,this,1);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }
    
    @Override
    public @NotNull String getChineseTranslate() {
        return "焰色火焰方块";
    }
}
