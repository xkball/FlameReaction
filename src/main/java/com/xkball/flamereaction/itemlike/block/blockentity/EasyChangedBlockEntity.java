package com.xkball.flamereaction.itemlike.block.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Objects;

public abstract class EasyChangedBlockEntity extends BlockEntity {
    
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
    //接受
    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        handleUpdateTag(Objects.requireNonNull(pkt.getTag()));
    }
    
}
