package com.xkball.flamereaction.capability.heat;

public interface SimpleHeatHandler extends IHeatHandler{
    
    
    @Override
    default void addToTemperature(int temperature){
        setHeat(Heat.upToTemp(getHeat(),temperature));
    };
    
    @Override
    default void addHeat(int amount){
    
    };
    
    @Override
    default HeatGap.GapKind getGapKind(){
        return HeatGap.GapKind.LINEAR;
    };
    
    @Override
    int maxChangeSpeed();
    
    @Override
    int getSpecificHeatCapacity();
}
