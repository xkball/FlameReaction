package com.xkball.flamereaction.eventhandler;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.itemlike.block.blockentity.burningblockentity.AlcoholLampBlockEntity;
import com.xkball.flamereaction.itemlike.block.commonblocks.FlameFireBlock;
import com.xkball.flamereaction.itemlike.block.commonblocks.burningblock.AlcoholLamp;
import com.xkball.flamereaction.itemlike.item.commonitem.FlameDyeItem;
import com.xkball.flamereaction.itemlike.item.materialitem.ColoredFlameItem;
import com.xkball.flamereaction.part.material.FlammableChemicalMaterials;
import com.xkball.flamereaction.util.BlockList;
import com.xkball.flamereaction.itemlike.block.materialblock.MaterialBlock;
import com.xkball.flamereaction.util.ItemList;
import com.xkball.flamereaction.itemlike.item.materialitem.MaterialItem;
import com.xkball.flamereaction.util.MaterialProperty;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class ColorHandler {
    
    @SubscribeEvent
    public static void itemColorHandler(ColorHandlerEvent.Item event){
        for(Item item : ItemList.item_instance.values()){
            colorMaterialItem(item,event);
            colorItemBlock(item,event);
            colorChemicalItem(item,event);
            colorFlameDyeItem(item,event);
            if (item != null && item == FlameReaction.WROUGHT_IRON_NUGGET) {
                event.getItemColors().register((itemStack,iTintIndex) ->
                    new Color(169,152,152).getRGB()
                ,item);
            }
            if (item != null && item == FlameReaction.WROUGHT_IRON_INGOT) {
                event.getItemColors().register((itemStack,iTintIndex) ->
                                new Color(169,152,152).getRGB()
                        ,item);
            }
            if (item != null && item == FlameReaction.WROUGHT_IRON_STICK) {
                event.getItemColors().register((itemStack,iTintIndex) ->
                                new Color(169,152,152).getRGB()
                        ,item);
            }
        }
    }
    
    @SubscribeEvent
    public static void blockColorHandler(ColorHandlerEvent.Block event){
        for(Block block : BlockList.block_instance.values()){
            colorMaterialBlock(block,event);
        }
        colorFlameFireBlock(FlameReaction.FLAME_FIRE_BLOCK,event);
    }
    
    public static void colorItemBlock(Item item,ColorHandlerEvent.Item event){
        if(item instanceof BlockItem bi){
            if(bi.getBlock() instanceof MaterialBlock mb){
                var mic = MaterialProperty.getInstanceFromIMaterial(mb.getMaterial());
                if(mic != null && mic.getColor() != null){
                    event.getItemColors().register( (itemStack,iTintIndex) -> {
                        if(itemStack.getItem() == bi){
                            return mic.getColor().getRGB();
                        }
                        else{
                            return MaterialColor.NONE.col;
                        }
                    },bi);
                }
            }
        }
    }
    
    public static void colorMaterialItem(Item item,ColorHandlerEvent.Item event){
        if(item instanceof MaterialItem mi){
            var mic = MaterialProperty.getInstanceFromIMaterial(mi.getMaterial());
            if(mic != null && mic.getColor() != null){
                event.getItemColors().register( (itemStack,iTintIndex) -> {
                    if(itemStack.getItem() == mi){
                        return mic.getColor().getRGB();
                    }
                    else{
                        return MaterialColor.NONE.col;
                    }
                },mi);
            }
        
        }
    }
    
    public static void colorChemicalItem(Item item,ColorHandlerEvent.Item event){
        if(item instanceof ColoredFlameItem cfi){
            var mic = (FlammableChemicalMaterials)cfi.getMaterial();
            if(mic != null && mic.getColor() != null){
                event.getItemColors().register( (itemStack,iTintIndex) -> {
                    if(itemStack.getItem() == cfi){
                        return mic.getColor().getRGB();
                    }
                    else{
                        return MaterialColor.NONE.col;
                    }
                },cfi);
            }
        }
    }
    
    public static void colorFlameDyeItem(Item item,ColorHandlerEvent.Item event){
        if(item instanceof FlameDyeItem fdi){
            event.getItemColors().register( (itemStack,iTintIndex) -> fdi.getDyeColor().getMaterialColor().col
                    ,fdi);
        
        }
    }
    
    public static void colorMaterialBlock(Block block,ColorHandlerEvent.Block event){
        if(block instanceof MaterialBlock mb){
            var mic = MaterialProperty.getInstanceFromIMaterial(mb.getMaterial());
            if(mic != null && mic.getColor() != null){
                event.getBlockColors().register((blockState,batg,blockPos,tint) ->{
                            if(blockState.getBlock() == mb){
                                return mic.getColor().getRGB();
                            }
                            else {
                                return MaterialColor.NONE.col;
                            }
                        },
                        mb);
            }
        }
    }
    
    public static void colorFlameFireBlock(Block block,ColorHandlerEvent.Block event){
        if(block instanceof FlameFireBlock){
            event.getBlockColors().register(
                    (blockState,batg,blockPos,tint) -> blockState.getValue(FlameFireBlock.MATERIAL).getColor().getRGB(),
                    block);
        }
        if(block instanceof AlcoholLamp){
            event.getBlockColors().register((blockState,batg,blockPos,tint) ->
            {
                if(batg!=null && blockPos!= null){
                    var entity = batg.getBlockEntity(blockPos);
                    if( entity instanceof AlcoholLampBlockEntity alcoholLampBlockEntity){
                        var color = alcoholLampBlockEntity.getColor();
                        if(color != 0) return color;
                    }
                    
                }
                return new Color(255,255,255,0).getRGB();
            });
        }
    }
}
