package com.xkball.flamereaction.crafting;

import com.xkball.flamereaction.eventhandler.register.RecipeRegister;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.NotNull;

public interface IChemicalRecipe extends Recipe<Container> {
    String ID = "chemical_recipe";
    
    @Override
    default boolean isSpecial() {
        return true;
    }
    
    @Override
    default boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }
    
    @Override
    @NotNull
    default RecipeType<?> getType() {
        return RecipeRegister.CHEMICAL_TYPE.get();
    }
    
}
