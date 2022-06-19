package com.xkball.flamereaction;

import com.mojang.logging.LogUtils;
import com.xkball.flamereaction.eventhandler.BlockEntityRegister;
import com.xkball.flamereaction.itemlike.block.blocktags.BlockTags;
import com.xkball.flamereaction.itemlike.block.commonblocks.FlameFireBlock;
import com.xkball.flamereaction.itemlike.item.commonitem.ExhibitBlockKey;
import com.xkball.flamereaction.itemlike.item.commonitem.FlameDyeItem;
import com.xkball.flamereaction.itemlike.item.commonitem.UniversalSaddle;
import com.xkball.flamereaction.itemlike.item.itemtags.ItemTags;
import com.xkball.flamereaction.itemlike.item.materialitem.ColoredFlameItem;
import com.xkball.flamereaction.part.material.FlammableChemicalMaterials;
import com.xkball.flamereaction.util.BlockList;
import com.xkball.flamereaction.itemlike.block.commonblocks.ExhibitBlock;
import com.xkball.flamereaction.itemlike.block.materialblock.MaterialBlocks;
import com.xkball.flamereaction.itemlike.block.materialblock.MetalScaffoldingBlock;
import com.xkball.flamereaction.util.ItemList;
import com.xkball.flamereaction.itemlike.item.materialitem.MaterialIngot;
import com.xkball.flamereaction.itemlike.item.materialitem.MaterialPlate;
import com.xkball.flamereaction.part.material.BasicMaterial;
import com.xkball.flamereaction.part.material.IMaterial;
import com.xkball.flamereaction.util.PeriodicTableOfElements;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.Objects;


@Mod(FlameReaction.MOD_ID)
public class FlameReaction
{
    public static final ItemStack AIR = new ItemStack(Items.AIR);
    
    private static final Logger LOGGER = LogUtils.getLogger();
    
    public static final String MOD_ID = "flamereaction";

    public FlameReaction() {
        MinecraftForge.EVENT_BUS.register(this);
        //BlockRegister.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BlockEntityRegister.BLOCK_ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
    
    public static String getItemName(Item item){
        return Objects.requireNonNull(item.getRegistryName()).getPath();
    }
    
    public static  FlameFireBlock FLAME_FIRE_BLOCK;
    public static UniversalSaddle UNIVERSAL_SADDLE;
    
    public static void init(){
        BasicMaterial.loadList();
        ItemTags.init();
        BlockTags.init();
        if(ItemList.item_instance.isEmpty() && BlockList.block_instance.isEmpty()){
            for(IMaterial material : BasicMaterial.commonMaterials){
                if(material != PeriodicTableOfElements.Cu && material != PeriodicTableOfElements.Fe){
                    new MaterialIngot(material);
                    new MaterialBlocks(material);
                }
                new MaterialPlate(material);
                
            }
            new MetalScaffoldingBlock(PeriodicTableOfElements.Fe);
            new ExhibitBlock();
            new ExhibitBlockKey();
            for(FlammableChemicalMaterials materials : FlammableChemicalMaterials.values()){
                new ColoredFlameItem(materials,materials.getName());
            }
            FLAME_FIRE_BLOCK = new FlameFireBlock();
            UNIVERSAL_SADDLE = new UniversalSaddle();
            
            for(DyeColor dyeColor : DyeColor.values()){
                new FlameDyeItem(dyeColor.getName(), dyeColor);
            }
        }
    }
}
