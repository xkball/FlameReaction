package com.xkball.flamereaction.itemlike.block.materialblock;

import com.xkball.flamereaction.itemlike.block.FRCBlock;
import com.xkball.flamereaction.part.material.IMaterial;
import com.xkball.flamereaction.util.MaterialType;
import net.minecraft.world.level.block.Block;

public abstract class MaterialBlock extends Block implements FRCBlock {
    
    private final IMaterial material;
    public MaterialBlock(Block.Properties p_41383_, IMaterial material) {
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
