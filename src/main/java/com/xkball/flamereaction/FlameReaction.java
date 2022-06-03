package com.xkball.flamereaction;

import com.mojang.logging.LogUtils;
import com.xkball.flamereaction.itemlike.block.BlockList;
import com.xkball.flamereaction.itemlike.block.materialblock.MaterialBlocks;
import com.xkball.flamereaction.itemlike.block.materialblock.MetalScaffoldingBlock;
import com.xkball.flamereaction.itemlike.item.ItemList;
import com.xkball.flamereaction.itemlike.item.materialitem.MaterialIngot;
import com.xkball.flamereaction.itemlike.item.materialitem.MaterialPlate;
import com.xkball.flamereaction.part.material.BasicMaterial;
import com.xkball.flamereaction.part.material.IMaterial;
import com.xkball.flamereaction.util.PeriodicTableOfElements;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import java.util.Objects;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(FlameReaction.MOD_ID)
public class FlameReaction
{
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    
    public static final String MOD_ID = "flamereaction";

    public FlameReaction() {
//        // Register the setup method for modloading
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
//        // Register the enqueueIMC method for modloading
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
//        // Register the processIMC method for modloading
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

//    private void setup(final FMLCommonSetupEvent event) {
//        // some preinit code
//        //LOGGER.info("HELLO FROM PREINIT");
//        //LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
//
//    }
//
//    private void enqueueIMC(final InterModEnqueueEvent event) {
//        // Some example code to dispatch IMC to another mod
//        InterModComms.sendTo("flamereaction", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
//    }
//
//    private void processIMC(final InterModProcessEvent event) {
//        // Some example code to receive and process InterModComms from other mods
//        LOGGER.info("Got IMC {}", event.getIMCStream().
//                map(m->m.messageSupplier().get()).
//                collect(Collectors.toList()));
//    }
//
//    // You can use SubscribeEvent and let the Event Bus discover methods to call
//    @SubscribeEvent
//    public void onServerStarting(ServerStartingEvent event) {
//        // Do something when the server starts
//        LOGGER.info("HELLO from server starting");
//    }
//
//    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
//    // Event bus for receiving Registry Events)
//    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
//    public static class RegistryEvents {
//        @SubscribeEvent
//        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent)
//        {
//            // Register a new block here
//            LOGGER.info("HELLO from Register Block");
//        }
//    }
    
    public static RegistryObject<Item> getItem(ResourceLocation resourceLocation){
        return RegistryObject.create(resourceLocation, ForgeRegistries.ITEMS);
    }
    
    public static RegistryObject<Item> getItem(String name){
        return getItem(new ResourceLocation(FlameReaction.MOD_ID,name));
    }
    
    public static String getItemName(Item item){
        return Objects.requireNonNull(item.getRegistryName()).getPath();
    }
    
    public static void init(){
        BasicMaterial.loadList();
        if(ItemList.item_instance.isEmpty() && BlockList.block_instance.isEmpty()){
            for(IMaterial material : BasicMaterial.commonMaterials){
                if(material != PeriodicTableOfElements.Cu && material != PeriodicTableOfElements.Fe){
                    new MaterialIngot(material);
                    new MaterialBlocks(material);
                }
                new MaterialPlate(material);
                
            }
            new MetalScaffoldingBlock(PeriodicTableOfElements.Fe);
        }
    }
}
