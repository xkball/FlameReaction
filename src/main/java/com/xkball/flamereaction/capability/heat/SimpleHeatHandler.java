package com.xkball.flamereaction.capability.heat;

public interface SimpleHeatHandler extends IHeatHandler{
    
    @Override
    default void addToTemperature(int temperature){
        setHeat(Heat.upToTemp(getHeat(),temperature));
    }
    
    @Override
    default void addHeat(int amount){
        amount = amount > maxChangeSpeed() ? maxChangeSpeed() : amount;
        var heat = this.getHeat();
        var temp = heat.getDegree();
        var buf = heat.getHeatBuf()+amount;
    
        boolean f = true;
        if(amount >= 0) {
            while (f) {
                var i = HeatGap.getNextGap(heat,getSpecificHeatCapacity());
                if (buf < i) {
                    f = false;
                    heat.setHeatBuf(buf);
                    this.setHeat(heat);
                }
                else {
                    buf = buf - i;
                    heat.up();
                    //this.setHeat(heat);
                }
            }
        }
        else {
            var w = 0;
            while (w<100) {
                if(buf >= 0){
                    heat.setDegree(temp);
                    heat.setHeatBuf(buf);
                    this.setHeat(heat);
                    break;
                }
                else {
                    temp = temp-1;
                    heat.setHeatBuf(0);
                    buf = buf + HeatGap.getForeGap(heat,getSpecificHeatCapacity());
                    heat.setDegree(temp);
                    w++;
                }
            }
        }
    }
    
    @Override
    default HeatGap.GapKind getGapKind(){
        return HeatGap.GapKind.LINEAR;
    }
    
    @Override
    int maxChangeSpeed();
    
    @Override
    int getSpecificHeatCapacity();
}
