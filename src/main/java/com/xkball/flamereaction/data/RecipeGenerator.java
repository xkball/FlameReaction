package com.xkball.flamereaction.data;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.crafting.recipebuilder.SingleToFluidRecipeBuilder;
import com.xkball.flamereaction.crafting.recipebuilder.SingleToItemRecipeBuilder;
import com.xkball.flamereaction.eventhandler.register.FluidRegister;
import com.xkball.flamereaction.eventhandler.register.RecipeRegister;
import com.xkball.flamereaction.itemlike.block.materialblock.MaterialBlock;
import com.xkball.flamereaction.itemlike.item.materialitem.MaterialIngot;
import com.xkball.flamereaction.util.BlockList;
import com.xkball.flamereaction.util.ItemList;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Consumer;

public class RecipeGenerator extends RecipeProvider {
    public RecipeGenerator(DataGenerator p_125973_) {
        super(p_125973_);
    }
    
    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        for(Block block : BlockList.material_block){
            if(block instanceof MaterialBlock mb){
                var item = ItemList.item_instance.get(mb.getMaterial().getName()+"_ingot");
                ShapedRecipeBuilder.shaped(block)
                        .pattern("iii")
                        .pattern("iii")
                        .pattern("iii")
                        .define('i', item)
                        .unlockedBy(mb.getMaterial().getName()+"_ingot", InventoryChangeTrigger.TriggerInstance.hasItems(item))
                        .save(consumer,new ResourceLocation(FlameReaction.MOD_ID,"block/"+ Objects.requireNonNull(block.getRegistryName()).getPath()));
            }
            
        }
        for(Item item: ItemList.item_instance.values()){
            if(item instanceof MaterialIngot){
                var out = ForgeRegistries.ITEMS.getValue(new ResourceLocation(FlameReaction.MOD_ID,((MaterialIngot) item).getMaterial().getName()+"_plate"));
                if(out!=null){
                    new SingleToItemRecipeBuilder(out,item,FlameReaction.FORGING_TABLE, RecipeRegister.SINGLE_TO_ITEM_SERIALIZER.get())
                            .save(consumer,new ResourceLocation(FlameReaction.MOD_ID,"item/"+ Objects.requireNonNull(out.getRegistryName()).getPath()));
                }
            }
        }
        new SingleToFluidRecipeBuilder(new FluidStack(FluidRegister.IMPURE_ALCOHOL_FLUID.get(),20), Items.SUGAR_CANE,FlameReaction.BREWING_BARREL,RecipeRegister.SINGLE_TO_FLUID_SERIALIZER.get())
                .save(consumer,new ResourceLocation(FlameReaction.MOD_ID,"item_to_fluid/"+ Objects.requireNonNull(Items.SUGAR_CANE.getRegistryName()).getPath()));
    }
}
