package com.xkball.flamereaction.register;

import com.xkball.flamereaction.FlameReaction;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegister {
    public static final DeferredRegister<Item> ITEM_DR = DeferredRegister.create(ForgeRegistries.ITEMS, FlameReaction.MOD_ID);
    //public static final RegistryObject<Item> materialIngot = ITEM_DR.register("i", BasicMaterial::new);
}
