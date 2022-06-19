package com.xkball.flamereaction.part.material;

import com.xkball.flamereaction.util.MaterialProperty;
import com.xkball.flamereaction.util.PeriodicTableOfElements;

import java.awt.*;
import java.util.*;
import java.util.List;


public class BasicMaterial {
    public static final List<IMaterial> commonMaterials = new ArrayList<>();
   // public static Map<String,Item> item_instance = new HashMap<>();
    
    
    public static void loadList(){
        if(commonMaterials.size() == 0){
            commonMaterials.add(MaterialProperty.create(PeriodicTableOfElements.Rainbow));
            //是硼酸产生的
            commonMaterials.add(MaterialProperty.create(PeriodicTableOfElements.B,new Color(191,191,191)));
            commonMaterials.add(MaterialProperty.create(PeriodicTableOfElements.Li,new Color(156,153,177)));
            commonMaterials.add(MaterialProperty.create(PeriodicTableOfElements.Na,new Color(0,0,200)));
            commonMaterials.add(MaterialProperty.create(PeriodicTableOfElements.Ca,new Color(216,208,208)));
            commonMaterials.add(MaterialProperty.create(PeriodicTableOfElements.Ba,new Color(131,130,76)));
            commonMaterials.add(MaterialProperty.create(PeriodicTableOfElements.Cs,new Color(230,224,207)));
            commonMaterials.add(MaterialProperty.create(PeriodicTableOfElements.Cu,new Color(226,89,0)));
            commonMaterials.add(MaterialProperty.create(PeriodicTableOfElements.Fe,new Color(255,254,233)));
            commonMaterials.add(MaterialProperty.create(PeriodicTableOfElements.In,new Color(57,0,113)));
            commonMaterials.add(MaterialProperty.create(PeriodicTableOfElements.K,new Color(166,166,166)));
            commonMaterials.add(MaterialProperty.create(PeriodicTableOfElements.Mn,new Color(160,160,160)));
            commonMaterials.add(MaterialProperty.create(PeriodicTableOfElements.Mo,new Color(160,160,195)));
            commonMaterials.add(MaterialProperty.create(PeriodicTableOfElements.Pd,new Color(113,113,113)));
            commonMaterials.add(MaterialProperty.create(PeriodicTableOfElements.Rb,new Color(213,27,27)));
            commonMaterials.add(MaterialProperty.create(PeriodicTableOfElements.Sb,new Color(195,195,213)));
            commonMaterials.add(MaterialProperty.create(PeriodicTableOfElements.Sr,new Color(169,169,169)));
            //commonMaterials.add(MaterialProperty.create(PeriodicTableOfElements.Tl,new Color(216,216,216)));
            commonMaterials.add(MaterialProperty.create(PeriodicTableOfElements.Zn,new Color(212,203,203)));
            commonMaterials.add(MaterialProperty.create(PeriodicTableOfElements.Pt,new Color(199,206,158)));
        }
        
        
    }
    
   
    
    
    
    
    
    
}
