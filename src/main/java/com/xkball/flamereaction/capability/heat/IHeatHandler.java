package com.xkball.flamereaction.capability.heat;

import net.minecraft.core.Direction;

public interface IHeatHandler {
    
    Heat getHeat();
    
    void setHeat(Heat heat);
    
    void addToTemperature(int temperature);
    
    void addHeat(int amount);
    
    boolean isValid(Direction direction);
    
    int maxChangeSpeed();
    
    //比热容
    int getSpecificHeatCapacity();
    
    HeatGap.GapKind getGapKind();
    
    boolean haveFluid();
}
