package com.xkball.flamereaction.eventhandler.register;

import com.mojang.datafixers.DSL;
import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.itemlike.block.blockentity.*;
import com.xkball.flamereaction.itemlike.block.blockentity.burningblockentity.AlcoholLampBlockEntity;
import com.xkball.flamereaction.itemlike.block.blockentity.burningblockentity.SolidFuelBurningBoxBlockEntity;
import com.xkball.flamereaction.itemlike.block.commonblocks.ExhibitBlock;
import com.xkball.flamereaction.itemlike.block.commonblocks.burningblock.SolidFuelBurningBox;
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
    
    public static final RegistryObject<BlockEntityType<ForgingTableBlockEntity>> FORGING_TABLE_BLOCK_ENTITY=
            BLOCK_ENTITY_TYPES.register(ForgingTableBlockEntity.NAME,
                    () -> BlockEntityType.Builder.of(ForgingTableBlockEntity::new, FlameReaction.FORGING_TABLE)
                            .build(DSL.remainderType()));
    
    public static final RegistryObject<BlockEntityType<BrewingBarrelBlockEntity>> BREWING_BARREL_BLOCK_ENTITY=
            BLOCK_ENTITY_TYPES.register(BrewingBarrelBlockEntity.NAME,
                    () -> BlockEntityType.Builder.of(BrewingBarrelBlockEntity::new, FlameReaction.BREWING_BARREL)
                            .build(DSL.remainderType()));
    
    public static final RegistryObject<BlockEntityType<AlcoholLampBlockEntity>> ALCOHOL_LAMP_BLOCK_ENTITY=
            BLOCK_ENTITY_TYPES.register(AlcoholLampBlockEntity.NAME,
                    () -> BlockEntityType.Builder.of(AlcoholLampBlockEntity::new, FlameReaction.ALCOHOL_LAMP)
                            .build(DSL.remainderType()));
    
    public static final RegistryObject<BlockEntityType<DippingBlockEntity>> DIPPING_BLOCK_ENTITY=
            BLOCK_ENTITY_TYPES.register(DippingBlockEntity.NAME,
                    () -> BlockEntityType.Builder.of(DippingBlockEntity::new, FlameReaction.DIPPINGBLOCK)
                            .build(DSL.remainderType()));
    
    public static final RegistryObject<BlockEntityType<SolidFuelBurningBoxBlockEntity>> SOLID_FUEL_BURNING_BOX_BLOCK_ENTITY=
            BLOCK_ENTITY_TYPES.register(SolidFuelBurningBox.NAME,
                    () -> BlockEntityType.Builder.of(SolidFuelBurningBoxBlockEntity::new, FlameReaction.SOLID_FUEL_BURNING_BOX)
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
