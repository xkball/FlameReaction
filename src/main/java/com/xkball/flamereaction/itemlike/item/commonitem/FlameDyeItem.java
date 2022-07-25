package com.xkball.flamereaction.itemlike.item.commonitem;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.creativemodetab.CreativeModeTabs;
import com.xkball.flamereaction.itemlike.item.FRCItem;
import com.xkball.flamereaction.util.translateutil.DyeColorTrans;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public class FlameDyeItem extends DyeItem implements FRCItem {
    
    public FlameDyeItem(String name, DyeColor dyeColor) {
        super(dyeColor,new Item.Properties()
                .setNoRepair()
                .fireResistant()
                .tab(CreativeModeTabs.MATERIAL_GROUP));
        this.setRegistryName(FlameReaction.MOD_ID,name+"_flame_dye_item");
        add();
    }
    
    
    @Override
    public @NotNull String getChineseTranslate() {
        return DyeColorTrans.getTrans(this.getDyeColor())+"色焰色染料";
    }
}
