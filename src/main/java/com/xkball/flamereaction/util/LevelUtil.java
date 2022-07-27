package com.xkball.flamereaction.util;

import com.xkball.flamereaction.capability.heat.Heat;
import com.xkball.flamereaction.itemlike.block.blockentity.burningblockentity.AbstractBurningBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.PositionImpl;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.EmptyFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.EmptyHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

public class LevelUtil {
    
    public static void addParticles(Level level, BlockPos pos, ParticleOptions particle,int times){
        var random = new Random();
        for(int i=0;i<times;i++){
            var p1 = pos.getX()+0.5D+MathUtil.randomDoubleToPosOrNeg( random.nextDouble()*0.7);
            var p2 = pos.getY()-0.4D+MathUtil.randomDoubleToPosOrNeg(random.nextDouble()+0.2);
            var p3 = pos.getZ()+0.5D+MathUtil.randomDoubleToPosOrNeg(random.nextDouble()*0.7);
            level.addParticle(particle,p1,p2,p3,0,0,0);
        }
        
    }
    
    public static void addParticles(Level level, BlockPos pos, ParticleOptions particle){
        addParticles(level,pos,particle,15);
    }
    
    public static ItemStack itemHandlerInput(@Nonnull ItemStack in, IItemHandler iItemHandler){
        if(!(iItemHandler instanceof EmptyHandler)) {
            for (int i = 0; i < iItemHandler.getSlots(); i++) {
                
                if (iItemHandler.isItemValid(i, in)){
                    var s = iItemHandler.insertItem(i, in, true);
                    if(!s.isEmpty()){
                        in = iItemHandler.insertItem(i,in,false);
                    }
                }
                if (in.isEmpty()) return ItemStack.EMPTY;
            }
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
            if (j < fluidStacks.size()) {
                fluidStacks.set(j, FluidStack.loadFluidStackFromNBT(compoundtag));
            }
        }
        
    }
    
    public static CompoundTag saveHeat(CompoundTag compoundTag, Heat heat){
        compoundTag.putInt("heat_degree",heat.getDegree());
        compoundTag.putInt("heat_buf",heat.getHeatBuf());
        return compoundTag;
    }
    
    public static void loadHeat(CompoundTag compoundTag,Heat heat){
        heat.setDegree(compoundTag.getInt("heat_degree"));
        heat.setHeatBuf(compoundTag.getInt("heat_buf"));
    }
    
    
    public static FluidStack split(FluidStack source,int amount){
        int i = Math.min(amount, source.getAmount());
        var fluidStack = source.copy();
        fluidStack.setAmount(i);
        source.shrink(i);
        return fluidStack;
    }
    
    //startPos
    //true就顺向遍历获得物品
    public static boolean fillHand(ServerLevel level,BlockPos pos,Player player,ItemStack item,Direction side,boolean startPos){
        if(item.isEmpty()){
            var entity = level.getBlockEntity(pos);
            if (entity != null) {
                var cap = entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,side).orElse(EmptyHandler.INSTANCE);
                if(!(cap instanceof EmptyHandler)) {
                    var c = cap.getSlots();
                    for (int i = 0; i < c; i++) {
                        var s = startPos?i:c-i-1;
                        var ch = cap.insertItem(s,item,true);
                        if(!ch.isEmpty()) {
                            if(!player.getInventory().add(ch)){
                                player.drop(ch,false);
                            }
                            return true;
                        }
                    }
                }
            }
    
        }
        return false;
    }
    
    //与一个可以装物品与流体的方块交互
    public static boolean ioWithBlock(ServerLevel level,BlockPos pos,Player player, ItemStack item,Direction side){
        var entity = level.getBlockEntity(pos);
        var result = false;
        //取出流体
        if(emptyTank(level,pos,player,item,side)) result = true;
        //取出物品
        else if(fillHand(level,pos,player,item,side,false)) result = true;
        
        else if(fillTank(level,pos,player,item,side)) result = true;
        
         result = result || entity != null && itemHandlerInput(item, entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side).orElse(EmptyHandler.INSTANCE)).isEmpty();
    
        if(result && entity instanceof AbstractBurningBlockEntity entity1){
            entity1.dirty();
        }
        
        return result;
    }
    
    //布尔为操作结果，true为成功
    //把手上液体给罐子
    public static boolean fillTank(ServerLevel level,BlockPos pos,Player player, ItemStack item,Direction side){
        
        var b1 = FluidUtil.interactWithFluidHandler(player,player.getUsedItemHand(),level,pos,side);
        if(!b1) {
            //输出桶
            //暂不支持手持储罐，只有桶
            return LevelUtil.emptyBucket(level,pos,player,item,side);
        }
        return true;
    }
    
    public static boolean emptyTank(ServerLevel level,BlockPos pos,Player player, ItemStack item,Direction side){
        
        var b1 = FluidUtil.interactWithFluidHandler(player,player.getUsedItemHand(),level,pos,side);
        if(!b1){
                return LevelUtil.fillBucket(level, pos, player, item, side);
        }
        return true;
    }
    
    public static boolean emptyBucket(ServerLevel level,BlockPos pos,Player player, ItemStack item,Direction side){
        if(item.getItem() instanceof BucketItem bucket){
            var blockEntity = level.getBlockEntity(pos);
            if(blockEntity != null){
                AtomicBoolean flag = new AtomicBoolean(false);
                var cap = blockEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY,side);
                cap.ifPresent((iFluidHandler -> {
                    var fluid = new FluidStack(bucket.getFluid(),1000);
                    int c = iFluidHandler.getTanks();{
                        for(int i=0;i<c;i++){
                            if(iFluidHandler.isFluidValid(i,fluid)){
                                var s = iFluidHandler.fill(fluid, IFluidHandler.FluidAction.SIMULATE);
                                if(s == 1000){
                                    flag.set(true);
                                    break;
                                }
                            }
                        }
                    }
                }));
                if(flag.get()){
                    item.shrink(1);
                    var resultItem = new ItemStack(Items.BUCKET,1);
                    if(!player.getInventory().add(resultItem)){
                        player.drop(resultItem,false);
                    }
                }
                return flag.get();
            }
        }
        return false;
    }
    
    public static boolean fillBucket(ServerLevel level, BlockPos pos, Player player, ItemStack bucket,@Nullable Direction side){
        if(bucket.is(Items.BUCKET)){
            var blockEntity = level.getBlockEntity(pos);
            if(blockEntity != null) {
                var cap = blockEntity.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY,side).orElse(EmptyFluidHandler.INSTANCE);
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
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public static boolean hasBoolean(ItemStack itemStack,String s){
        if (itemStack.getTag() != null) {
            return itemStack.getTag().getInt(s) == 1;
        }
        return false;
    }
    
    public static void addBooleanTagToItem(ItemStack itemStack,String tagName,boolean b){
        itemStack.addTagElement(tagName, IntTag.valueOf(b?1:0));
    }
}
