package com.xkball.flamereaction.data;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.itemlike.item.itemtags.ItemTags;
import com.xkball.flamereaction.itemlike.item.materialitem.MaterialItem;
import com.xkball.flamereaction.util.ItemList;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class ItemTagGenerator extends ItemTagsProvider {
    public ItemTagGenerator(DataGenerator p_126530_, BlockTagsProvider p_126531_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_126530_, p_126531_, FlameReaction.MOD_ID, existingFileHelper);
    }
    
    @Override
    protected void addTags() {
        for(Item item : ItemList.item_instance.values()){
            addMaterialTag(item);
        }
        
    }
    
    private void addMaterialTag(Item item) {
        if(item instanceof MaterialItem mi){
            addMaterialTag(mi);
        }
    }
    
    public  void addMaterialTag(MaterialItem materialItem){
        var m = materialItem.getMaterial();
        var mt = materialItem.getMaterialKind();
        var tag = ItemTags.getMaterialTag(mt,m);
        this.tag(tag).add(materialItem);
    }
}
