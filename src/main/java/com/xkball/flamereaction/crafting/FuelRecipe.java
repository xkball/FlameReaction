package com.xkball.flamereaction.crafting;

import com.google.gson.JsonObject;
import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.eventhandler.register.RecipeRegister;
import com.xkball.flamereaction.util.JsonUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("ClassCanBeRecord")
public class FuelRecipe implements Recipe<Container> {
    
    public static final String ID = "fuel_recipe";
    
    private final ItemStack itemFuel;
    private final FluidStack fluidFuel;
    private final int time;
    private final int maxHeat;
    private final ResourceLocation id;
    
    public FuelRecipe(ItemStack itemFuel, FluidStack fluidFuel, int time, int maxHeat, ResourceLocation id) {
        this.itemFuel = itemFuel;
        this.fluidFuel = fluidFuel;
        this.time = time;
        this.maxHeat = maxHeat;
        this.id = id;
    }
    
    
    @Override
    public boolean matches(@NotNull Container p_44002_, @NotNull Level p_44003_) {
        return false;
    }
    
    @Override
    public @NotNull ItemStack assemble(@NotNull Container p_44001_) {
        return ItemStack.EMPTY;
    }
    
    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }
    
    @Override
    public @NotNull ItemStack getResultItem() {
        return ItemStack.EMPTY;
    }
    
    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }
    
    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return RecipeRegister.FUEL_RECIPE_SERIALIZER.get();
    }
    
    @Override
    public @NotNull RecipeType<?> getType() {
        return RecipeRegister.FUEL_RECIPE_TYPE.get();
    }
    
    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<FuelRecipe>{
    
        public static final String NAME = FlameReaction.MOD_ID+":fuel_recipe";
        
        @Override
        public @NotNull FuelRecipe fromJson(@NotNull ResourceLocation id, @NotNull JsonObject json) {
    
            var item = JsonUtil.itemsFromJson(json,"input_item").get(0);
            final FluidStack inputFluid = JsonUtil.fluidFromJson(json,"input_fluid").get(0);
            final int maxHeat = JsonUtil.integersFromJson(json,"max_heat").get(0);
            final int time = JsonUtil.integersFromJson(json,"time").get(0);
            
            return new FuelRecipe(item,inputFluid,time,maxHeat,id);
        }
    
        @Nullable
        @Override
        public FuelRecipe fromNetwork(@NotNull ResourceLocation id, @NotNull FriendlyByteBuf byteBuf) {
            var item = byteBuf.readItem();
            var fluid = byteBuf.readFluidStack();
            var time = byteBuf.readInt();
            var maxHeat = byteBuf.readInt();
            
            return new FuelRecipe(item,fluid,time,maxHeat,id);
        }
    
        @Override
        public void toNetwork(@NotNull FriendlyByteBuf byteBuf, @NotNull FuelRecipe recipe) {
            byteBuf.writeItemStack(recipe.itemFuel,true);
            byteBuf.writeFluidStack(recipe.fluidFuel);
            byteBuf.writeInt(recipe.time);
            byteBuf.writeInt(recipe.maxHeat);
        }
    }
}
