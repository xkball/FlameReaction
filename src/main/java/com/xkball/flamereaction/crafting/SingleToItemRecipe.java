package com.xkball.flamereaction.crafting;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.eventhandler.register.RecipeRegister;
import com.xkball.flamereaction.itemlike.block.blockentity.ITargetBlockEntity;
import com.xkball.flamereaction.util.JsonUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.PositionImpl;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;


//目前只支持单一输入输出
//高级的坑等着填
@SuppressWarnings("ClassCanBeRecord")
public class SingleToItemRecipe implements ISingleToSingleRecipe {
    
    protected final ResourceLocation id;
    protected final ItemStack resultItem;
    protected final ItemStack inputItem;
    protected final Block targetBlock;
   
    
    public SingleToItemRecipe(ResourceLocation id, ItemStack resultItem, ItemStack inputItem, Block targetBlock) {
        this.id = id;
        this.resultItem = resultItem;
        this.inputItem = inputItem;
        this.targetBlock = targetBlock;
    }
    
    @Override
    public boolean action(Level level, BlockPos pos) {
        if(resultItem != null && !resultItem.isEmpty()){
            DefaultDispenseItemBehavior.spawnItem(level,resultItem,1, Direction.UP, new PositionImpl(pos.getX(),pos.getY()+0.5,pos.getZ()));
            return true;
        }
        return false;
    }
    
    @Override
    public @NotNull Item getInput() {
        return inputItem.getItem();
    }
    
    @Override
    public boolean matches(@NotNull Container container, @NotNull Level p_44003_) {
        if(container instanceof ITargetBlockEntity te){
            return te.getTarget().equals(targetBlock) && container.getItem(0).is(inputItem.getItem());
        }
        return false;
    }
    
    @Override
    public @NotNull ItemStack assemble(@NotNull Container p_44001_) {
        return resultItem.copy();
    }
    
    
    @Override
    public @NotNull ItemStack getResultItem() {
        return resultItem.copy();
    }
    
    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }
    
    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return RecipeRegister.SINGLE_TO_ITEM_SERIALIZER.get();
    }
    
    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<SingleToItemRecipe> {
        public static final String NAME = FlameReaction.MOD_ID + ":single_to_item_recipe";
        @Override
        public @NotNull SingleToItemRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
            ImmutableList<ItemStack> input = ImmutableList.copyOf(JsonUtil.itemsFromJson(json,INPUT));
            ImmutableList<ItemStack> outputItem = ImmutableList.copyOf(JsonUtil.itemsFromJson(json,"output_item"));
            ImmutableList<Block> target = ImmutableList.copyOf(JsonUtil.blocksFromJson(json,"target"));
            if(input.isEmpty()) throw new JsonParseException("单一物品配方内无输入");
            if(input.size() != 1) throw new JsonParseException("单一物品配方内只有有单一输入");
            if(target.isEmpty()) throw new JsonParseException("单一物品配方内无目标方块");
            return new SingleToItemRecipe(id,outputItem.get(0),input.get(0),target.get(0));
            
        }
    
        @Nullable
        @Override
        public SingleToItemRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf byteBuf) {
            ImmutableList.Builder<ItemStack> input = ImmutableList.builder();
            ImmutableList.Builder<ItemStack> outputItem = ImmutableList.builder();
            ImmutableList.Builder<Block> target = ImmutableList.builder();
            
            input.add(byteBuf.readItem());
            outputItem.add(byteBuf.readItem());
            target.add(Objects.requireNonNull(ForgeRegistries.BLOCKS.getValue(byteBuf.readResourceLocation())));
            return new SingleToItemRecipe(id,outputItem.build().get(0),input.build().get(0),target.build().get(0));
        }
    
        @Override
        public void toNetwork(@NotNull FriendlyByteBuf byteBuf, @NotNull SingleToItemRecipe recipe) {
            var input = recipe.inputItem.copy();
            ItemStack outputItem = null;
            if(recipe.resultItem != null) outputItem = recipe.resultItem;
            
            byteBuf.writeItemStack(input,true);
            byteBuf.writeItemStack(outputItem == null?ItemStack.EMPTY : outputItem,true);
            byteBuf.writeResourceLocation(Objects.requireNonNull(recipe.targetBlock.getRegistryName()));
            
            
        }
    }
}
