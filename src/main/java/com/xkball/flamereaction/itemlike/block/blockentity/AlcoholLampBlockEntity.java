package com.xkball.flamereaction.itemlike.block.blockentity;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.eventhandler.register.BlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

public class AlcoholLampBlockEntity extends EasyChangedBlockEntity implements ITargetBlockEntity{
    
    public static final String NAME = "alcohol_lamp_block_entity";
    
    
    
    public AlcoholLampBlockEntity( BlockPos p_155229_, BlockState p_155230_) {
        super(BlockEntityRegister.ALCOHOL_LAMP_BLOCK_ENTITY.get(), p_155229_, p_155230_);
    }
    
    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        return super.getCapability(cap);
    }
    
    @Override
    public Block getTarget() {
        return FlameReaction.ALCOHOL_LAMP;
    }
}
