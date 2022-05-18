package com.xkball.flamereaction.data;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.item.ItemList;
import com.xkball.flamereaction.item.materialitem.MaterialItem;
import com.xkball.flamereaction.util.MaterialProperty;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ItemModelGenerator extends ItemModelProvider {
    public static final ResourceLocation GENERATED = new ResourceLocation("item/generated");
    
    @SubscribeEvent
    public static void dataGen(GatherDataEvent event){
        event.getGenerator().addProvider(new ItemModelGenerator(event.getGenerator(),event.getExistingFileHelper()));
    }
    public ItemModelGenerator(DataGenerator gen,ExistingFileHelper exFileHelper) {
        super(gen, FlameReaction.MOD_ID, exFileHelper);
    }
    
    
    
    @Override
    protected void registerModels() {
        for(Item item : ItemList.item_instance.values()){
            if(item instanceof MaterialItem){
                if(MaterialProperty.getInstanceFromIMaterial(((MaterialItem) item).getMaterial()).getColor()
                == null){
                    //generatedItem(FlameReaction.getItemName(item));
                }
                else {
                    generatedStampItem(FlameReaction.getItemName(item), ((MaterialItem) item).getMaterialKind().getName());
                }
            }
        }
    }
    
    public ItemModelBuilder generatedItem(String name) {
        return withExistingParent(name, GENERATED)
                .texture("layer0", modLoc("item/" + name));
    }
    public ItemModelBuilder generatedStampItem(String name,String type){
        return withExistingParent(name, GENERATED)
                .texture("layer0", modLoc("item/"+"common/"+type));
    }
    
    public ModelFile getModelFile(String id,String kind){
        return new ModelFile.UncheckedModelFile(new ResourceLocation(FlameReaction.MOD_ID,"item/"+id+kind));
    }
}
