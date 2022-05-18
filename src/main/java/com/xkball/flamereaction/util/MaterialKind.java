package com.xkball.flamereaction.util;

import org.lwjgl.system.CallbackI;

public enum MaterialKind {
    UNKNOW("unknow"),
    INGOT("ingot"),
    PLATE("plate");
    
    private final String name;
    
    MaterialKind(String name){
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}
