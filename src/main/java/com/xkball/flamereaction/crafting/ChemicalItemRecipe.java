package com.xkball.flamereaction.crafting;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.eventhandler.register.RecipeRegister;
import com.xkball.flamereaction.util.JsonUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@SuppressWarnings("ClassCanBeRecord")
public class ChemicalItemRecipe implements IChemicalRecipe{
    
    protected final ResourceLocation id;
    protected final ItemStack result;
    protected final ImmutableList<ItemStack> inputItem;
    protected final FluidStack inputFluid;
    protected final int heatNeed;
    
    public ChemicalItemRecipe(ResourceLocation id, ItemStack result, ImmutableList<ItemStack> inputItem, FluidStack inputFluid, int heatNeed) {
        this.id = id;
        this.result = result;
        this.inputItem = inputItem;
        this.inputFluid = inputFluid;
        this.heatNeed = heatNeed;
    }
    
    //可以没有流体
    @Override
    public boolean matches(@NotNull Container p_44002_, @NotNull Level p_44003_) {
        return false;
    }
    
    @Override
    public @NotNull ItemStack assemble(@NotNull Container p_44001_) {
        return result.copy();
    }
    
    @Override
    public @NotNull ItemStack getResultItem() {
        return result.copy();
    }
    
    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }
    
    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return RecipeRegister.CHEMICAL_ITEM_RECIPE_SERIALIZER.get();
    }
    
    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<ChemicalItemRecipe>{
        
        public static final String NAME = FlameReaction.MOD_ID + ":chemical_item_recipe";
        
        @Override
        public @NotNull ChemicalItemRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
            
            ImmutableList<ItemStack> inputItem = ImmutableList.copyOf(JsonUtil.itemsFromJson(json,"input_item"));
            final int heatNeed = JsonUtil.integersFromJson(json,"heat_need").get(0);
            final ItemStack result = JsonUtil.itemsFromJson(json,"result").get(0);
            final FluidStack inputFluid = JsonUtil.fluidFromJson(json,"input_fluid").get(0);
            
            return new ChemicalItemRecipe(id,result,inputItem,inputFluid,heatNeed);
        }
    
        @Override
        public ChemicalItemRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf byteBuf) {
            var heatNeed = byteBuf.readInt();
            var inputFluid = byteBuf.readFluidStack();
            var result = byteBuf.readItem();
            var list = new ArrayList<ItemStack>();
            var i = byteBuf.readInt();
            for(int o=0;o<i;o++){
                list.add(byteBuf.readItem());
            }
            return new ChemicalItemRecipe(id,result,ImmutableList.copyOf(list),inputFluid,heatNeed);
        }
    
        @Override
        public void toNetwork(@NotNull FriendlyByteBuf byteBuf, @NotNull ChemicalItemRecipe recipe) {
            byteBuf.writeInt(recipe.heatNeed);
            byteBuf.writeFluidStack(recipe.inputFluid);
            byteBuf.writeItemStack(recipe.result,true);
            byteBuf.writeInt(recipe.inputItem.size());
            for(ItemStack itemStack : recipe.inputItem){
                byteBuf.writeItemStack(itemStack,true);
            }
        }
    }
}
