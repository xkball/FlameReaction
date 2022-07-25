package com.xkball.flamereaction.util.translateutil;

import net.minecraft.world.item.DyeColor;

//注意！本实现依赖原版DyeColor的顺序
public enum DyeColorTrans {
    
    
    WHITE("white","白" ),
    ORANGE( "orange","橙" ),
    MAGENTA("magenta","品红" ),
    LIGHT_BLUE("light_blue","亮蓝"),
    YELLOW("yellow","黄" ),
    LIME( "lime","亮橙" ),
    PINK("pink","粉"),
    GRAY( "gray","灰" ),
    LIGHT_GRAY( "light_gray","亮灰" ),
    CYAN( "cyan","青" ),
    PURPLE( "purple","紫" ),
    BLUE( "blue","绿"),
    BROWN( "brown","棕"),
    GREEN( "green","绿" ),
    RED( "red","红"),
    BLACK( "black","黑");
    
    public String getC() {
        return c;
    }
    
    public static String getTrans(DyeColor dyeColor){
        var i = dyeColor.getId();
        return DyeColorTrans.values()[i].getC();
    }
    
    final String c;
    final String e;
    
    DyeColorTrans(String e, String c) {
        this.e = e;
        this.c = c;
    }
}
