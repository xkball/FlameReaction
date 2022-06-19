package com.xkball.flamereaction.part.material;

public interface IMaterial {
    String getName();
    
    default String getSymbol(){
        return getName();
    }
}
