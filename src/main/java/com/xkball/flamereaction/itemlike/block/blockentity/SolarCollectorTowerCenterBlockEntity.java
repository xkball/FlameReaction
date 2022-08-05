package com.xkball.flamereaction.itemlike.block.blockentity;

import com.xkball.flamereaction.capability.heat.CapabilityHeatHandler;
import com.xkball.flamereaction.capability.heat.Heat;
import com.xkball.flamereaction.capability.heat.HeatGap;
import com.xkball.flamereaction.capability.heat.SimpleHeatHandler;
import com.xkball.flamereaction.eventhandler.register.BlockEntityRegister;
import com.xkball.flamereaction.util.LevelUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SolarCollectorTowerCenterBlockEntity extends EasyChangedBlockEntity{
    
    private int heatBuf;
    private Heat heat = Heat.defaultHeat();
    
    public SolarCollectorTowerCenterBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(BlockEntityRegister.SOLAR_COLLECTOR_TOWER_CENTER_BLOCK_ENTITY.get(), p_155229_, p_155230_);
    }
    
    public static void tick(Level level, BlockPos pos, BlockState state,SolarCollectorTowerCenterBlockEntity entity){
        //处理热量
        var heatH =entity.getCapability(CapabilityHeatHandler.HEAT_HANDLER_CAPABILITY).resolve();
        if(heatH.isPresent()){
            var heatHandler = heatH.get();
            heatHandler.addHeat(entity.heatBuf);
            entity.heatBuf = 0;
            entity.heat = HeatGap.tick(heatHandler);
            entity.dirty();
        }
    }
    
    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityHeatHandler.HEAT_HANDLER_CAPABILITY){
            return LazyOptional.of(() -> new SimpleHeatHandler() {
                @Override
                public int maxChangeSpeed() {
                    return 5000;
                }
    
                @Override
                public int getSpecificHeatCapacity() {
                    return 50;
                }
    
                @Override
                public Heat getHeat() {
                    return heat;
                }
    
                @Override
                public void setHeat(Heat heat1) {
                    heat = heat1;
                }
    
                @Override
                public boolean isValid(Direction direction) {
                    return true;
                }
    
                @Override
                public boolean haveFluid() {
                    return false;
                }
            }).cast();
        }
        return super.getCapability(cap, side);
    }
    
    public void addHeat(int i){
        this.heatBuf = heatBuf+i;
        dirty();
    }
    
    public Heat getHeatBuf(){
        return heat;
    }
    
    @Override
    public void load(@NotNull CompoundTag compoundTag) {
        super.load(compoundTag);
        heatBuf = compoundTag.getInt("heat_buf");
        heat = Heat.defaultHeat();
        LevelUtil.loadHeat(compoundTag,heat);
    }
    
    @Override
    protected void saveAdditional(@NotNull CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        compoundTag.putInt("heat_buf",heatBuf);
        LevelUtil.saveHeat(compoundTag,heat);
    }
    
    @Override
    public void handleUpdateTag(CompoundTag compoundTag) {
        super.handleUpdateTag(compoundTag);
        heatBuf = compoundTag.getInt("heat_buf");
        heat = Heat.defaultHeat();
        LevelUtil.loadHeat(compoundTag,heat);
    }
    
    @Override
    public @NotNull CompoundTag getUpdateTag() {
        var compoundTag =  super.getUpdateTag();
        compoundTag.putInt("heat_buf",heatBuf);
        return LevelUtil.saveHeat(compoundTag,heat);
    }
}
