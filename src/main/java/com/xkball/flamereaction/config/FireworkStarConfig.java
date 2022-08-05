package com.xkball.flamereaction.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class FireworkStarConfig {
    
    public static ForgeConfigSpec CONFIG;
    public static ForgeConfigSpec.BooleanValue SP_FIREWORK_RECIPE;
    
    static {
        ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
        BUILDER.comment("烟火之星合成配置").push("general");
        SP_FIREWORK_RECIPE = BUILDER.comment("是否使用焰色燃料进行合成").define("need flame dye",false);
        BUILDER.pop();
        CONFIG = BUILDER.build();
    }
    
}
