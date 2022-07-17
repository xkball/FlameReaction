package com.xkball.flamereaction.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.PositionImpl;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.EmptyFluidHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import java.util.Random;
import java.util.function.Predicate;

public class LevelUtil {
    
    public static void addParticles(Level level, BlockPos pos, ParticleOptions particle){
        var random = new Random();
        for(int i=0;i<15;i++){
            var p1 = pos.getX()+0.5D+MathUtil.randomDoubleToPosOrNeg( random.nextDouble()*0.7);
            var p2 = pos.getY()-0.4D+MathUtil.randomDoubleToPosOrNeg(random.nextDouble()+0.2);
            var p3 = pos.getZ()+0.5D+MathUtil.randomDoubleToPosOrNeg(random.nextDouble()*0.7);
            level.addParticle(particle,p1,p2,p3,0,0,0);
        }
        
    }
    
    public static ItemStack itemHandlerInput(@Nonnull ItemStack in, IItemHandler iItemHandler){
        for(int i=0;i<iItemHandler.getSlots();i++){
            if(iItemHandler.isItemValid(i,in)) in = iItemHandler.insertItem(i,in,false);
            if(in.isEmpty()) return ItemStack.EMPTY;
        }
        return in;
    }
    
    public static void addItem(ServerLevel level,BlockPos pos,ItemStack itemStack){
        DefaultDispenseItemBehavior.spawnItem(level,itemStack,1, Direction.UP, new PositionImpl(pos.getX(),pos.getY()+0.5,pos.getZ()));
    }
    
    public static void containerInput(ServerLevel level,BlockPos pos,Container container, ItemStack leftHandItem, ItemStack rightHandItem, Predicate<ItemStack> predicate){
        //存入
        //左手
        if(!leftHandItem.isEmpty()&&predicate.test(leftHandItem)){
            var buf = leftHandItem.getCount();
            var result = HopperBlockEntity.addItem(null,container,leftHandItem.copy(),null);
            leftHandItem.shrink(buf- result.getCount());
            return;
        }
        //右手
        if(!rightHandItem.isEmpty()&&predicate.test(rightHandItem)){
            var buf = rightHandItem.getCount();
            var result = HopperBlockEntity.addItem(null,container,rightHandItem.copy(),null);
            rightHandItem.shrink(buf - result.getCount());
            return;
        }
        
        //取出
        if(!container.isEmpty()&&rightHandItem.isEmpty()){
            for(int i = container.getContainerSize()-1;i>=0;i--){
                var item = container.getItem(i);
                if(!item.isEmpty()){
                    addItem(level,pos,item);
                    container.setItem(i,ItemStack.EMPTY);
                    break;
                }
            }
        }
        
        if(!predicate.test(leftHandItem)){
            addItem(level,pos,leftHandItem.split(leftHandItem.getCount()));
            return;
        }
        if(!predicate.test(rightHandItem)){
            addItem(level,pos,rightHandItem.split(rightHandItem.getCount()));
        }
        
    }
    
    
    public static CompoundTag saveAllFluids(CompoundTag tag, NonNullList<FluidStack> fluidStacks,boolean enforceSave){
        ListTag listtag = new ListTag();
    
        for(int i = 0; i < fluidStacks.size(); ++i) {
            var fluidStack = fluidStacks.get(i);
            if (!fluidStack.isEmpty()) {
                CompoundTag compoundtag = new CompoundTag();
                compoundtag.putByte("Slot", (byte)i);
                fluidStack.writeToNBT(compoundtag);
                listtag.add(compoundtag);
            }
        }
    
        if (!listtag.isEmpty() || enforceSave) {
            tag.put("Fluids", listtag);
        }
    
        return tag;
    }
    
    
    public static void loadAllFluids(CompoundTag tag, NonNullList<FluidStack> fluidStacks) {
        ListTag listtag = tag.getList("Fluids", 10);
        
        for(int i = 0; i < listtag.size(); ++i) {
            CompoundTag compoundtag = listtag.getCompound(i);
            int j = compoundtag.getByte("Slot") & 255;
            if (j >= 0 && j < fluidStacks.size()) {
                fluidStacks.set(j, FluidStack.loadFluidStackFromNBT(compoundtag));
            }
        }
        
    }
    
    public static FluidStack split(FluidStack source,int amount){
        int i = Math.min(amount, source.getAmount());
        var fluidStack = source.copy();
        fluidStack.setAmount(i);
        source.shrink(i);
        return fluidStack;
    }
    
    
    public static void fillBucket(ServerLevel level, BlockPos pos, Player player, ItemStack bucket){
        if(bucket.is(Items.BUCKET)){
            var blockEntity = level.getBlockEntity(pos);
            if(blockEntity != null) {
                var cap = blockEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).orElse(EmptyFluidHandler.INSTANCE);
                if(!(cap instanceof EmptyFluidHandler)){
                    var stimulate = cap.drain(1000, IFluidHandler.FluidAction.SIMULATE);
                    if(stimulate.getAmount() == 1000){
                        var result = cap.drain(1000, IFluidHandler.FluidAction.EXECUTE);
                        var resultItem = new ItemStack(result.getFluid().getBucket(),1);
                        bucket.shrink(1);
                        SoundEvent soundevent = result.getFluid().getAttributes().getFillSound();
                        player.level.playSound(null, player.getX(), player.getY() + 0.5, player.getZ(), soundevent, SoundSource.BLOCKS, 1.0F, 1.0F);
                        if(!player.getInventory().add(resultItem)){
                            player.drop(resultItem,false);
                        }
                    }
                }
            }
        }
    }
}
