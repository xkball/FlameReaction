package com.xkball.flamereaction.itemlike.block.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class EasyChangedBlockEntity extends BlockEntity {
    
    public int getCoolDown() {
        return coolDown;
    }
    
    protected int coolDown = 10;
    
    
    public EasyChangedBlockEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }
    
    public void dirty(){
        this.setChanged();
        var state = this.getBlockState();
        Objects.requireNonNull(this.level).sendBlockUpdated(this.getBlockPos(), state, state, Block.UPDATE_ALL);
    }
    
    //发包
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
    
    @Override
    public void load(@NotNull CompoundTag compoundTag) {
        super.load(compoundTag);
        coolDown = compoundTag.getInt("cool");
    }
    
    @Override
    protected void saveAdditional(@NotNull CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        compoundTag.putInt("cool",coolDown);
    }
    
    @Override
    public void handleUpdateTag(CompoundTag compoundTag) {
        super.handleUpdateTag(compoundTag);
        compoundTag.putInt("cool",coolDown);
    }
    
    //区块载入发包？
    @Override
    public @NotNull CompoundTag getUpdateTag() {
        var compoundTag = super.getUpdateTag();
        this.saveWithoutMetadata();
        coolDown = compoundTag.getInt("cool");
        return compoundTag;
    }
    
    
    
}
