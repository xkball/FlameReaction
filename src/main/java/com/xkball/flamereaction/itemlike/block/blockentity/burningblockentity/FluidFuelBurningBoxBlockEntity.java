package com.xkball.flamereaction.itemlike.block.blockentity.burningblockentity;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.capability.fluid.SimpleFluidHandler;
import com.xkball.flamereaction.crafting.util.FuelContainer;
import com.xkball.flamereaction.eventhandler.register.BlockEntityRegister;
import com.xkball.flamereaction.eventhandler.register.RecipeRegister;
import com.xkball.flamereaction.itemlike.block.blockentity.ITargetBlockEntity;
import com.xkball.flamereaction.itemlike.block.commonblocks.burningblock.FluidFuelBurningBox;
import com.xkball.flamereaction.itemlike.block.commonblocks.burningblock.SolidFuelBurningBox;
import com.xkball.flamereaction.util.LevelUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FluidFuelBurningBoxBlockEntity extends AbstractBurningBlockEntity implements ITargetBlockEntity {
    
    private NonNullList<FluidStack> fluidStacks = NonNullList.withSize(1,FluidStack.EMPTY);
    
    public FluidFuelBurningBoxBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(BlockEntityRegister.FlUID_FUEL_BURNING_BOX_BLOCK_ENTITY.get(), p_155229_, p_155230_);
    }
    
    
    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
            return LazyOptional.of(() -> new SimpleFluidHandler(5000) {
                @Override
                public @NotNull FluidStack getFluidInTank(int tank) {
                    return fluidStacks.get(0);
                }
    
                @Override
                public void onChanged() {
                    dirty();
                }
    
                @Override
                protected void setFluidInTank(int shot, FluidStack fluidStack) {
                    fluidStacks.set(0,fluidStack);
                }
            }).cast();
        }
        return super.getCapability(cap, side);
    }
    
    @Override
    public Block getTarget() {
        return FlameReaction.FLUID_FUEL_BURNING_BOX;
    }
    
    @Override
    public boolean updateFuel(BlockState bs) {
        if(bs.is(FlameReaction.FLUID_FUEL_BURNING_BOX) && bs.getValue(SolidFuelBurningBox.FIRED) && this.level != null ){
            if(this.timeLast>=0 && this.timeLast<5  ) {
                var fluidStack = this.fluidStacks.get(0);
                level.getRecipeManager().getRecipeFor(RecipeRegister.FUEL_RECIPE_TYPE.get(), new FuelContainer() {
                    @Override
                    public ItemStack getItem() {
                        return ItemStack.EMPTY;
                    }
                    
                    @Override
                    public FluidStack getFluid() {
                        return fluidStacks.get(0);
                    }
                }, level).ifPresent(recipe -> fluidStack.shrink(recipe.getItemFuel().getCount()));
                
                this.fluidStacks.set(0, fluidStack);
                dirty();
                return true;
            }
            if(this.timeLast < 0){
                bs.setValue(SolidFuelBurningBox.FIRED,Boolean.FALSE);
                this.level.setBlock(this.getBlockPos(),bs,Block.UPDATE_ALL);
            }
        }
        else {
            this.timeLast = 0;
        }
        return false;
    }
    
    @Override
    public void load(@NotNull CompoundTag compoundTag) {
        super.load(compoundTag);
        fluidStacks = NonNullList.withSize(1, FluidStack.EMPTY);
        LevelUtil.loadAllFluids(compoundTag,fluidStacks);
    }
    
    @Override
    protected void saveAdditional(@NotNull CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
       LevelUtil.saveAllFluids(compoundTag,fluidStacks,true);
    }
    
    @Override
    public @NotNull CompoundTag getUpdateTag() {
        var compoundTag = super.getUpdateTag();
        LevelUtil.saveAllFluids(compoundTag,fluidStacks,true);
        return compoundTag;
    }
    
    @Override
    public void handleUpdateTag(CompoundTag compoundTag) {
        super.handleUpdateTag(compoundTag);
        fluidStacks = NonNullList.withSize(1, FluidStack.EMPTY);
        LevelUtil.loadAllFluids(compoundTag,fluidStacks);
        
    }
    
    public FluidStack getFluid() {
        return fluidStacks.get(0);
    }
}
