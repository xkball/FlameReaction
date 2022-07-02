package com.xkball.flamereaction.crafting;

import com.xkball.flamereaction.eventhandler.register.RecipeRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public interface ISingleToSingleRecipe extends Recipe<Container> {
    
    String ID = "single_to_single_recipe";
    String INPUT = "input";
    String OUTPUT = "output";
    
    boolean action(Level level, BlockPos pos);
    @Nonnull Item getInput();
    
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
        return RecipeRegister.SINGLE_TO_SINGLE_TYPE.get();
    }
    
    @Override
    @NotNull
    default ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }
    
    default FluidStack getResultFluid(){
        return FluidStack.EMPTY;
    }
}
