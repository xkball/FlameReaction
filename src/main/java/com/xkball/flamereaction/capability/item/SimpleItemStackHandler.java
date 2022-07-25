package com.xkball.flamereaction.capability.item;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public abstract class SimpleItemStackHandler implements IItemHandler {
    
    protected int getStackLimit(int slot, @Nonnull ItemStack stack)
    {
        return Math.min(getSlotLimit(slot), stack.getMaxStackSize());
    }
    
    @Override
    public int getSlots() {
        return 1;
    }
    
    @NotNull
    @Override
    public abstract ItemStack getStackInSlot(int slot);
    
    public abstract void setStackInSlot(int slot,ItemStack stack);
    
    @NotNull
    @Override
    //复制并修改自ItemStackHandler
    public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        if (stack.isEmpty())
            return ItemStack.EMPTY;
    
        if (!isItemValid(slot, stack))
            return stack;
    
        ItemStack existing = getStackInSlot(slot);
    
        int limit = getStackLimit(slot, stack);
    
        if (!existing.isEmpty())
        {
            if (!ItemHandlerHelper.canItemStacksStack(stack, existing))
                return stack;
        
            limit -= existing.getCount();
        }
    
        if (limit <= 0)
            return stack;
    
        boolean reachedLimit = stack.getCount() > limit;
    
        if (!simulate)
        {
            if (existing.isEmpty())
            {
                setStackInSlot(slot, reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack);
            }
            else
            {
                existing.grow(reachedLimit ? limit : stack.getCount());
            }
            onContentsChanged(slot);
        }
    
        return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount()- limit) : ItemStack.EMPTY;
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
                setStackInSlot(slot, ItemStack.EMPTY);
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
                setStackInSlot(slot, ItemHandlerHelper.copyStackWithSize(existing, existing.getCount() - toExtract));
                onContentsChanged(slot);
            }
        
            return ItemHandlerHelper.copyStackWithSize(existing, toExtract);
        }
    }
    
    @Override
    public int getSlotLimit(int slot) {
        return 64;
    }
    
    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return true;
    }
    
    public void onContentsChanged(int slot) {}
}
