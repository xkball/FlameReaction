package com.xkball.flamereaction.part.material;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.itemgroup.Groups;
import com.xkball.flamereaction.util.MaterialKind;
import com.xkball.flamereaction.util.MaterialProperty;
import com.xkball.flamereaction.util.PeriodicTableOfElements;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;
import java.util.*;
import java.util.List;


public class BasicMaterial {
    public static List<IMaterial> commonMaterials = new ArrayList<>();
   // public static Map<String,Item> item_instance = new HashMap<>();
    
    
    public static void loadList(){
        if(commonMaterials.size() == 0){
            commonMaterials.add(MaterialProperty.create(PeriodicTableOfElements.Rainbow));
            //是硼酸产生的
            commonMaterials.add(MaterialProperty.create(PeriodicTableOfElements.B,new Color(0,0,0)));
            commonMaterials.add(MaterialProperty.create(PeriodicTableOfElements.Li,new Color(0,0,0)));
            commonMaterials.add(MaterialProperty.create(PeriodicTableOfElements.Na,new Color(0,0,0)));
            commonMaterials.add(MaterialProperty.create(PeriodicTableOfElements.Ca,new Color(0,0,0)));
            commonMaterials.add(MaterialProperty.create(PeriodicTableOfElements.Ba,new Color(255,255,0)));
            commonMaterials.add(MaterialProperty.create(PeriodicTableOfElements.Cs,new Color(0,0,0)));
            commonMaterials.add(MaterialProperty.create(PeriodicTableOfElements.Cu,new Color(0,0,0)));
            commonMaterials.add(MaterialProperty.create(PeriodicTableOfElements.Fe,new Color(0,40,0)));
            commonMaterials.add(MaterialProperty.create(PeriodicTableOfElements.In,new Color(0,0,0)));
            commonMaterials.add(MaterialProperty.create(PeriodicTableOfElements.K,new Color(0,0,0)));
            commonMaterials.add(MaterialProperty.create(PeriodicTableOfElements.Mn,new Color(0,0,0)));
            commonMaterials.add(MaterialProperty.create(PeriodicTableOfElements.Mo,new Color(0,0,0)));
            commonMaterials.add(MaterialProperty.create(PeriodicTableOfElements.Pd,new Color(0,0,0)));
            commonMaterials.add(MaterialProperty.create(PeriodicTableOfElements.Rb,new Color(0,0,0)));
            commonMaterials.add(MaterialProperty.create(PeriodicTableOfElements.Sb,new Color(0,0,0)));
            commonMaterials.add(MaterialProperty.create(PeriodicTableOfElements.Sr,new Color(0,0,0)));
            commonMaterials.add(MaterialProperty.create(PeriodicTableOfElements.Tl,new Color(0,0,0)));
            commonMaterials.add(MaterialProperty.create(PeriodicTableOfElements.Zn,new Color(0,0,0)));
        }
        
    }
    
   
    
    
    
    
    
    
}
