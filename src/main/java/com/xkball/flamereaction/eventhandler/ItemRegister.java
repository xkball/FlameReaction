package com.xkball.flamereaction.eventhandler;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.itemlike.item.ItemList;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemRegister {
    //public static final DeferredRegister<Item> ITEM_DR = DeferredRegister.create(ForgeRegistries.ITEMS, FlameReaction.MOD_ID);
    //public static final RegistryObject<Item> materialIngot = ITEM_DR.register("i", BasicMaterial::new);
    
    @SubscribeEvent
    public static void onRegisterItem(RegistryEvent.Register<Item> event) {
        FlameReaction.init();
        for(Item item : ItemList.item_instance.values()){
                event.getRegistry().register(item);
        }
        
    }
}
