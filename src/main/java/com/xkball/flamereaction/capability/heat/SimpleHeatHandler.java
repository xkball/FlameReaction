package com.xkball.flamereaction.capability.heat;

public interface SimpleHeatHandler extends IHeatHandler{
    
    
    @Override
    default void addToTemperature(int temperature){
        setHeat(Heat.upToTemp(getHeat(),temperature));
    };
    
    @Override
    default void addHeat(int amount){
        amount = amount > maxChangeSpeed() ? maxChangeSpeed() : amount;
        var heat = this.getHeat();
        var temp = heat.getDegree();
        var buf = heat.getHeatBuf()+amount;
    
        boolean f = true;
        if(amount >= 0) {
            while (f) {
                var i = HeatGap.getNextGap(heat);
                if (amount < i) {
                    f = false;
                }
                else {
                    amount = amount - i;
                    heat.up();
                    this.setHeat(heat);
                }
            }
        }
        else {
            while (f) {
                if(buf >= 0){
                    heat.setDegree(temp);
                    heat.setHeatBuf(buf);
                    this.setHeat(heat);
                    f = false;
                }
                else {
                    temp = temp-1;
                    heat.setDegree(temp);
                    buf = buf + HeatGap.getForeGap(heat);
                }
            }
        }
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
