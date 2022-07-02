package com.xkball.flamereaction.eventhandler.register;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.util.BlockList;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockRegister {
    
    
//    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, FlameReaction.MOD_ID);
//    public static final RegistryObject<Block> EXHIBIT_BLOCK = BLOCKS.register(ExhibitBlock.NAME,ExhibitBlock::new);
    
    
    @SubscribeEvent
    public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
        FlameReaction.init();
        for(Block block : BlockList.block_instance.values()){
            blockRegistryEvent.getRegistry().register(block);
        }
    }
}
