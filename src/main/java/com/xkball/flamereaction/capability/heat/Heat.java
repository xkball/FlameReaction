package com.xkball.flamereaction.capability.heat;

import net.minecraft.nbt.CompoundTag;

public class Heat {
    
    public static final int STANDARD_TEMPERATURE = 300;
    
    private int degree;
    private int heatBuf;
    
    public Heat(int degree, int heatBuf) {
        this.degree = degree;
        this.heatBuf = heatBuf;
    }
    
    public static Heat defaultHeat(){
        return new Heat(STANDARD_TEMPERATURE,0);
    }
    
    public  void up(){
        this.degree = degree+1;
        this.heatBuf = 0;
    }
    
    public void down(int buf){
        if(degree>0) {
            this.degree = degree - 1;
            this.heatBuf = buf;
        }
    }
    
    public Heat copy(){
        return new Heat(this.degree,this.heatBuf);
    }
    
    public static Heat upToTemp(Heat heat, int temp){
        heat.setDegree(temp);
        return heat;
    }
    
    public CompoundTag save(CompoundTag tag){
        tag.putInt("heat_degree",degree);
        tag.putInt("heat_buf",heatBuf);
        return tag;
    }
    
    public static Heat load(CompoundTag tag){
        var degree = tag.getInt("heat_degree");
        var buf = tag.getInt("heat_buf");
        return new Heat(degree,buf);
    }
    
    public int getHeatBuf() {
        return heatBuf;
    }
    
    public void setHeatBuf(int heatBuf) {
        this.heatBuf = heatBuf;
    }
    
    public int getDegree() {
        return degree;
    }
    
    public void setDegree(int degree) {
        this.degree = degree;
    }
    
    @Override
    public String toString() {
        return "温度: "+degree+" 热量缓存: "+heatBuf;
    }
}
