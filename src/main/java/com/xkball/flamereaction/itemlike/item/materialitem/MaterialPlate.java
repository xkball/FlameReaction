package com.xkball.flamereaction.itemlike.item.materialitem;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.creativemodetab.CreativeModeTabs;
import com.xkball.flamereaction.part.material.IMaterial;
import com.xkball.flamereaction.util.MaterialType;
import net.minecraft.world.item.Item;

public class MaterialPlate extends MaterialItem{
    
    public MaterialPlate(IMaterial material){
        
        super((new Item.Properties()
                        .fireResistant()
                        .setNoRepair()
                        .tab(CreativeModeTabs.MATERIAL_GROUP)),
                material);
        this.setRegistryName(FlameReaction.MOD_ID,material.getName()+"_plate");
        add();
    }
    
    @Override
    public MaterialType getMaterialKind() {
        return MaterialType.PLATE;
    }
}
