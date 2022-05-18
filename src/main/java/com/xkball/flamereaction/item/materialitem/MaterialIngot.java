package com.xkball.flamereaction.item.materialitem;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.item.ItemList;
import com.xkball.flamereaction.itemgroup.Groups;
import com.xkball.flamereaction.part.material.IMaterial;
import com.xkball.flamereaction.util.MaterialKind;



public class MaterialIngot extends MaterialItem{
    
    public MaterialIngot(IMaterial material) {
        super(new Properties()
                        .fireResistant()
                        .setNoRepair()
                        .tab(Groups.MATERIAL_GROUP),
                material);
        
        this.setRegistryName(FlameReaction.MOD_ID,material.getName()+"_ingot");
        ItemList.addItem(this);
    }
    
    public  MaterialKind getMaterialKind() {
        return MaterialKind.INGOT;
    }
}
