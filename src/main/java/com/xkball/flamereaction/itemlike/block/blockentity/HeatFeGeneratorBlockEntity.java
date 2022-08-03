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
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HeatFeGeneratorBlockEntity extends EasyChangedBlockEntity{
    
    private int heatBuf;
    private Heat heat = Heat.defaultHeat();
    private IEnergyStorage energy = new EnergyStorage(500000,5000){
        @Override
        public boolean canReceive() {
            return false;
        }
    
        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            int energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));
            if (!simulate)
                energy += energyReceived;
            return energyReceived;
        }
    };
    
    public HeatFeGeneratorBlockEntity( BlockPos p_155229_, BlockState p_155230_) {
        super(BlockEntityRegister.HEAT_FE_GENERATOR_BLOCK_ENTITY.get(), p_155229_, p_155230_);
    }
    
    
    public static void tick(Level level, BlockPos pos,@SuppressWarnings("unused") BlockState state, HeatFeGeneratorBlockEntity entity){
        var heatH =entity.getCapability(CapabilityHeatHandler.HEAT_HANDLER_CAPABILITY,Direction.DOWN).resolve();
        if(heatH.isPresent()){
            var heatHandler = heatH.get();
            entity.heat = HeatGap.tick(heatHandler);
            entity.dirty();
        }
        //抽热量
        var de = level.getBlockEntity(pos.below());
        if (de != null) {
            de.getCapability(CapabilityHeatHandler.HEAT_HANDLER_CAPABILITY,Direction.UP).ifPresent(
                    (heatHandler) -> {
                        var heat = heatHandler.getHeat();
                        if(heat.getDegree()>300) {
                            var sp = heatHandler.getSpecificHeatCapacity();
                            entity.setHeatBuf(Math.abs(HeatGap.getTickChange(heat, sp)));
                        }
                    }
            );
        }
        entity.getCapability(CapabilityHeatHandler.HEAT_HANDLER_CAPABILITY,Direction.DOWN).ifPresent(
                heatHandler -> {
                    var heat = heatHandler.getHeat();
                    if(heat.getDegree()>320) {
                        var sp = heatHandler.getSpecificHeatCapacity();
                        entity.setHeatBuf((entity.heatBuf) + Math.abs(HeatGap.getTickChange(heat, sp)));
                    }
                }
        );
        //加能量
        if(entity.heatBuf>0){
            entity.getCapability(CapabilityEnergy.ENERGY,Direction.UP).ifPresent(
                    iEnergyStorage -> {
                        iEnergyStorage.receiveEnergy(entity.heatBuf*3/4,false);
                        entity.heatBuf = 0;
                    }
            );
        }
        //传递能量
        var ue = level.getBlockEntity(pos.above());
        if(ue != null){
            var cap1 = entity.getCapability(CapabilityEnergy.ENERGY,Direction.UP).orElse(new EnergyStorage(1));
            var sd = Math.min(cap1.getEnergyStored(), 5000);
            ue.getCapability(CapabilityEnergy.ENERGY,Direction.DOWN).ifPresent(
                    iEnergyStorage -> {
                        if(iEnergyStorage.canReceive()){
                            var i = iEnergyStorage.receiveEnergy(sd,false);
                            cap1.extractEnergy(i,false);
                        }
                    }
            );
            
        }
        entity.heatBuf = 0;
    
    }
    
    
    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityEnergy.ENERGY && side == Direction.UP){
            return LazyOptional.of(() -> energy).cast();
        }
        if(cap == CapabilityHeatHandler.HEAT_HANDLER_CAPABILITY && side == Direction.DOWN){
            return LazyOptional.of(() -> new SimpleHeatHandler() {
                @Override
                public int maxChangeSpeed() {
                    return 5000;
                }
    
                @Override
                public int getSpecificHeatCapacity() {
                    return 1;
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
    
    
    public int getHeatBuf() {
        return heatBuf;
    }
    
    public void setHeatBuf(int heatBuf) {
        this.heatBuf = heatBuf;
    }
    
    
    public int getEX(){
        return this.getCapability(CapabilityEnergy.ENERGY,Direction.UP).orElse(new EnergyStorage(1)).getEnergyStored();
    }
    
}
