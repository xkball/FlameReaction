package com.xkball.flamereaction.crafting.recipebuilder;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.xkball.flamereaction.util.JsonUtil;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
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
public class GlassCraftingRecipeBuilder implements RecipeBuilder {
    
    protected final IntList intList;
    protected final ItemStack outputItem;
    protected final Block targetBlock;
    private final RecipeSerializer<?> type;
    
    public GlassCraftingRecipeBuilder(ItemStack outputItem, Block targetBlock, RecipeSerializer<?> type) {
        this.intList = new IntArrayList();
        this.outputItem = outputItem;
        this.targetBlock = targetBlock;
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
    
    //点过的数字为0；
    public GlassCraftingRecipeBuilder patten(int i1,int i2,int i3,int i4,int i5){
        this.intList.addAll(IntList.of(i1,i2,i3,i4,i5));
        return this;
    }
    
    @Override
    public @NotNull Item getResult() {
        return outputItem.getItem();
    }
    
    @Override
    public void save(@NotNull Consumer<FinishedRecipe> consumer, @NotNull ResourceLocation id) {
        consumer.accept(new Result(type,id,intList,outputItem,targetBlock));
    }
    
    public static class Result implements FinishedRecipe{
    
        private final RecipeSerializer<?> type;
        private final ResourceLocation id;
        protected final IntList intList;
        protected final ItemStack outputItem;
        protected final Block targetBlock;
    
        public Result(RecipeSerializer<?> type, ResourceLocation id, IntList intList, ItemStack outputItem, Block targetBlock) {
            this.type = type;
            this.id = id;
            this.intList = intList;
            this.outputItem = outputItem;
            this.targetBlock = targetBlock;
        }
    
        @Override
        public void serializeRecipeData(@NotNull JsonObject json) {
            
            json.add("patten", JsonUtil.integersToJson(intList));
            JsonArray output = new JsonArray();
            output.add(JsonUtil.itemToJsonWithoutNBT(outputItem));
            json.add("result",output);
            JsonArray target = new JsonArray();
            target.add(JsonUtil.blockToJson(targetBlock));
            json.add("target",target);
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
