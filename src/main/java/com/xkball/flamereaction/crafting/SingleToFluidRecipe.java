package com.xkball.flamereaction.crafting;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.eventhandler.register.RecipeRegister;
import com.xkball.flamereaction.itemlike.block.blockentity.ITargetBlockEntity;
import com.xkball.flamereaction.util.JsonUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@SuppressWarnings("ClassCanBeRecord")
public class SingleToFluidRecipe implements ISingleToSingleRecipe {
    
    protected final ResourceLocation id;
    protected final FluidStack resultFluid;
    protected final ItemStack inputItem;
    protected final Block targetBlock;
    
    public SingleToFluidRecipe(ResourceLocation id, FluidStack resultFluid, ItemStack inputItem, Block targetBlock) {
        this.id = id;
        this.resultFluid = resultFluid;
        this.inputItem = inputItem;
        this.targetBlock = targetBlock;
    }
    
    @Override
    public boolean action(Level level, BlockPos pos) {
        return false;
    }
    
    @Override
    public @NotNull Item getInput() {
        return inputItem.getItem();
    }
    
    @Override
    public boolean matches(@NotNull Container container, @NotNull Level level) {
        if( container instanceof ITargetBlockEntity ibe && ibe.getTarget().equals(targetBlock)){
            return container.getItem(0).is(inputItem.getItem());
        }
        return false;
    }
    
    @Override
    public FluidStack getResultFluid() {
        return resultFluid.copy();
    }
    
    @Override
    public @NotNull ItemStack assemble(@NotNull Container p_44001_) {
        return ItemStack.EMPTY;
    }
    
    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }
    
    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return RecipeRegister.SINGLE_TO_FLUID_SERIALIZER.get();
    }
    
    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<SingleToFluidRecipe>{
        
        public static final String NAME = FlameReaction.MOD_ID + ":single_to_fluid_recipe";
        
        @Override
        public @NotNull SingleToFluidRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
            ImmutableList<ItemStack> input = ImmutableList.copyOf(JsonUtil.itemsFromJson(json,INPUT));
            ImmutableList<Block> target = ImmutableList.copyOf(JsonUtil.blocksFromJson(json,"target"));
            ImmutableList<FluidStack> result = ImmutableList.copyOf(JsonUtil.fluidFromJson(json,"result_fluid"));
            if(input.isEmpty() || target.isEmpty() || result.isEmpty()) throw new JsonParseException("配方输入/目标/产物 不能为空");
            if(input.size() != 1) throw new JsonParseException("配方只能有单一输入");
            return new SingleToFluidRecipe(id,result.get(0),input.get(0),target.get(0));
        }
    
        @Nullable
        @Override
        public SingleToFluidRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf byteBuf) {
            var item = byteBuf.readItem();
            var block = ForgeRegistries.BLOCKS.getValue(byteBuf.readResourceLocation());
            var result = byteBuf.readFluidStack();
            return new SingleToFluidRecipe(id,result,item,block);
        }
    
        @Override
        public void toNetwork(@NotNull FriendlyByteBuf byteBuf, @NotNull SingleToFluidRecipe recipe) {
            var input = recipe.inputItem.copy();
            var result = recipe.resultFluid.copy();
            
            byteBuf.writeItemStack(input,true);
            byteBuf.writeResourceLocation(Objects.requireNonNull(recipe.targetBlock.getRegistryName()));
            byteBuf.writeFluidStack(result);
        }
    }
}
