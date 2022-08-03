package com.xkball.flamereaction.itemlike.block.blockentity;

import com.xkball.flamereaction.capability.heat.CapabilityHeatHandler;
import com.xkball.flamereaction.eventhandler.register.BlockEntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class SolarReflectorBlockEntity extends EasyChangedBlockEntity{
    
    private int x;
    private int y;
    private int z;
    
    public SolarReflectorBlockEntity( BlockPos p_155229_, BlockState p_155230_) {
        super(BlockEntityRegister.SOLAR_REFLECTOR_BLOCK_ENTITY.get(), p_155229_, p_155230_);
    }
    
    public static void tick(Level level, BlockPos pos, BlockState state, SolarReflectorBlockEntity entity){
        var pos1= new BlockPos(entity.x,entity.y,entity.z);
        var entity1 = level.getBlockEntity(pos1);
        if(entity1 instanceof SolarCollectorTowerCenterBlockEntity) {
            var light = level.getBrightness(LightLayer.SKY, pos);
            entity1.getCapability(CapabilityHeatHandler.HEAT_HANDLER_CAPABILITY).ifPresent(
                    heatHandler -> ((SolarCollectorTowerCenterBlockEntity) entity1).addHeat(light)
            );
            
        }
    }
    
    
    @Override
    public void load(@NotNull CompoundTag compoundTag) {
        super.load(compoundTag);
        x = compoundTag.getInt("px");
        y = compoundTag.getInt("py");
        z = compoundTag.getInt("pz");
    }
    
    @Override
    protected void saveAdditional(@NotNull CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        compoundTag.putInt("px",x);
        compoundTag.putInt("py",y);
        compoundTag.putInt("pz",z);
    }
    
    @Override
    public void handleUpdateTag(CompoundTag compoundTag) {
        super.handleUpdateTag(compoundTag);
        x = compoundTag.getInt("px");
        y = compoundTag.getInt("py");
        z = compoundTag.getInt("pz");
    }
    
    @Override
    public @NotNull CompoundTag getUpdateTag() {
        var compoundTag = super.getUpdateTag();
        compoundTag.putInt("px",x);
        compoundTag.putInt("py",y);
        compoundTag.putInt("pz",z);
        return compoundTag;
    }
    
    public int getPX() {
        return x;
    }
    
    private void setX(int x) {
        this.x = x;
    }
    
    public int getPY() {
        return y;
    }
    
    private void setY(int y) {
        this.y = y;
    }
    
    public int getPZ() {
        return z;
    }
    
    private void setZ(int z) {
        this.z = z;
    }
    
    public void setPos(int x,int y,int z){
        this.setX(x);
        this.setY(y);
        this.setZ(z);
        dirty();
    }
}
