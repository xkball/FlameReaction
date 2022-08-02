package com.xkball.flamereaction.itemlike.block.blockentity.burningblockentity;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.capability.fluid.SimpleFluidHandler;
import com.xkball.flamereaction.crafting.util.FuelContainer;
import com.xkball.flamereaction.eventhandler.register.BlockEntityRegister;
import com.xkball.flamereaction.eventhandler.register.RecipeRegister;
import com.xkball.flamereaction.itemlike.block.blockentity.ITargetBlockEntity;
import com.xkball.flamereaction.itemlike.block.blockentity.burningblockentity.AbstractBurningBlockEntity;
import com.xkball.flamereaction.itemlike.block.commonblocks.burningblock.AlcoholLamp;
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

import java.util.concurrent.atomic.AtomicInteger;

public class AlcoholLampBlockEntity extends AbstractBurningBlockEntity implements ITargetBlockEntity {
    
    public static final String NAME = "alcohol_lamp_block_entity";
    private NonNullList<FluidStack> fluidStack = NonNullList.withSize(1,FluidStack.EMPTY);
    
    private int color = 0;
    
    public AlcoholLampBlockEntity( BlockPos p_155229_, BlockState p_155230_) {
        super(BlockEntityRegister.ALCOHOL_LAMP_BLOCK_ENTITY.get(), p_155229_, p_155230_);
    }
    
    @Override
    public boolean canProduceHeat(BlockState blockState) {
        return blockState.getValue(AlcoholLamp.HAS_IRON_STAND);
    }
    
    @Override
    public boolean updateFuel(BlockState bs) {
        if(bs.is(FlameReaction.FLUID_FUEL_BURNING_BOX) && bs.getValue(SolidFuelBurningBox.FIRED) && this.level != null ){
            var fluidStack1 = this.fluidStack.get(0);
            if(timeLast>=5) return true;
            AtomicInteger time = new AtomicInteger();
            level.getRecipeManager().getRecipeFor(RecipeRegister.FUEL_RECIPE_TYPE.get(), new FuelContainer() {
                @Override
                public ItemStack getItem() {
                    return ItemStack.EMPTY;
                }
                
                @Override
                public FluidStack getFluid() {
                    return fluidStack.get(0);
                }
            }, level).ifPresent(recipe -> {
                fluidStack1.shrink(recipe.getFluidFuel().getAmount());
                time.set(recipe.getTime());
                this.setMaxHeatProduce(recipe.getMaxHeat());
            });
            this.timeLast = timeLast+ time.get();
            this.fluidStack.set(0, fluidStack1);
            dirty();
            if(timeLast>0) return true;
            if(fluidStack.isEmpty()){
                bs = bs.setValue(FluidFuelBurningBox.FIRED,Boolean.FALSE);
                this.level.setBlock(this.getBlockPos(),bs,Block.UPDATE_ALL);
                this.setMaxHeatProduce(0);
                this.timeLast = 0;
                dirty();
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
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
            return LazyOptional.of(() -> new SimpleFluidHandler(3000) {
                @Override
                public @NotNull FluidStack getFluidInTank(int tank) {
                    return fluidStack.get(0);
                }
    
                @Override
                protected void setFluidInTank(int shot, FluidStack fluidStack1) {
                    fluidStack.set(0,fluidStack1);
                }
    
                @Override
                public void onChanged() {
                    dirty();
                }
                
            }).cast();
        }
        return super.getCapability(cap, side);
    }
    
    @Override
    public Block getTarget() {
        return FlameReaction.ALCOHOL_LAMP;
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
    
    public FluidStack getFluid(){
        return fluidStack.get(0);
    }
}
