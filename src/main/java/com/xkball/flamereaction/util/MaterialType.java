package com.xkball.flamereaction.util;

public enum MaterialType {
    UNKNOW("unknow","未知"),
    INGOT("ingot","锭"),
    PLATE("plates","板"),
    BLOCK("block","块"),
    CHEMICAL("chemical","化学品"),
    STICK("stick","棍");
    
    private final String name;
    
    
    
    private final String chinese;
    
    MaterialType(String name,String chinese){
        this.name = name;
        this.chinese = chinese;
    }
    
    public String getName() {
        return name;
    }
    public String getChinese() {
        return chinese;
    }
}
