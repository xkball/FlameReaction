package com.xkball.flamereaction.itemlike.item.materialitem;

import com.xkball.flamereaction.itemlike.item.FRCItem;
import com.xkball.flamereaction.part.material.IMaterial;
import com.xkball.flamereaction.util.MaterialType;
import net.minecraft.world.item.Item;

public abstract class MaterialItem extends Item implements FRCItem {
    
    private final IMaterial material;
    public MaterialItem(Properties p_41383_,IMaterial material) {
        super(p_41383_);
        this.material = material;
    }
    
    public MaterialType getMaterialKind(){
        return MaterialType.UNKNOW;
    }
    
    public IMaterial getMaterial() {
        return material;
    }
}
