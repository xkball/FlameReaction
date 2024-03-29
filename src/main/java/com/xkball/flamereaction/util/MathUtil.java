package com.xkball.flamereaction.util;

import java.util.Random;

public class MathUtil {
    
    public static final Random random = new Random();
    
    public static boolean randomBoolean(int tureRate){
        return random.nextInt(tureRate) == 0;
    }
    
    public static boolean randomBoolean(){
        return randomBoolean(1);
    }
    
    public static boolean randomBoolean(float trueRate){
        return random.nextFloat(1f)<=trueRate;
    }
    
    public static double randomDoubleToPosOrNeg(double f){
        return random.nextBoolean()?f:-f;
    }
}
