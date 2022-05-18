package com.xkball.flamereaction.item.materialitem;

import com.xkball.flamereaction.part.material.IMaterial;
import com.xkball.flamereaction.util.MaterialKind;
import net.minecraft.world.item.Item;
public abstract class MaterialItem extends Item {
    
    private final IMaterial material;
    public MaterialItem(Properties p_41383_,IMaterial material) {
        super(p_41383_);
        this.material = material;
    }
    
    public abstract MaterialKind getMaterialKind();
    
    public IMaterial getMaterial() {
        return material;
    }
}
