package com.xkball.flamereaction.capability.heat;

public interface IHeatHandler {
    
    Heat getHeat();
    
    void setHeat(Heat heat);
    
    void addToTemperature(int temperature);
    
    void addHeat(int amount);
    
    int maxChangeSpeed();
    
    int getSpecificHeatCapacity();
    
    HeatGap.GapKind getGapKind();
    
    boolean haveFluid();
    
    default void tick(){
        spreadHeat();
        natureCost();
        
    }
    
    default void spreadHeat(){
    
    }
    
    default void natureCost(){
    
    }
}
