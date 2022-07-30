package com.xkball.flamereaction.util;

import com.xkball.flamereaction.capability.item.SimpleItemStackHandler;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;

public class CapabilityUtil {
    
    
    
    
    public static ItemStack extractItem(SimpleItemStackHandler iItemHandler, int slot, int amount, boolean simulate) {
        if (amount == 0)
            return ItemStack.EMPTY;
        
        ItemStack existing = iItemHandler.getStackInSlot(slot);
        
        if (existing.isEmpty())
            return ItemStack.EMPTY;
        
        int toExtract = Math.min(amount, existing.getMaxStackSize());
        
        if (existing.getCount() <= toExtract)
        {
            if (!simulate)
            {
                iItemHandler.setStackInSlot(slot,ItemStack.EMPTY);
                iItemHandler.onContentsChanged(slot);
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
                iItemHandler.setStackInSlot(slot, ItemHandlerHelper.copyStackWithSize(existing, existing.getCount() - toExtract));
                iItemHandler.onContentsChanged(slot);
            }
            
            return ItemHandlerHelper.copyStackWithSize(existing, toExtract);
        }
    }
}
