package com.xkball.flamereaction.itemlike.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.CapabilityItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public interface FRCInfo {
    @NotNull List<String> getInfo(ServerLevel level, BlockPos pos);
    
    
    default @NotNull List<String> getItemInfo(BlockEntity entity){
        var result = new java.util.ArrayList<>(List.of("内含物品: "));
        AtomicBoolean f = new AtomicBoolean(false);
        var cap = entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
        cap.ifPresent((iItemHandler -> {
            for(int i=0;i<iItemHandler.getSlots();i++){
                result.add(i+": "+iItemHandler.getStackInSlot(i));
                f.set(true);
            }
        }));
        if(f.get()) return result;
        result.add("无");
        return result;
    }
    
    default String getFluidInfo(FluidStack fluidStack){
        return "液体:"+fluidStack.getDisplayName().getString()+" 量:"+fluidStack.getAmount();
    }
    
}
