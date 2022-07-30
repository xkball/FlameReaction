package com.xkball.flamereaction.itemlike.block.blockentity;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.capability.fluid.SimpleFluidHandler;
import com.xkball.flamereaction.capability.item.SimpleItemStackHandler;
import com.xkball.flamereaction.crafting.ISingleToSingleRecipe;
import com.xkball.flamereaction.eventhandler.register.BlockEntityRegister;
import com.xkball.flamereaction.eventhandler.register.RecipeRegister;
import com.xkball.flamereaction.util.LevelUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class BrewingBarrelBlockEntity extends EasyChangedBlockEntity implements Container,ITargetBlockEntity {
    
    public static final String NAME = "brewing_barrel_block_entity";
    public static final float SPEED = 0.2F;
    public static final int CAPACITY = 3000;
    
    private NonNullList<ItemStack> item = NonNullList.withSize(1,ItemStack.EMPTY);
    private NonNullList<FluidStack> fluids = NonNullList.withSize(2,FluidStack.EMPTY);
    public int count = 0;
    
    public BrewingBarrelBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(BlockEntityRegister.BREWING_BARREL_BLOCK_ENTITY.get(), p_155229_, p_155230_);
    }
    
    public void checkRecipe(ItemStack itemStack){
        if(!itemStack.isEmpty() && !this.remove && this.level!=null){
            ISingleToSingleRecipe recipe = level.getRecipeManager().getRecipeFor(RecipeRegister.SINGLE_TO_SINGLE_TYPE.get(),this,level).orElse(null);
            if(recipe != null) {
                var resultFluid = recipe.getResultFluid();
                if(fluids.get(1).isEmpty() ){
                    
                    this.fluids.set(1,resultFluid);
                    item.get(0).shrink(1);
                }
            }
        }
    }
    
    public static void tick(Level level, BlockPos pos, BlockState state, BrewingBarrelBlockEntity entity) {
        entity.count++;
        if(entity.count >=5){
            entity.count = 0;
            entity.checkRecipe(entity.item.get(0));
            entity.produceFluid();
        }
    }
    
    public void produceFluid(){
        var buf = fluids.get(1);
        var f = fluids.get(0);
        if(f.isEmpty() && !buf.isEmpty()){
            fluids.set(0,LevelUtil.split(buf, (int) (SPEED*5)));
        }
        else if(!buf.isEmpty() && f.isFluidEqual(buf) && f.getAmount()<CAPACITY ){
            buf.grow((int) (-SPEED*5));
            f.grow((int) (SPEED*5));
        }
    }
    
    
    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return LazyOptional.of(() -> new SimpleItemStackHandler(){
                @Override
                public @NotNull ItemStack getStackInSlot(int slot) {
                    return slot == 0 ? item.get(0) : ItemStack.EMPTY;
                }
    
                @Override
                public void setStackInSlot(int slot, ItemStack stack) {
                    if(slot==0) item.set(0,stack);
                }
    
                @Override
                public void onContentsChanged(int slot) {
                    dirty();
                }
            }).cast();
        }
        if(cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
            return LazyOptional.of(()-> new SimpleFluidHandler(CAPACITY){
                @Override
                public @NotNull FluidStack getFluidInTank(int tank) {
                    return fluids.get(0);
                }
    
                @Override
                protected void setFluidInTank(int shot, FluidStack fluidStack) {
                    fluids.set(0,fluidStack);
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
        return FlameReaction.BREWING_BARREL;
    }
    
    @Override
    public void load(@Nonnull CompoundTag compoundTag) {
        super.load(compoundTag);
        this.item = NonNullList.withSize(1, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compoundTag,item);
        this.fluids = NonNullList.withSize(2,FluidStack.EMPTY);
        LevelUtil.loadAllFluids(compoundTag, fluids);
    }
    
    @Override
    protected void saveAdditional(@Nonnull CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        ContainerHelper.saveAllItems(compoundTag,item);
        LevelUtil.saveAllFluids(compoundTag, fluids,true);
    }
    
    @Nonnull
    @Override
    public CompoundTag getUpdateTag() {
        var compoundTag = super.getUpdateTag();
        ContainerHelper.saveAllItems(compoundTag,item);
        LevelUtil.saveAllFluids(compoundTag, fluids,true);
        return compoundTag;
    }
    
    @Override
    public void handleUpdateTag(CompoundTag compoundTag) {
        this.item = NonNullList.withSize(1, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compoundTag,item);
        this.fluids = NonNullList.withSize(2,FluidStack.EMPTY);
        LevelUtil.loadAllFluids(compoundTag, fluids);
    }
    
    @Override
    public int getContainerSize() {
        return 1;
    }
    
    @Override
    public boolean isEmpty() {
        return item.get(0).isEmpty();
    }
    
    @Override
    public @NotNull ItemStack getItem(int i) {
        return i == 0 ? item.get(0) : ItemStack.EMPTY;
    }
    
    @Override
    public @NotNull ItemStack removeItem(int i1, int i2) {
        dirty();
        return ContainerHelper.removeItem(this.item, i1, i2);
    }
    
    @Override
    public @NotNull ItemStack removeItemNoUpdate(int i) {
        return ContainerHelper.takeItem(this.item, i);
    }
    
    @Override
    public void setItem(int i, @NotNull ItemStack itemStack) {
        if(i==0)
        {
            item.set(0,itemStack);
            checkRecipe(itemStack);
        }
        dirty();
    }
    
    @Override
    public boolean stillValid(@NotNull Player player) {
        if (this.level !=null && this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        } else {
            return player.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) <= 64.0D;
        }
    }
    
    
    @Override
    public void clearContent() {
        this.item.clear();
    }
}
