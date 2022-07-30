package com.xkball.flamereaction.itemlike.block.blockentity.burningblockentity;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.capability.item.SimpleItemStackHandler;
import com.xkball.flamereaction.crafting.util.FuelContainer;
import com.xkball.flamereaction.eventhandler.register.BlockEntityRegister;
import com.xkball.flamereaction.eventhandler.register.RecipeRegister;
import com.xkball.flamereaction.itemlike.block.blockentity.ITargetBlockEntity;
import com.xkball.flamereaction.itemlike.block.commonblocks.burningblock.SolidFuelBurningBox;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.CapabilityItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SolidFuelBurningBoxBlockEntity extends AbstractBurningBlockEntity implements ITargetBlockEntity {
    
    private NonNullList<ItemStack> items = NonNullList.withSize(1,ItemStack.EMPTY);
    
    
    public SolidFuelBurningBoxBlockEntity( BlockPos p_155229_, BlockState p_155230_) {
        super(BlockEntityRegister.SOLID_FUEL_BURNING_BOX_BLOCK_ENTITY.get(), p_155229_, p_155230_);
    }
    
    
    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return LazyOptional.of(() -> new SimpleItemStackHandler() {
                @Override
                public @NotNull ItemStack getStackInSlot(int slot) {
                    return items.get(slot);
                }
    
                @Override
                public void setStackInSlot(int slot, ItemStack stack) {
                    items.set(slot,stack);
                }
    
                @Override
                public void onContentsChanged(int slot) {
                    dirty();
                }
            }).cast();
        }
        return super.getCapability(cap, side);
    }
    
    @Override
    public Block getTarget() {
        return FlameReaction.SOLID_FUEL_BURNING_BOX;
    }
    
    @Override
    public boolean updateFuel(BlockState bs) {
        if(bs.is(FlameReaction.SOLID_FUEL_BURNING_BOX) && bs.getValue(SolidFuelBurningBox.FIRED) && this.level != null ){
            if(this.timeLast>0 && this.timeLast<5  ) {
                var item = this.items.get(0);
                level.getRecipeManager().getRecipeFor(RecipeRegister.FUEL_RECIPE_TYPE.get(), new FuelContainer() {
                    @Override
                    public ItemStack getItem() {
                        return item;
                    }
        
                    @Override
                    public FluidStack getFluid() {
                        return FluidStack.EMPTY;
                    }
                }, level).ifPresent(recipe -> item.shrink(recipe.getItemFuel().getCount()));
    
                this.items.set(0, item);
                dirty();
                return true;
            }
            else if(this.timeLast == 0){
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
        items = NonNullList.withSize(1,ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compoundTag,items);
    }
    
    @Override
    protected void saveAdditional(@NotNull CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        ContainerHelper.saveAllItems(compoundTag,items);
    }
    
    @Override
    public @NotNull CompoundTag getUpdateTag() {
        var compoundTag = super.getUpdateTag();
        ContainerHelper.saveAllItems(compoundTag,items);
        return compoundTag;
    }
    
    @Override
    public void handleUpdateTag(CompoundTag compoundTag) {
        super.handleUpdateTag(compoundTag);
        items = NonNullList.withSize(1,ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compoundTag,items);
        
    }
    
    public ItemStack getItems() {
        return items.get(0);
    }
}
