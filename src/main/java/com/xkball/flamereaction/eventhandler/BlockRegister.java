package com.xkball.flamereaction.eventhandler;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.itemlike.block.BlockList;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockRegister {
    
    @SubscribeEvent
    public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
        FlameReaction.init();
        for(Block block : BlockList.block_instance.values()){
            blockRegistryEvent.getRegistry().register(block);
        }
    
    }
}
