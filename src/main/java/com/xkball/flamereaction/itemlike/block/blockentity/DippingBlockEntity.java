package com.xkball.flamereaction.itemlike.block.blockentity;

import com.xkball.flamereaction.capability.item.SimpleItemStackHandler;
import com.xkball.flamereaction.eventhandler.register.BlockEntityRegister;
import com.xkball.flamereaction.util.LevelUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DippingBlockEntity extends EasyChangedBlockEntity{
    
    public static final String NAME = "dipping_block_entity";
    
    private NonNullList<FluidStack> fluid = NonNullList.withSize(1,FluidStack.EMPTY);
    private NonNullList<ItemStack> items = NonNullList.withSize(2,ItemStack.EMPTY);
    
    public DippingBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(BlockEntityRegister.DIPPING_BLOCK_ENTITY.get(), p_155229_, p_155230_);
    }
    
    public static void tick(Level level, BlockPos pos, BlockState state, DippingBlockEntity entity) {
    
    }
    
    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return LazyOptional.of(() -> new SimpleItemStackHandler(){
                @Override
                public @NotNull ItemStack getStackInSlot(int slot) {
                    return items.get(slot);
                }
        
                @Override
                public void setStackInSlot(int slot, ItemStack stack) {
                     items.set(slot,stack);
                }
        
                @NotNull
                @Override
                public ItemStack extractItem(int slot, int amount, boolean simulate) {
                    if (amount == 0)
                        return ItemStack.EMPTY;
                    
                    ItemStack existing = getStackInSlot(slot);
    
                    if (existing.isEmpty())
                        return ItemStack.EMPTY;
    
                    int toExtract = Math.min(amount, existing.getMaxStackSize());
    
                    if (existing.getCount() <= toExtract)
                    {
                        if (!simulate)
                        {
                            this.setStackInSlot(slot,ItemStack.EMPTY);
                            onContentsChanged(slot);
                            return existing;
                        }
                        else
                        {
                            return existing.copy();
                        }
                    }
                    else
                    {
                        if (!simulate)
                        {
                            this.setStackInSlot(slot, ItemHandlerHelper.copyStackWithSize(existing, existing.getCount() - toExtract));
                            onContentsChanged(slot);
                        }
        
                        return ItemHandlerHelper.copyStackWithSize(existing, toExtract);
                    }
                }
    
                @Override
                public int getSlots() {
                    return 2;
                }
    
                @Override
                protected void onContentsChanged(int slot) {
                    dirty();
                }
            }).cast();
        }
        return super.getCapability(cap, side);
    }
    
    @Override
    public void load(@NotNull CompoundTag compoundTag) {
        super.load(compoundTag);
        fluid = NonNullList.withSize(1,FluidStack.EMPTY);
        items = NonNullList.withSize(2,ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compoundTag,items);
        LevelUtil.loadAllFluids(compoundTag,fluid);
    }
    
    @Override
    protected void saveAdditional(@NotNull CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        ContainerHelper.saveAllItems(compoundTag,items,true);
        LevelUtil.saveAllFluids(compoundTag,fluid,true);
    }
    
    @Override
    public @NotNull CompoundTag getUpdateTag() {
        var compoundTag =  super.getUpdateTag();
        ContainerHelper.saveAllItems(compoundTag,items,true);
        LevelUtil.saveAllFluids(compoundTag,fluid,true);
        return compoundTag;
    }
    
    @Override
    public void handleUpdateTag(CompoundTag compoundTag) {
        super.handleUpdateTag(compoundTag);
        fluid = NonNullList.withSize(1,FluidStack.EMPTY);
        items = NonNullList.withSize(2,ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compoundTag,items);
        LevelUtil.loadAllFluids(compoundTag,fluid);
    }
}
