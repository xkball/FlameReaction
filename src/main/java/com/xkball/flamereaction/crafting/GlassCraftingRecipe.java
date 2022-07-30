package com.xkball.flamereaction.crafting;

import com.google.gson.JsonObject;
import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.crafting.util.IntListContainer;
import com.xkball.flamereaction.eventhandler.register.RecipeRegister;
import com.xkball.flamereaction.util.JsonUtil;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@SuppressWarnings("ClassCanBeRecord")
public class GlassCraftingRecipe implements Recipe<IntListContainer> {
    
    public static final String ID = "glass_crafting_recipe";
    
    protected final ResourceLocation id;
    protected final IntList intList;
    protected final ItemStack outputItem;
    protected final Block targetBlock;
    
    public GlassCraftingRecipe(ResourceLocation id, IntList intList, ItemStack outputItem, Block targetBlock) {
        this.id = id;
        this.intList = intList;
        this.outputItem = outputItem;
        this.targetBlock = targetBlock;
    }
    
    @Override
    public boolean matches(@NotNull IntListContainer intListContainer, @NotNull Level level) {
        return intListContainer.getTarget() == targetBlock && intListMatches(intList,intListContainer.getIntList());
    }
    
    public static boolean intListMatches(IntList i1,IntList i2){
        if(i1.size()== i2.size()){
            for(int i=0;i<i1.size();i++){
                if(!(i1.getInt(i)==i2.getInt(i))) return false;
            }
            return true;
        }
        return false;
    }
    
    @Override
    public @NotNull ItemStack assemble(@NotNull IntListContainer intListContainer) {
        return outputItem.copy();
    }
    
    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }
    
    @Override
    public @NotNull ItemStack getResultItem() {
        return outputItem.copy();
    }
    
    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }
    
    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return RecipeRegister.GLASS_CRAFTING_RECIPE_SERIALIZER.get();
    }
    
    @Override
    public boolean isSpecial() {
        return true;
    }
    
    @Override
    public @NotNull RecipeType<?> getType() {
        return RecipeRegister.GLASS_CRAFTING_TYPE.get();
    }
    
    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<GlassCraftingRecipe>{
    
        public static final String NAME = FlameReaction.MOD_ID + ":glass_crafting_recipe";
        
        @Override
        public @NotNull GlassCraftingRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
            
            final ItemStack result = JsonUtil.itemsFromJson(json,"result").get(0);
            final Block target = JsonUtil.blocksFromJson(json,"target").get(0);
            var array = JsonUtil.integersFromJson(json,"patten");
            IntList list = new IntArrayList(array);
            
            return new GlassCraftingRecipe(id,list,result,target);
        }
    
        @Nullable
        @Override
        public GlassCraftingRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf byteBuf) {
            var list = IntList.of(byteBuf.readVarIntArray());
            var result = byteBuf.readItem();
            var target = FlameReaction.getBlock(byteBuf.readResourceLocation());
            return new GlassCraftingRecipe(id,list,result,target);
        }
    
        @Override
        public void toNetwork(@NotNull FriendlyByteBuf byteBuf, @NotNull GlassCraftingRecipe recipe) {
            byteBuf.writeVarIntArray(recipe.intList.toIntArray());
            byteBuf.writeItemStack(recipe.outputItem,true);
            byteBuf.writeResourceLocation(Objects.requireNonNull(recipe.targetBlock.getRegistryName()));
        }
    }
}
