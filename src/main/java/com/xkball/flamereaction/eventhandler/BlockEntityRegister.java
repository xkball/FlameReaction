package com.xkball.flamereaction.eventhandler;

import com.mojang.datafixers.DSL;
import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.itemlike.block.blockentity.ExhibitBlockEntity;
import com.xkball.flamereaction.itemlike.block.commonblocks.ExhibitBlock;
import com.xkball.flamereaction.util.BlockList;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


//@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockEntityRegister {
    
    
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, FlameReaction.MOD_ID);

    public static final RegistryObject<BlockEntityType<ExhibitBlockEntity>> EXHIBIT_BLOCK_ENTITY=
        BLOCK_ENTITY_TYPES.register("exhibit_block_entity",
            () -> BlockEntityType.Builder.of(ExhibitBlockEntity::new, BlockList.block_instance.get(ExhibitBlock.NAME))
                    .build(DSL.remainderType()));
    
//    public static final RegistryObject<BlockEntityType<FlameFireBlockEntity>> FLAME_FIRE_BLOCK_ENTITY=
//            BLOCK_ENTITY_TYPES.register("flame_fire_block_entity",
//                    () -> BlockEntityType.Builder.of(FlameFireBlockEntity::new, BlockList.block_instance.get(FlameFireBlock.NAME))
//                            .build(DSL.remainderType()));

    
//    @SubscribeEvent
//    public static void onRegisterBlockEntityType(RegistryEvent.Register<BlockEntityType<?>> event) {
//        event.getRegistry().register(BlockEntityType.Builder.of(ExhibitBlockEntity::new,
//                BlockList.block_instance.get(ExhibitBlock.NAME)).build(DSL.remainderType()).setRegistryName(ExhibitBlock.NAME));
//    }
}
