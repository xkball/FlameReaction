package com.xkball.flamereaction;

import com.mojang.logging.LogUtils;
import com.xkball.flamereaction.creativemodetab.CreativeModeTabs;
import com.xkball.flamereaction.eventhandler.register.BlockEntityRegister;
import com.xkball.flamereaction.eventhandler.register.FluidRegister;
import com.xkball.flamereaction.eventhandler.register.RecipeRegister;
import com.xkball.flamereaction.itemlike.block.blocktags.BlockTags;
import com.xkball.flamereaction.itemlike.block.commonblocks.*;
import com.xkball.flamereaction.itemlike.block.commonblocks.burningblock.AlcoholLamp;
import com.xkball.flamereaction.itemlike.block.commonblocks.burningblock.FluidFuelBurningBox;
import com.xkball.flamereaction.itemlike.block.commonblocks.burningblock.SolidFuelBurningBox;
import com.xkball.flamereaction.itemlike.item.commonitem.CommonItem;
import com.xkball.flamereaction.itemlike.item.commonitem.tool.ExhibitBlockKey;
import com.xkball.flamereaction.itemlike.item.commonitem.FlameDyeItem;
import com.xkball.flamereaction.itemlike.item.commonitem.tool.Hammer;
import com.xkball.flamereaction.itemlike.item.commonitem.UniversalSaddle;
import com.xkball.flamereaction.itemlike.item.commonitem.tool.Pliers;
import com.xkball.flamereaction.itemlike.item.commonitem.tool.Wrench;
import com.xkball.flamereaction.itemlike.item.itemtags.ItemTags;
import com.xkball.flamereaction.itemlike.item.materialitem.ColoredFlameItem;
import com.xkball.flamereaction.itemlike.item.materialitem.MaterialStick;
import com.xkball.flamereaction.network.NetworkHandler;
import com.xkball.flamereaction.part.material.FlammableChemicalMaterials;
import com.xkball.flamereaction.util.BlockList;
import com.xkball.flamereaction.itemlike.block.materialblock.MaterialBlocks;
import com.xkball.flamereaction.itemlike.block.materialblock.MetalScaffoldingBlock;
import com.xkball.flamereaction.util.ItemList;
import com.xkball.flamereaction.itemlike.item.materialitem.MaterialIngot;
import com.xkball.flamereaction.itemlike.item.materialitem.MaterialPlate;
import com.xkball.flamereaction.part.material.BasicMaterial;
import com.xkball.flamereaction.part.material.IMaterial;
import com.xkball.flamereaction.util.PeriodicTableOfElements;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;

import java.util.Objects;


@Mod(FlameReaction.MOD_ID)
public class FlameReaction
{
    public static final ItemStack AIR = new ItemStack(Items.AIR);
    
    private static final Logger LOGGER = LogUtils.getLogger();
    
    public static final String MOD_ID = "flamereaction";

    public FlameReaction() {
        
        LOGGER.info("flame_reaction initialization started");
        MinecraftForge.EVENT_BUS.register(this);
        var bus = FMLJavaModLoadingContext.get().getModEventBus();
        BlockEntityRegister.BLOCK_ENTITY_TYPES.register(bus);
        RecipeRegister.RECIPE.register(bus);
        RecipeRegister.SERIALIZER.register(bus);
        FluidRegister.FLUIDS.register(bus);
        FluidRegister.FLUID_BLOCKS.register(bus);
        FluidRegister.FLUID_BUCKETS.register(bus);
        //ForgeMod.enableMilkFluid();
        bus.addListener(this::setup);
    }
    
    //晚于注册？
    private void setup(final FMLCommonSetupEvent event)
    {
        IMPURE_ALCOHOL_FLUID_BLOCK = (UnstableFluidBlock) FluidRegister.IMPURE_ALCOHOL_FLUID_BLOCK.get();
        IMPURE_ALCOHOL_FLUID_BUCKET = (BucketItem) FluidRegister.IMPURE_ALCOHOL_FLUID_BUCKET.get();
        BlockList.addBlock(IMPURE_ALCOHOL_FLUID_BLOCK);
        //IMPURE_ALCOHOL_FLUID_BUCKET.setRegistryName(new ResourceLocation(FlameReaction.MOD_ID,UnstableFluidBlock.IMPURE_ALCOHOL_FLUID_NAME));
        ItemList.addItem(IMPURE_ALCOHOL_FLUID_BUCKET);
        event.enqueueWork(NetworkHandler::init);
    }
    
    public static String getItemName(Item item){
        return Objects.requireNonNull(item.getRegistryName()).getPath();
    }
    
    public static Block getBlock(ResourceLocation resourceLocation){
        return ForgeRegistries.BLOCKS.getValue(resourceLocation);
    }
    
    public static Item getItem(ResourceLocation resourceLocation){
        return ForgeRegistries.ITEMS.getValue(resourceLocation);
    }
    
    public static FlameFireBlock FLAME_FIRE_BLOCK;
    public static UniversalSaddle UNIVERSAL_SADDLE;
    public static ForgingTable FORGING_TABLE;
    public static BrewingBarrel BREWING_BARREL;
    public static UnstableFluidBlock IMPURE_ALCOHOL_FLUID_BLOCK;
    public static BucketItem IMPURE_ALCOHOL_FLUID_BUCKET;
    public static Wrench WRENCH;
    public static Pliers PLIERS;
    public static DippingBlock DIPPINGBLOCK;
    public static AlcoholLamp ALCOHOL_LAMP;
    public static CommonItem WROUGHT_IRON_INGOT;
    public static CommonItem WROUGHT_IRON_NUGGET;
    public static SolidFuelBurningBox SOLID_FUEL_BURNING_BOX;
    public static FluidFuelBurningBox FLUID_FUEL_BURNING_BOX;
    public static CommonItem ICON;
    
    public static void init(){
        BasicMaterial.loadList();
        ItemTags.init();
        BlockTags.init();
        if(ItemList.item_instance.isEmpty() && BlockList.block_instance.isEmpty()){
            for(IMaterial material : BasicMaterial.commonMaterials){
                if(material != PeriodicTableOfElements.Cu && material != PeriodicTableOfElements.Fe){
                    new MaterialIngot(material);
                }
            }
            for(IMaterial material : BasicMaterial.commonMaterials){
                if(material != PeriodicTableOfElements.Cu && material != PeriodicTableOfElements.Fe){
                    new MaterialBlocks(material);
                }
            }
            for(IMaterial material : BasicMaterial.commonMaterials){
                new MaterialPlate(material);
                new MaterialStick(material);
            }
            
            new MetalScaffoldingBlock(PeriodicTableOfElements.Fe);
            new ExhibitBlock();
            new ExhibitBlockKey();
            for(FlammableChemicalMaterials materials : FlammableChemicalMaterials.values()){
                new ColoredFlameItem(materials,materials.getName());
            }
            FLAME_FIRE_BLOCK = new FlameFireBlock();
            UNIVERSAL_SADDLE = new UniversalSaddle();
            FORGING_TABLE = new ForgingTable();
            BREWING_BARREL = new BrewingBarrel();
            WRENCH = new Wrench();
            PLIERS = new Pliers();
            DIPPINGBLOCK = new DippingBlock();
            ALCOHOL_LAMP = new AlcoholLamp();
            WROUGHT_IRON_INGOT = new CommonItem(CreativeModeTabs.MATERIAL_GROUP,CommonItem.WROUGHT_IRON_INGOT,"锻铁锭");
            WROUGHT_IRON_INGOT = new CommonItem(CreativeModeTabs.MATERIAL_GROUP,CommonItem.WROUGHT_IRON_NUGGET,"锻铁粒");
            SOLID_FUEL_BURNING_BOX = new SolidFuelBurningBox();
            FLUID_FUEL_BURNING_BOX = new FluidFuelBurningBox();
            ICON = new CommonItem(CreativeModeTabs.FLAME_REACTION_GROUP,CommonItem.ICON,"焰火");
            
            for(DyeColor dyeColor : DyeColor.values()){
                new FlameDyeItem(dyeColor.getName(), dyeColor);
            }
            
            new Hammer();
        }
    }
}
