package com.xkball.flamereaction.itemlike.block.blockentity.burningblockentity;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.eventhandler.register.BlockEntityRegister;
import com.xkball.flamereaction.itemlike.block.blockentity.ITargetBlockEntity;
import com.xkball.flamereaction.itemlike.block.blockentity.burningblockentity.AbstractBurningBlockEntity;
import com.xkball.flamereaction.util.LevelUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

public class AlcoholLampBlockEntity extends AbstractBurningBlockEntity implements ITargetBlockEntity {
    
    public static final String NAME = "alcohol_lamp_block_entity";
    private NonNullList<FluidStack> fluidStack = NonNullList.withSize(1,FluidStack.EMPTY);
    
    private int color = 0;
    
    public AlcoholLampBlockEntity( BlockPos p_155229_, BlockState p_155230_) {
        super(BlockEntityRegister.ALCOHOL_LAMP_BLOCK_ENTITY.get(), p_155229_, p_155230_);
    }
    
    @Override
    public void load(@NotNull CompoundTag compoundTag) {
        super.load(compoundTag);
        fluidStack = NonNullList.withSize(1,FluidStack.EMPTY);
        LevelUtil.loadAllFluids(compoundTag,fluidStack);
        color = compoundTag.getInt("color");
    }
    
    @Override
    protected void saveAdditional(@NotNull CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        LevelUtil.saveAllFluids(compoundTag,fluidStack,true);
        compoundTag.putInt("color",color);
    }
    
    @Override
    public @NotNull CompoundTag getUpdateTag() {
        var compoundTag =  super.getUpdateTag();
        LevelUtil.saveAllFluids(compoundTag,fluidStack,true);
        compoundTag.putInt("color",color);
        return compoundTag;
    }
    
    @Override
    public void handleUpdateTag(CompoundTag compoundTag) {
        super.handleUpdateTag(compoundTag);
        fluidStack = NonNullList.withSize(1,FluidStack.EMPTY);
        LevelUtil.loadAllFluids(compoundTag,fluidStack);
        color = compoundTag.getInt("color");
    }
    
    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        return super.getCapability(cap);
    }
    
    @Override
    public Block getTarget() {
        return FlameReaction.ALCOHOL_LAMP;
    }
    
    @Override
    public boolean updateFuel(BlockState bs) {
        return false;
    }
    
    public int getColor() {
        return color;
    }
    
    public void cleanColor(){
        color = 0;
    }
    
    
    public void setColor(int color) {
        this.color = color;
    }
    
}
