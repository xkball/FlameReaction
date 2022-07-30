package com.xkball.flamereaction.itemlike.block.blockentity.burningblockentity;

import com.xkball.flamereaction.capability.heat.CapabilityHeatHandler;
import com.xkball.flamereaction.itemlike.block.blockentity.EasyChangedBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractBurningBlockEntity extends EasyChangedBlockEntity {
    
    protected int timeLast = 0;
    protected int maxHeatProduce = 0;
    
    public AbstractBurningBlockEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }
    
    
    public static void serverTick(Level level, BlockPos pos, BlockState state, AbstractBurningBlockEntity entity) {
        if(!level.isClientSide){
            if(entity.updateFuel(state)){
                //entity.setTimeLast(entity.getTimeLast()-1);
                var entityUp = level.getBlockEntity(pos.above());
                if (entityUp != null) {
                    var cap = entityUp.getCapability(CapabilityHeatHandler.HEAT_HANDLER_CAPABILITY, Direction.DOWN);
                    cap.ifPresent((iHeatHandler -> {
                        if(iHeatHandler.isValid(Direction.DOWN)){
                            iHeatHandler.addHeat(entity.maxHeatProduce);
                        }
                    }));
                }
            }
            entity.setTimeLast(entity.getTimeLast()-1);
        }
    }
    
    //若有燃料返回true
    //没有燃料改变bs
    abstract public boolean updateFuel(BlockState bs);
    
    @Override
    public void load(@NotNull CompoundTag compoundTag) {
        super.load(compoundTag);
        timeLast = compoundTag.getInt("time_last");
        maxHeatProduce = compoundTag.getInt("max_heat_produce");
    }
    
    @Override
    protected void saveAdditional(@NotNull CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        compoundTag.putInt("time_last",timeLast);
        compoundTag.putInt("max_heat_produce",maxHeatProduce);
    }
    
    @Override
    public @NotNull CompoundTag getUpdateTag() {
        var compoundTag = super.getUpdateTag();
        compoundTag.putInt("time_last",timeLast);
        compoundTag.putInt("max_heat_produce",maxHeatProduce);
        return compoundTag;
    }
    
    @Override
    public void handleUpdateTag(CompoundTag compoundTag) {
        super.handleUpdateTag(compoundTag);
        timeLast = compoundTag.getInt("time_last");
        maxHeatProduce = compoundTag.getInt("max_heat_produce");
    }
    
    public int getMaxHeatProduce() {
        return maxHeatProduce;
    }
    
    public void setMaxHeatProduce(int maxHeatProduce) {
        this.maxHeatProduce = maxHeatProduce;
    }
    
    public int getTimeLast() {
        return timeLast;
    }
    
    public void setTimeLast(int timeLast) {
        this.timeLast = timeLast;
    }
}
