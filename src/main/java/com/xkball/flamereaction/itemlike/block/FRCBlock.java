package com.xkball.flamereaction.itemlike.block;

import net.minecraft.world.level.block.Block;

public class FRCBlock extends Block {
    public FRCBlock(Properties p_49795_) {
        super(p_49795_);
    }
    
    public void add(){
        BlockList.addBlock(this);
    }
}
