package com.xkball.flamereaction.crafting.recipebuilder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.xkball.flamereaction.util.JsonUtil;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

@SuppressWarnings("ClassCanBeRecord")
public class SingleToFluidRecipeBuilder implements RecipeBuilder {
    
    protected final FluidStack resultFluid;
    protected final Item inputItem;
    protected final Block targetBlock;
    private final RecipeSerializer<?> type;
    
    public SingleToFluidRecipeBuilder(FluidStack fluidStack, Item inputItem, Block targetBlock, RecipeSerializer<?> type) {
        this.resultFluid = fluidStack;
        this.inputItem = inputItem;
        this.targetBlock = targetBlock;
        this.type = type;
    }
    
    //不可用
    @Override
    public @NotNull RecipeBuilder unlockedBy(@NotNull String p_176496_, @NotNull CriterionTriggerInstance p_176497_) {
        return this;
    }
    
    //不可用
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
        consumer.accept(new Result(resultFluid,inputItem,targetBlock,type,id));
    }
    
    public static class Result implements FinishedRecipe{
        protected final FluidStack resultFluid;
        protected final Item inputItem;
        protected final Block targetBlock;
        private final RecipeSerializer<?> type;
        private final ResourceLocation id;
    
        public Result(FluidStack resultFluid, Item inputItem, Block targetBlock, RecipeSerializer<?> type, ResourceLocation id) {
            this.resultFluid = resultFluid;
            this.inputItem = inputItem;
            this.targetBlock = targetBlock;
            this.type = type;
            this.id = id;
        }
    
        @Override
        public void serializeRecipeData(@NotNull JsonObject jsonObject) {
            JsonArray inputArray = new JsonArray();
            inputArray.add(JsonUtil.itemToJsonWithoutNBT(new ItemStack(inputItem,1)));
            jsonObject.add("input",inputArray);
            JsonArray outputArray = new JsonArray();
            outputArray.add(JsonUtil.fluidToJson(resultFluid));
            jsonObject.add("result_fluid",outputArray);
            JsonArray target = new JsonArray();
            target.add(JsonUtil.blockToJson(targetBlock));
            jsonObject.add("target",target);
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
