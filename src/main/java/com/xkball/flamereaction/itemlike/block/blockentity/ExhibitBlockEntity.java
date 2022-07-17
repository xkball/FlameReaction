package com.xkball.flamereaction.itemlike.block.blockentity;

import com.mojang.logging.LogUtils;
import com.xkball.flamereaction.eventhandler.register.BlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;


public class ExhibitBlockEntity extends EasyChangedBlockEntity {
    
    private int age = 0;
    
    private static final Logger LOGGER = LogUtils.getLogger();
    
    private NonNullList<ItemStack> item = NonNullList.withSize(1, ItemStack.EMPTY);
    private @Nullable Direction.Axis axis = null;
    private boolean isLikeItemEntity = false;
    private @Nullable String introduction = null;
    
    public ExhibitBlockEntity(BlockPos blockPos, BlockState blockState){
        super(BlockEntityRegister.EXHIBIT_BLOCK_ENTITY.get(),blockPos,blockState);
    
    }
    
    public void input(ItemStack itemStack,@Nullable Direction.Axis axis){
        
            this.item.set(0,itemStack);
            this.axis = axis;
            dirty();
            LOGGER.info("reset item:"+ this.item +"   at:"+ Objects.requireNonNull(this.getLevel()) +" "+ this.getBlockPos());
        
    }
    
    @Deprecated
    public void clearItem(){
        this.item.set(0,new ItemStack(Items.AIR));
        this.axis = null;
        dirty();
    }
    
    
    
    @Override
    public void load(@Nonnull CompoundTag compoundTag) {
        super.load(compoundTag);
        this.item = NonNullList.withSize(1, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compoundTag,item);
        if(compoundTag.contains("axis")) this.axis = Direction.Axis.byName(compoundTag.getString("axis"));
        this.isLikeItemEntity = compoundTag.getBoolean("isLikeItemEntity");
        if(compoundTag.contains("introduction")) this.introduction = compoundTag.getString("introduction");
    }
    
    @Override
    protected void saveAdditional(@Nonnull CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        if(axis != null) compoundTag.putString("axis",axis.getName());
        compoundTag.putBoolean("isLikeItemEntity",isLikeItemEntity);
        if(introduction != null) compoundTag.putString("introduction",introduction);
        ContainerHelper.saveAllItems(compoundTag,item);
        
    }
    
    //区块载入时调用？
    @Nonnull
    @Override
    public CompoundTag getUpdateTag() {
        var compound = super.getUpdateTag();
        if(axis != null) compound.putString("axis",axis.getName());
        ContainerHelper.saveAllItems(compound,item);
        compound.putBoolean("isLikeItemEntity",isLikeItemEntity);
        if(introduction != null) compound.putString("introduction",introduction);
        return compound;
    }
    
    @Override
    public void handleUpdateTag(CompoundTag compoundTag) {
        this.isLikeItemEntity = compoundTag.getBoolean("isLikeItemEntity");
        if(compoundTag.contains("introduction")) this.introduction = compoundTag.getString("introduction");
        this.item = NonNullList.withSize(1, ItemStack.EMPTY);
        ContainerHelper.loadAllItems(compoundTag,item);
        if(compoundTag.contains("axis")) this.axis = Direction.Axis.byName(compoundTag.getString("axis"));
    }
    
    public static void tick(Level level, BlockPos pos, BlockState state, ExhibitBlockEntity entity) {
        if(entity.age < 10000)entity.age++;
        else entity.age = 0;
        
    }
    
    
    @Nullable
    public Direction.Axis getAxis() {
        return axis;
    }
    
    public ItemStack getItem() {
        return item.get(0);
    }
    
    public boolean isLikeItemEntity() {
        return isLikeItemEntity;
    }
    
    public void setLikeItemEntity(boolean likeItemEntity) {
        isLikeItemEntity = likeItemEntity;
        dirty();
    }
    
    public @Nullable String getIntroduction() {
        return introduction;
    }
    
    public void setIntroduction(@Nullable String introduction) {
        this.introduction = introduction;
        dirty();
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
}
