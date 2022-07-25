package com.xkball.flamereaction.itemlike.block;

import com.xkball.flamereaction.util.BlockList;
import com.xkball.flamereaction.util.translateutil.ChineseTranslatable;
import net.minecraft.world.level.block.Block;

public interface FRCBlock extends ChineseTranslatable {
    default void add(){
        BlockList.addBlock((Block) this);
    }
    
    default Block get(String name){
        return BlockList.block_instance.get(name);
    }
}
