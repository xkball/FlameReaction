package com.xkball.flamereaction.data;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.itemlike.item.ColoredFlammableItem;
import com.xkball.flamereaction.itemlike.item.commonitem.CommonItem;
import com.xkball.flamereaction.itemlike.item.commonitem.tool.ExhibitBlockKey;
import com.xkball.flamereaction.itemlike.item.commonitem.FlameDyeItem;
import com.xkball.flamereaction.itemlike.item.commonitem.tool.Hammer;
import com.xkball.flamereaction.itemlike.item.commonitem.UniversalSaddle;
import com.xkball.flamereaction.itemlike.item.commonitem.tool.Pliers;
import com.xkball.flamereaction.itemlike.item.commonitem.tool.Wrench;
import com.xkball.flamereaction.util.ItemList;
import com.xkball.flamereaction.itemlike.item.materialitem.MaterialItem;
import com.xkball.flamereaction.util.MaterialProperty;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import static com.xkball.flamereaction.FlameReaction.getItemName;


public class ItemModelGenerator extends ItemModelProvider {
    
    public static final ResourceLocation GENERATED = new ResourceLocation("item/generated");
    public static final ResourceLocation HANDHELD = new ResourceLocation("item/handheld");
    
    public ItemModelGenerator(DataGenerator gen,ExistingFileHelper exFileHelper) {
        super(gen, FlameReaction.MOD_ID, exFileHelper);
    }
    
    
    
    @Override
    protected void registerModels() {
        for(Item item : ItemList.item_instance.values()){
            if(item instanceof MaterialItem){
                if(MaterialProperty.getInstanceFromIMaterial(((MaterialItem) item).getMaterial()).getColor()
                == null){
                    generatedItem(getItemName(item));
                }
                else {
                    generatedStampItem(getItemName(item), ((MaterialItem) item).getMaterialKind().getName());
                }
            }
            if(item instanceof ExhibitBlockKey){
                handheldItem(getItemName(item));
            }
            if(item instanceof ColoredFlammableItem){
                generatedStampItem(getItemName(item),"powder");
            }
            if(item instanceof UniversalSaddle){
                handheldItem(getItemName(item));
            }
            if(item instanceof FlameDyeItem){
                generatedStampItem(getItemName(item),"flame_dye_item");
            }
            if(item instanceof Hammer){
                generatedItem("hammer");
            }
            
        }
        generatedItem("impure_alcohol_bucket");
        handheldItem(Pliers.NAME);
        handheldItem(Wrench.NAME);
        generatedStampItem(CommonItem.WROUGHT_IRON_INGOT,"ingot");
        generatedStampItem(CommonItem.WROUGHT_IRON_NUGGET,"nugget");
    }
    
    public ItemModelBuilder generatedItem(String name) {
        return withExistingParent(name, GENERATED)
                .texture("layer0", modLoc("item/" + name));
    }
    public ItemModelBuilder generatedStampItem(String name,String type){
        return withExistingParent(name, GENERATED)
                .texture("layer0", modLoc("item/"+"common/"+type));
    }
    
    public ItemModelBuilder handheldItem(String name) {
        return withExistingParent(name, HANDHELD)
                .texture("layer0", modLoc("item/" + name));
    }
    
    public ModelFile getModelFile(String id,String kind){
        return new ModelFile.UncheckedModelFile(new ResourceLocation(FlameReaction.MOD_ID,"item/"+id+kind));
    }
}
