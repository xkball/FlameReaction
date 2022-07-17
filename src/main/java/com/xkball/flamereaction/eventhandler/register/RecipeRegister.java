package com.xkball.flamereaction.eventhandler.register;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.crafting.*;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.lwjgl.system.CallbackI;

import javax.print.StreamPrintServiceFactory;
import java.util.Objects;

public class RecipeRegister {
    
    public static final DeferredRegister<RecipeType<?>> RECIPE = DeferredRegister.create(Registry.RECIPE_TYPE_REGISTRY, FlameReaction.MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZER = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, FlameReaction.MOD_ID);
    public static final RegistryObject<RecipeType<ISingleToSingleRecipe>> SINGLE_TO_SINGLE_TYPE = RECIPE.register(ISingleToSingleRecipe.ID, ModRecipeType::new);
    public static final RegistryObject<RecipeType<IChemicalRecipe>> CHEMICAL_TYPE = RECIPE.register(IChemicalRecipe.ID,ModRecipeType::new);
    public static final RegistryObject<RecipeType<GlassCraftingRecipe>> GLASS_CRAFTING_TYPE = RECIPE.register(GlassCraftingRecipe.ID,ModRecipeType::new);
    public static final RegistryObject<RecipeType<FuelRecipe>> FUEL_RECIPE_TYPE = RECIPE.register(FuelRecipe.ID,ModRecipeType::new);
    
    public static final RegistryObject<SingleToItemRecipe.Serializer> SINGLE_TO_ITEM_SERIALIZER = SERIALIZER.register(serializerName(SingleToItemRecipe.Serializer.NAME), SingleToItemRecipe.Serializer::new);
    public static final RegistryObject<SingleToFluidRecipe.Serializer> SINGLE_TO_FLUID_SERIALIZER = SERIALIZER.register(serializerName(SingleToFluidRecipe.Serializer.NAME), SingleToFluidRecipe.Serializer::new);
    public static final RegistryObject<ChemicalItemRecipe.Serializer> CHEMICAL_ITEM_RECIPE_SERIALIZER = SERIALIZER.register(serializerName(ChemicalItemRecipe.Serializer.NAME),ChemicalItemRecipe.Serializer::new);
    public static final RegistryObject<GlassCraftingRecipe.Serializer> GLASS_CRAFTING_RECIPE_SERIALIZER = SERIALIZER.register(serializerName(GlassCraftingRecipe.Serializer.NAME),GlassCraftingRecipe.Serializer::new);
    public static final RegistryObject<FuelRecipe.Serializer> FUEL_RECIPE_SERIALIZER = SERIALIZER.register(serializerName(FuelRecipe.Serializer.NAME),FuelRecipe.Serializer::new);
    
    public static String serializerName(String name){
        return name.substring(FlameReaction.MOD_ID.length()+1);
    }
    
    
    //复制自FireCrafting
    private static class ModRecipeType<T extends Recipe<?>> implements RecipeType<T> {
        @Override
        public String toString() {
            return Objects.requireNonNull(Registry.RECIPE_TYPE.getKey(this)).toString();
        }
    }
}

