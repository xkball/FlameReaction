package com.xkball.flamereaction.itemlike.item.commonitem;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.creativemodetab.CreativeModeTabs;
import com.xkball.flamereaction.itemlike.item.FRCItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public class CommonItem extends Item implements FRCItem {
    
    public static final String WROUGHT_IRON_INGOT = "wrought_iron_ingot";
    public static final String WROUGHT_IRON_NUGGET = "wrought_iron_nugget";
    
    private final String c;
    public CommonItem(CreativeModeTab tab, String s,String c) {
        super(new Item.Properties()
                .setNoRepair()
                .fireResistant()
                .tab(tab));
        this.c = c;
        this.setRegistryName(new ResourceLocation(FlameReaction.MOD_ID,s));
        add();
    }
    
    
    @Override
    public @NotNull String getChineseTranslate() {
        return c;
    }
}
