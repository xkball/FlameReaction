package com.xkball.flamereaction.eventhandler.register;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.util.ItemList;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
