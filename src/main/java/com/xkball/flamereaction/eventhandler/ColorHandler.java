package com.xkball.flamereaction.eventhandler;

import com.xkball.flamereaction.item.ItemList;
import com.xkball.flamereaction.item.materialitem.MaterialItem;
import com.xkball.flamereaction.part.material.IMaterial;
import com.xkball.flamereaction.util.MaterialProperty;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ColorHandler {
    
    @SubscribeEvent
    public static void colorHandler(ColorHandlerEvent.Item event){
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
        }
    }
}
