package com.xkball.flamereaction.itemlike.block;

import net.minecraft.world.level.block.Block;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BlockList {
    public static Map<String, Block> block_instance = new HashMap<>();
    
    public static void addBlock(Block block){
        block_instance.put(Objects.requireNonNull(block.getRegistryName()).getPath(),block);
    }
    
}
