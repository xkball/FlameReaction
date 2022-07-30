package com.xkball.flamereaction.itemlike.block.blockentity;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.capability.fluid.SimpleFluidHandler;
import com.xkball.flamereaction.capability.heat.*;
import com.xkball.flamereaction.capability.item.SimpleItemStackHandler;
import com.xkball.flamereaction.crafting.GlassCraftingRecipe;
import com.xkball.flamereaction.crafting.util.IntListContainer;
import com.xkball.flamereaction.eventhandler.register.BlockEntityRegister;
import com.xkball.flamereaction.eventhandler.register.RecipeRegister;
import com.xkball.flamereaction.util.LevelUtil;
import com.xkball.flamereaction.util.MathUtil;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DippingBlockEntity extends EasyChangedBlockEntity{
    
    public static final String NAME = "dipping_block_entity";
    
    private NonNullList<FluidStack> fluid = NonNullList.withSize(1,FluidStack.EMPTY);
    private NonNullList<ItemStack> items = NonNullList.withSize(2,ItemStack.EMPTY);
    private Heat heat = Heat.defaultHeat();
    private int timeCount = 0;
    
   
    public DippingBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(BlockEntityRegister.DIPPING_BLOCK_ENTITY.get(), p_155229_, p_155230_);
    }
    
    public static void clientTick(Level level, BlockPos pos, BlockState state, DippingBlockEntity entity){
        var target = entity.items.get(0);
        var fluid = entity.fluid.get(0);
        //todo 各种粒子效果
        //todo 水
        //todo 岩浆
        
        //合成时的效果
        entity.timeCount++;
        if(entity.timeCount>=10) {
            entity.timeCount=0;
            
           if(LevelUtil.hasBoolean(target,"has_item")){
               var particles = ParticleTypes.SMOKE;
               LevelUtil.addParticles(level,pos,particles);
           }
        
        }
        
        
    }
    public static void tick(Level level, BlockPos pos, BlockState state, DippingBlockEntity entity) {
        entity.timeCount++;
        entity.coolDown--;
        var heatH =entity.getCapability(CapabilityHeatHandler.HEAT_HANDLER_CAPABILITY).resolve();
        if(heatH.isPresent()){
            var heatHandler = heatH.get();
            entity.heat = HeatGap.tick(heatHandler);
            entity.dirty();
        }
        
        if(entity.timeCount>=10) {
            entity.timeCount=0;
            var target = entity.items.get(0);
            var fluid = entity.fluid.get(0);
            //检查合成
            //一为有物品
            if (!target.isEmpty() && target.getTag() != null && LevelUtil.hasBoolean(target,"has_item")) {
                var intList = target.getTag().getIntArray("list");
                var recipe = level.getRecipeManager().getAllRecipesFor(RecipeRegister.GLASS_CRAFTING_TYPE.get());
                for (GlassCraftingRecipe recipe1 : recipe) {
                    if (recipe1.matches(new IntListContainer() {
                        @Override
                        public IntList getIntList() {
                            return IntArrayList.wrap(intList);
                        }
            
                        @Override
                        public Block getTarget() {
                            return FlameReaction.FLAME_FIRE_BLOCK;
                        }
                    }, level)) {
                        if(MathUtil.randomBoolean(6) && fluid.getFluid() == Fluids.WATER && !fluid.isEmpty()){
                            LevelUtil.addBooleanTagToItem(target,"has_item",false);
                            entity.items.set(1,recipe1.getResultItem());
                            fluid.shrink(20);
                        }
                        
                    }
                }
            }
            
            entity.dirty();
        }
        //热量计算
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
    
                @Override
                public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                    return slot == 0 && items.get(0).isEmpty();
                }
    
                @Override
                public int getSlots() {
                    return 2;
                }
    
                @Override
                public void onContentsChanged(int slot) {
                    dirty();
                    coolDown = 10;
                }
            }).cast();
        }
        if(cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
            return LazyOptional.of(() -> new SimpleFluidHandler(3000) {
                
                @Override
                public @NotNull FluidStack getFluidInTank(int tank) {
                    return fluid.get(0);
                }
    
                @Override
                public void onChanged() {
                    dirty();
                    coolDown = 10;
                }
    
                @Override
                protected void setFluidInTank(int shot,FluidStack fluidStack) {
                    fluid.set(0,fluidStack);
                }
            }).cast();
        }
        if(cap == CapabilityHeatHandler.HEAT_HANDLER_CAPABILITY){
            return LazyOptional.of(() -> new SimpleHeatHandler() {
                @Override
                public int maxChangeSpeed() {
                    return 400;
                }
    
                @Override
                public int getSpecificHeatCapacity() {
                    return 1+fluid.get(0).getAmount()/1000;
                }
    
                @Override
                public Heat getHeat() {
                    return heat;
                }
    
                @Override
                public void setHeat(Heat nHeat) {
                    heat = nHeat;
                }
    
                @Override
                public boolean isValid(Direction direction) {
                    return direction == Direction.UP;
                }
    
                @Override
                public boolean haveFluid() {
                    return true;
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
        heat = Heat.defaultHeat();
        
        ContainerHelper.loadAllItems(compoundTag,items);
        LevelUtil.loadAllFluids(compoundTag,fluid);
        LevelUtil.loadHeat(compoundTag,heat);
    }
    
    @Override
    protected void saveAdditional(@NotNull CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        ContainerHelper.saveAllItems(compoundTag,items,true);
        LevelUtil.saveAllFluids(compoundTag,fluid,true);
        LevelUtil.saveHeat(compoundTag,heat);
        
    }
    
    @Override
    public @NotNull CompoundTag getUpdateTag() {
        var compoundTag =  super.getUpdateTag();
        ContainerHelper.saveAllItems(compoundTag,items,true);
        LevelUtil.saveHeat(compoundTag,heat);
        
        return LevelUtil.saveAllFluids(compoundTag,fluid,true);
    }
    
    @Override
    public void handleUpdateTag(CompoundTag compoundTag) {
        super.handleUpdateTag(compoundTag);
        fluid = NonNullList.withSize(1,FluidStack.EMPTY);
        items = NonNullList.withSize(2,ItemStack.EMPTY);
        heat = Heat.defaultHeat();
        
        ContainerHelper.loadAllItems(compoundTag,items);
        LevelUtil.loadAllFluids(compoundTag,fluid);
        LevelUtil.loadHeat(compoundTag,heat);
    }
    
    public ItemStack getItem(){
        return items.get(0);
    }
    
    public FluidStack getFluid(){
        return fluid.get(0);
    }
    
    public Heat getHeat(){
        return heat.copy();
    }
}
