package com.xkball.flamereaction.eventhandler.register;


import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.itemlike.block.blockentityrender.DippingBlockBER;
import com.xkball.flamereaction.itemlike.block.blockentityrender.ExhibitBlockBER;
import com.xkball.flamereaction.itemlike.block.blockentityrender.ForgingTableBER;
import com.xkball.flamereaction.itemlike.block.blockentityrender.SolarReflectorBER;
import com.xkball.flamereaction.util.BlockList;
import com.xkball.flamereaction.util.LevelUtil;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RenderRegistry {
    @SubscribeEvent
    public static void onRenderSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(BlockList.block_instance.get("iron_scaffolding_block"), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(BlockList.block_instance.get("exhibit_block"), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(FlameReaction.FLAME_FIRE_BLOCK,RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(FluidRegister.IMPURE_ALCOHOL_FLUID.get(),RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(FluidRegister.IMPURE_ALCOHOL_FLUID_FLOWING.get(),RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(FlameReaction.ALCOHOL_LAMP,RenderType.translucent());
            ItemProperties.register(FlameReaction.PLIERS,
                    new ResourceLocation(FlameReaction.MOD_ID,"has_item"),
                    //一有零无
                    ((pStack, pLevel, pEntity, pSeed) -> LevelUtil.hasBoolean(pStack,"has_item")?1:0));
        });
    }
    
    
    @SubscribeEvent
    public static void onRegisterRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(BlockEntityRegister.EXHIBIT_BLOCK_ENTITY.get(), ExhibitBlockBER::new);
        event.registerBlockEntityRenderer(BlockEntityRegister.FORGING_TABLE_BLOCK_ENTITY.get(), ForgingTableBER::new);
        event.registerBlockEntityRenderer(BlockEntityRegister.DIPPING_BLOCK_ENTITY.get(), DippingBlockBER::new);
        //event.registerBlockEntityRenderer(BlockEntityRegister.ALCOHOL_LAMP_BLOCK_ENTITY.get(), AlcoholLampBER::new);
        event.registerBlockEntityRenderer(BlockEntityRegister.SOLAR_REFLECTOR_BLOCK_ENTITY.get(), SolarReflectorBER::new);
    }
    
    
    
}
