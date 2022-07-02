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
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

@SuppressWarnings("ClassCanBeRecord")
public class SingleToItemRecipeBuilder implements RecipeBuilder {
    
    protected final Item resultItem;
    protected final Item inputItem;
    protected final Block targetBlock;
    private final RecipeSerializer<?> type;
    
    public SingleToItemRecipeBuilder(Item resultItem, Item inputItem, Block targetBlock, RecipeSerializer<?> type) {
        this.resultItem = resultItem;
        this.inputItem = inputItem;
        this.targetBlock = targetBlock;
        this.type = type;
    }
    
    //不可用的方法
    @Override
    public @NotNull RecipeBuilder unlockedBy(@NotNull String p_176496_, @NotNull CriterionTriggerInstance p_176497_) {
        return this;
    }
    
    //不可用的方法
    @Override
    public @NotNull RecipeBuilder group(@Nullable String p_176495_) {
        return this;
    }
    
    @Override
    public @NotNull Item getResult() {
        return resultItem;
    }
    
    @Override
    public void save(@NotNull Consumer<FinishedRecipe> consumer, @NotNull ResourceLocation id) {
        consumer.accept(new Result(resultItem,inputItem,targetBlock,id,type));
    }
    
    public static class Result implements FinishedRecipe{
    
        private final Item resultItem;
        private final Item inputItem;
        private final Block targetBlock;
        private final ResourceLocation id;
        private final RecipeSerializer<?> type;
    
        public Result(Item resultItem, Item inputItem, Block targetBlock, ResourceLocation id, RecipeSerializer<?> type) {
            this.resultItem = resultItem;
            this.inputItem = inputItem;
            this.targetBlock = targetBlock;
            this.id = id;
            this.type = type;
        }
    
        @Override
        public void serializeRecipeData(JsonObject jsonObject) {
            JsonArray inputArray = new JsonArray();
            inputArray.add(JsonUtil.itemToJsonWithoutNBT(new ItemStack(inputItem,1)));
            jsonObject.add("input",inputArray);
            JsonArray output = new JsonArray();
            output.add(JsonUtil.itemToJsonWithoutNBT(new ItemStack(resultItem,1)));
            jsonObject.add("output_item",output);
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
