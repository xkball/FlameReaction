package com.xkball.flamereaction.util;

public enum MaterialType {
    UNKNOW("unknow"),
    INGOT("ingot"),
    PLATE("plate"),
    BLOCK("block");
    
    private final String name;
    
    MaterialType(String name){
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}
