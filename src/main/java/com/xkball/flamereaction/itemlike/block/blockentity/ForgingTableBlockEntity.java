package com.xkball.flamereaction.itemlike.block.blockentity;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.eventhandler.register.BlockEntityRegister;
import com.xkball.flamereaction.eventhandler.register.RecipeRegister;
import com.xkball.flamereaction.itemlike.item.itemtags.ItemTags;
import com.xkball.flamereaction.util.LevelUtil;
import com.xkball.flamereaction.util.MathUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class ForgingTableBlockEntity extends EasyChangedBlockEntity implements ITargetBlockEntity, WorldlyContainer {
    public static final String NAME = "forging_table_block_entity";
    private static final Random random = new Random();
    
    public NonNullList<ItemStack> item = NonNullList.withSize(1,ItemStack.EMPTY);
    private float degreeBuf = random.nextFloat()*360;
    
    public ForgingTableBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlockEntityRegister.FORGING_TABLE_BLOCK_ENTITY.get(), blockPos, blockState);
    }
    
    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(side == Direction.UP &&cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return LazyOptional.of(() -> new ItemStackHandler(){
                @NotNull
                @Override
                public ItemStack extractItem(int slot, int amount, boolean simulate) {
                    return ItemStack.EMPTY;
                }
    
                @Override
                protected void onContentsChanged(int slot) {
                    if(slot == 0) item = stacks;
                }
            }).cast();
        }
        return super.getCapability(cap, side);
    }
    
    public boolean tryDoRecipe(ItemStack itemStack, ServerLevel level, BlockPos pos, Player player){
        if(itemStack.is(ItemTags.HAMMER) && MathUtil.randomBoolean(3)){
            Recipe<?> recipe = level.getRecipeManager().getRecipeFor(RecipeRegister.SINGLE_TO_SINGLE_TYPE.get(),this,level).orElse(null);
            if(recipe != null){
                itemStack.hurtAndBreak(1,player,(player1 -> player1.broadcastBreakEvent(InteractionHand.MAIN_HAND)));
                AtomicReference<ItemStack> result = new AtomicReference<>(recipe.getResultItem());
                var pos1 = pos.mutable().move(Direction.DOWN);
                var entity = level.getBlockEntity(pos1);
                if(entity != null){
                    var cap = getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,Direction.UP);
                    cap.ifPresent((iItemHandler -> result.set(LevelUtil.itemHandlerInput(result.get(), iItemHandler))));
                    this.setItem(0,ItemStack.EMPTY);
                    return true;
                }
                if(!result.get().isEmpty()){
                    LevelUtil.addItem(level,pos,result.get());
                    this.setItem(0,ItemStack.EMPTY);
                }
                dirty();
            }
            return true;
        }
        return false;
    }
    
    @Override
    public void load(@NotNull CompoundTag compoundTag) {
        super.load(compoundTag);
        item = NonNullList.withSize(1,ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compoundTag,item);
        degreeBuf = compoundTag.getFloat("degree_buf");
    }
    
    @Override
    protected void saveAdditional(@NotNull CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        ContainerHelper.saveAllItems(compoundTag,item);
        compoundTag.putFloat("degree_buf",degreeBuf);
    }
    
    @Override
    public @NotNull CompoundTag getUpdateTag() {
        var compound = super.getUpdateTag();
        ContainerHelper.saveAllItems(compound,item);
        compound.putFloat("degree_buf",degreeBuf);
        return compound;
    }
    
    @Override
    public void handleUpdateTag(CompoundTag compoundTag) {
        super.handleUpdateTag(compoundTag);
        item = NonNullList.withSize(1,ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compoundTag,item);
        degreeBuf = compoundTag.getFloat("degree_buf");
    }
    
    @Override
    public Block getTarget() {
        return FlameReaction.FORGING_TABLE;
    }
    
    @Override
    public int getContainerSize() {
        return 1;
    }
    
    @Override
    public boolean isEmpty() {
        return item.get(0).isEmpty();
    }
    
    public boolean hasItem(){
        return item.get(0).isEmpty();
    }
    
    @Override
    public @NotNull ItemStack getItem(int i) {
        return i==0?item.get(0):ItemStack.EMPTY;
    }
    
    @Override
    public @NotNull ItemStack removeItem(int i1, int i2) {
        dirty();
        return ContainerHelper.removeItem(this.item, i1, i2);
    }
    
    @Override
    public @NotNull ItemStack removeItemNoUpdate(int p_18951_) {
        return ContainerHelper.takeItem(this.item, p_18951_);
    }
    
    @Override
    public void setItem(int i, @NotNull ItemStack itemStack) {
        if(i==0) this.item.set(0,itemStack);
        degreeBuf = random.nextFloat()*360;
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
    
    public float getDegree(){
        return degreeBuf;
    }
    
    @Override
    public void clearContent() {
        this.item.clear();
    }
    
    @Override
    public int @NotNull [] getSlotsForFace(@NotNull Direction direction) {
        return direction == Direction.UP?new int[]{0}:new int[]{1};
    }
    
    @Override
    public boolean canPlaceItemThroughFace(int p_19235_, @NotNull ItemStack p_19236_, @Nullable Direction direction) {
        return direction == Direction.UP;
    }
    
    @Override
    public boolean canTakeItemThroughFace(int p_19239_, @NotNull ItemStack p_19240_, @NotNull Direction p_19241_) {
        return false;
    }
}
