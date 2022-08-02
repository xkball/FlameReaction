package com.xkball.flamereaction.itemlike.item.commonitem;

import com.xkball.flamereaction.itemlike.item.FRCItem;
import com.xkball.flamereaction.util.ItemList;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public abstract class FRCBucketItem extends BucketItem implements FRCItem {
    
    public static final String IMPURE_ALCOHOL_BUCKET = "impure_alcohol_bucket";
    
    public FRCBucketItem(Supplier<? extends Fluid> supplier, Properties builder,String e) {
        super(supplier, builder);
        ItemList.item_instance.put(e,this);
    }
    
}
