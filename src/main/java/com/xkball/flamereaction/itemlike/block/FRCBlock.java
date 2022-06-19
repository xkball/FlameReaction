package com.xkball.flamereaction.itemlike.block;

import com.xkball.flamereaction.util.BlockList;
import net.minecraft.world.level.block.Block;

public interface FRCBlock {
    default void add(){
        BlockList.addBlock((Block) this);
    }
}
