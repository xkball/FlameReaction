package com.xkball.flamereaction.capability.heat;

public class HeatGap {
    
    public enum GapKind{
        LINEAR
    }
    
    public static int getNextGap(Heat heat,int sp){
        var i = heat.getDegree();
        var g = standardLinearGap(i);
        return g*sp - heat.getHeatBuf();
    }
    
    public static int getForeGap(Heat heat,int sp){
        var i = heat.getDegree()-1;
        var g = standardLinearGap(i);
        return g*sp + heat.getHeatBuf();
    }
    
    public static Heat tick(IHeatHandler heatHandler){
        var heat = heatHandler.getHeat();
        var i = getTickChange(heat,heatHandler.getSpecificHeatCapacity());
        heatHandler.addHeat(i);
        return heatHandler.getHeat();
    }
    
    public static int getTickChange(Heat heat,int sp){
        var d = Math.abs(heat.getDegree()-300);
        return (int) (heat.getDegree()>300?-(d*sp*1.4*((d/100)*5+1)):(d*sp*2*((d/100)+1)));
    }
    
    public static int standardLinearGap(int i){
        return (i-300)*20+1;
    }
    
    public static int standardLinearGap(Heat heat){
        return standardLinearGap(heat.getDegree());
    }
}
