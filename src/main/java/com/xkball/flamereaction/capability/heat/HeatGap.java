package com.xkball.flamereaction.capability.heat;

public class HeatGap {
    
    public enum GapKind{
        LINEAR;
    }
    
    public static int getNextGap(Heat heat){
        var i = heat.getDegree();
        var g = standardLinearGap(i);
        return g - heat.getHeatBuf();
    }
    
    public static int getForeGap(Heat heat){
        var i = heat.getDegree()-1;
        var g = standardLinearGap(i);
        return g + heat.getHeatBuf();
    }
    
    
    public static int standardLinearGap(int i){
        return (i-300)*20;
    }
    
    public static int standardLinearGap(Heat heat){
        return standardLinearGap(heat.getDegree());
    }
}
