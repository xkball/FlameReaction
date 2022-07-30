package com.xkball.flamereaction.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


//复制并修改自FireCrafting模组
//其实就是抄的，好吧
//好吧现在多少有点自己写的
//好吧现在修改幅度有点大了
public class JsonUtil {
    
    public static JsonObject itemToJsonWithoutNBT(ItemStack stack){
        var json = new JsonObject();
        json.addProperty("item", Objects.requireNonNull(stack.getItem().getRegistryName()).toString());
        json.addProperty("count",stack.getCount());
        return json;
    }
    
    public static JsonObject blockToJson(Block block){
        var json = new JsonObject();
        json.addProperty("block", Objects.requireNonNull(block.getRegistryName()).toString());
        return json;
    }
    
    public static JsonObject fluidToJson(FluidStack fluidStack){
        var json = new JsonObject();
        json.addProperty("fluid", Objects.requireNonNull(fluidStack.getFluid().getRegistryName()).toString());
        json.addProperty("amount",fluidStack.getAmount());
        return json;
    }
    
    public static JsonArray integersToJson(IntList list){
        var result = new JsonArray();
        for(int i:list){
            result.add(i);
        }
        return result;
    }
    
    public static List<Integer> integersFromJson(JsonObject json, String memberName){
        List<Integer> list = new ArrayList<>();
        json.getAsJsonArray(memberName).forEach((jsonElement -> list.add(jsonElement.getAsInt())));
        if(list.isEmpty()) throw new JsonParseException("json内无对应内容");
        return list;
    }
    
    public static List<ItemStack> itemsFromJson(JsonObject json, String memberName) {
        List<ItemStack> list = new ArrayList<>();
        var array = json.getAsJsonArray(memberName);
        if(array.size()>0) {
            array.forEach((jsonElement -> {
                ItemStack itemStack = CraftingHelper.getItemStack(jsonElement.getAsJsonObject(), false, true);
                list.add(itemStack);
            }));
            if (list.isEmpty()) throw new JsonParseException("json内无对应内容");
            return list;
        }
        return List.of(ItemStack.EMPTY);
    }
    
    
//    public static NonNullList<Ingredient> ingredientsFromJson(JsonArray pIngredientArray) {
//
//        NonNullList<Ingredient> nonnulllist = NonNullList.create();
//        for (int i = 0; i < pIngredientArray.size(); ++i) {
//            Ingredient ingredient = Ingredient.fromJson(pIngredientArray.get(i));
//            if (net.minecraftforge.common.ForgeConfig.SERVER.skipEmptyShapelessCheck.get() || !ingredient.isEmpty()) {
//                nonnulllist.add(ingredient);
//            }
//        }
//        return nonnulllist;
//    }
    
    public static List<Block> blocksFromJson(JsonObject json, String memberName) {
        List<Block> list = new ArrayList<>();
        json.getAsJsonArray(memberName).forEach((j) ->
        {var res = new ResourceLocation(GsonHelper.getAsString(j.getAsJsonObject(),"block"));
            list.add(ForgeRegistries.BLOCKS.getValue(res));
        });
        if(list.isEmpty()) throw new JsonParseException("json内无对应内容");
        return list;
    }
    
    //注意！
    //只能获得单个流体，不是列表
    public static List<FluidStack> fluidFromJson(JsonObject json, String memberName) {
        JsonArray object = json.get(memberName).getAsJsonArray();
        if(object.size()>0) {
            var in = object.get(0).getAsJsonObject();
            String fluid = in.get("fluid").getAsString();
            int amount = in.get("amount").getAsInt();
            Fluid value = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(fluid));
            return List.of(new FluidStack(Objects.requireNonNull(value), amount));
        }
        return List.of(FluidStack.EMPTY.copy());
    }
    
    public static List<EntityType<?>> entityTypesFromJson(JsonObject json, String memberName) {
        ArrayList<EntityType<?>> list = new ArrayList<>();
        json.getAsJsonArray(memberName).forEach((j) -> list.add(ForgeRegistries.ENTITIES.getValue(new ResourceLocation(j.getAsJsonObject().get("entity").getAsString()))));
        if(list.isEmpty()) throw new JsonParseException("json内无对应内容");
        return list;
    }
    
   
}
