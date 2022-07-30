package com.xkball.flamereaction.itemlike.block.commonblocks;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.itemlike.block.FRCBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.function.Supplier;

public class UnstableFluidBlock extends LiquidBlock implements FRCBlock {
    
    public static final String IMPURE_ALCOHOL_FLUID_NAME = "impure_alcohol";
    public UnstableFluidBlock(Supplier<? extends FlowingFluid> supplier ,String string) {
        super(supplier,
                BlockBehaviour.Properties.of(Material.WATER).noDrops().strength(1F));
        //this.setRegistryName(new ResourceLocation(FlameReaction.MOD_ID,string));
        //add();
    }
    
    @Override
    public void onPlace(@NotNull BlockState p_54754_, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState p_54757_, boolean p_54758_) {
        if(!level.isClientSide)level.scheduleTick(pos,this,1);
        super.onPlace(p_54754_, level, pos, p_54757_, p_54758_);
    }
    
    @Override
    @SuppressWarnings("deprecation")
    public void tick(@NotNull BlockState blockState, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull Random p_60465_) {
        var fluid = level.getFluidState(pos);
        if(fluid.isSource()){
            level.setBlock(pos,FlameReaction.IMPURE_ALCOHOL_FLUID_BLOCK.defaultBlockState().setValue(LiquidBlock.LEVEL,14), Block.UPDATE_ALL);
        }
    }
    
    @Override
    public @NotNull String getChineseTranslate() {
        return "不稳定的流体方块";
    }
}
