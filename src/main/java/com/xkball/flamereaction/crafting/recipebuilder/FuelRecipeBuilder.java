package com.xkball.flamereaction.crafting.recipebuilder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.xkball.flamereaction.util.JsonUtil;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

@SuppressWarnings("ClassCanBeRecord")
public class FuelRecipeBuilder implements RecipeBuilder {
    
    private final ItemStack itemFuel;
    private final FluidStack fluidFuel;
    private final int time;
    private final int maxHeat;
    private final RecipeSerializer<?> type;
    
    public FuelRecipeBuilder(ItemStack itemFuel, FluidStack fluidFuel, int time, int maxHeat, RecipeSerializer<?> type) {
        this.itemFuel = itemFuel;
        this.fluidFuel = fluidFuel;
        this.time = time;
        this.maxHeat = maxHeat;
        this.type = type;
    }
    
    @Override
    public @NotNull RecipeBuilder unlockedBy(@NotNull String p_176496_, @NotNull CriterionTriggerInstance p_176497_) {
        return this;
    }
    
    @Override
    public @NotNull RecipeBuilder group(@Nullable String p_176495_) {
        return this;
    }
    
    @Override
    public @NotNull Item getResult() {
        return Items.AIR;
    }
    
    @Override
    public void save(@NotNull Consumer<FinishedRecipe> consumer, @NotNull ResourceLocation id) {
        consumer.accept(new Result(itemFuel,fluidFuel,time,maxHeat,id,type));
    }
    
    public static class Result implements FinishedRecipe{
    
        private final ItemStack itemFuel;
        private final FluidStack fluidFuel;
        private final int time;
        private final int maxHeat;
        private final ResourceLocation id;
        private final RecipeSerializer<?> type;
    
        public Result(ItemStack itemFuel, FluidStack fluidFuel, int time, int maxHeat, ResourceLocation id, RecipeSerializer<?> type) {
            this.itemFuel = itemFuel;
            this.fluidFuel = fluidFuel;
            this.time = time;
            this.maxHeat = maxHeat;
            this.id = id;
            this.type = type;
        }
    
        @Override
        public void serializeRecipeData(@NotNull JsonObject json) {
    
            JsonArray inputItem = new JsonArray();
            inputItem.add(JsonUtil.itemToJsonWithoutNBT(itemFuel));
            json.add("input_item",inputItem);
            JsonArray inputFluid = new JsonArray();
            inputFluid.add(JsonUtil.fluidToJson(fluidFuel));
            json.add("input_fluid",inputFluid);
            json.add("time",JsonUtil.integersToJson(IntList.of(time)));
            json.add("max_heat",JsonUtil.integersToJson(IntList.of(maxHeat)));
        }
    
        @Override
        public @NotNull ResourceLocation getId() {
            return id;
        }
    
        @Override
        public @NotNull RecipeSerializer<?> getType() {
            return type;
        }
    
        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }
    
        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }
    }
}
