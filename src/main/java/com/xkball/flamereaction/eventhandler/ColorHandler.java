package com.xkball.flamereaction.eventhandler;

import com.xkball.flamereaction.itemlike.block.BlockList;
import com.xkball.flamereaction.itemlike.block.materialblock.MaterialBlock;
import com.xkball.flamereaction.itemlike.item.ItemList;
import com.xkball.flamereaction.itemlike.item.materialitem.MaterialItem;
import com.xkball.flamereaction.util.MaterialProperty;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ColorHandler {
    
    @SubscribeEvent
    public static void itemColorHandler(ColorHandlerEvent.Item event){
        for(Item item : ItemList.item_instance.values()){
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
    }
    
    @SubscribeEvent
    public static void blockColorHandler(ColorHandlerEvent.Block event){
        for(Block block : BlockList.block_instance.values()){
            if(block instanceof MaterialBlock mb){
                var mic = MaterialProperty.getInstanceFromIMaterial(mb.getMaterial());
                if(mic != null && mic.getColor() != null){
                    event.getBlockColors().register((blockState,batg,blockPos,color) ->{
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
    }
}
