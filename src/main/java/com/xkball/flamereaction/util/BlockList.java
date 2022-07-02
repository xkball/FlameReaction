package com.xkball.flamereaction.util;

import com.xkball.flamereaction.itemlike.block.materialblock.MaterialBlocks;
import net.minecraft.world.level.block.Block;

import java.util.*;

public class BlockList {
    public static final Map<String, Block> block_instance = new LinkedHashMap<>();
    public static final List<Block> material_block = new ArrayList<>();
    
    public static void addBlock(Block block){
        block_instance.put(Objects.requireNonNull(block.getRegistryName()).getPath(),block);
        if(block instanceof MaterialBlocks) material_block.add(block);
    }
    
}
