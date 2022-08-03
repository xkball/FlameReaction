package com.xkball.flamereaction.data;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.crafting.recipebuilder.FuelRecipeBuilder;
import com.xkball.flamereaction.crafting.recipebuilder.GlassCraftingRecipeBuilder;
import com.xkball.flamereaction.crafting.recipebuilder.SingleToFluidRecipeBuilder;
import com.xkball.flamereaction.crafting.recipebuilder.SingleToItemRecipeBuilder;
import com.xkball.flamereaction.eventhandler.register.FluidRegister;
import com.xkball.flamereaction.eventhandler.register.RecipeRegister;
import com.xkball.flamereaction.itemlike.block.commonblocks.ExhibitBlock;
import com.xkball.flamereaction.itemlike.block.materialblock.MaterialBlock;
import com.xkball.flamereaction.itemlike.block.materialblock.MetalScaffoldingBlock;
import com.xkball.flamereaction.itemlike.item.materialitem.MaterialIngot;
import com.xkball.flamereaction.itemlike.item.materialitem.MaterialStick;
import com.xkball.flamereaction.util.BlockList;
import com.xkball.flamereaction.util.ItemList;
import com.xkball.flamereaction.util.PeriodicTableOfElements;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Consumer;

public class RecipeGenerator extends RecipeProvider {
    public RecipeGenerator(DataGenerator p_125973_) {
        super(p_125973_);
    }
    
    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        //矿物块合成
        for(Block block : BlockList.material_block){
            if(block instanceof MaterialBlock mb){
                var item = ItemList.item_instance.get(mb.getMaterial().getName()+"_ingot");
                ShapedRecipeBuilder.shaped(block)
                        .pattern("iii")
                        .pattern("iii")
                        .pattern("iii")
                        .define('i', item)
                        .unlockedBy(mb.getMaterial().getName()+"_ingot", InventoryChangeTrigger.TriggerInstance.hasItems(item))
                        .save(consumer,new ResourceLocation(FlameReaction.MOD_ID,"block/"+ Objects.requireNonNull(block.getRegistryName()).getPath()));
            }
            
        }
        for(Item item: ItemList.item_instance.values()){
            //板的合成
            if(item instanceof MaterialIngot){
                var out = ForgeRegistries.ITEMS.getValue(new ResourceLocation(FlameReaction.MOD_ID,((MaterialIngot) item).getMaterial().getName()+"_plate"));
                if(out!=null){
                    new SingleToItemRecipeBuilder(out,item,FlameReaction.FORGING_TABLE, RecipeRegister.SINGLE_TO_ITEM_SERIALIZER.get())
                            .save(consumer,new ResourceLocation(FlameReaction.MOD_ID,"item/"+ Objects.requireNonNull(out.getRegistryName()).getPath()));
                }
            }
            
            if(item instanceof MaterialStick i){
                //棍子的合成
                var in = ForgeRegistries.ITEMS.getValue(new ResourceLocation(FlameReaction.MOD_ID, i.getMaterial().getName() +"_ingot"));
                if(i.getMaterial() == PeriodicTableOfElements.Fe) in = Items.IRON_INGOT;
                if(i.getMaterial() == PeriodicTableOfElements.Cu) in = Items.COPPER_INGOT;
                if(in != null){
                    new ShapedRecipeBuilder(item,2)
                            .pattern("i")
                            .pattern("i")
                            .define('i',in)
                            .unlockedBy(Objects.requireNonNull(in.getRegistryName()).getPath(),InventoryChangeTrigger.TriggerInstance.hasItems(in))
                            .save(consumer,new ResourceLocation(FlameReaction.MOD_ID, "item/"+Objects.requireNonNull(i.getRegistryName()).getPath()));
                }
            }
            
        }
        
        //酒精生产
        new SingleToFluidRecipeBuilder(new FluidStack(FluidRegister.IMPURE_ALCOHOL_FLUID.get(),20), Items.SUGAR_CANE,FlameReaction.BREWING_BARREL,RecipeRegister.SINGLE_TO_FLUID_SERIALIZER.get())
                .save(consumer,new ResourceLocation(FlameReaction.MOD_ID,"item_to_fluid/"+ Objects.requireNonNull(Items.SUGAR_CANE.getRegistryName()).getPath()));
        
        //锻造台合成
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.SMITHING_TABLE),FlameReaction.FORGING_TABLE)
                .unlockedBy(Objects.requireNonNull(FlameReaction.FORGING_TABLE.getRegistryName()).getPath(),InventoryChangeTrigger.TriggerInstance.hasItems(FlameReaction.FORGING_TABLE))
                .save(consumer,new ResourceLocation(FlameReaction.MOD_ID,"stone_cutting/"+FlameReaction.FORGING_TABLE.getRegistryName().getPath()));
        
        //玻璃合成:酒精灯
        new GlassCraftingRecipeBuilder(new ItemStack(FlameReaction.ALCOHOL_LAMP,1),FlameReaction.DIPPINGBLOCK,RecipeRegister.GLASS_CRAFTING_RECIPE_SERIALIZER.get())
                .patten(0,1,0,1,0)
                .patten(0,1,0,1,0)
                .patten(1,1,0,1,1)
                .patten(1,0,0,0,1)
                .patten(1,1,1,1,1)
                .save(consumer, new ResourceLocation(FlameReaction.MOD_ID,"glass_crafting/alcohol_lamp"));
    
        //燃料:酒精,煤
        new FuelRecipeBuilder(null,new FluidStack(FluidRegister.IMPURE_ALCOHOL_FLUID.get(),1),10,200,RecipeRegister.FUEL_RECIPE_SERIALIZER.get())
                .save(consumer,new ResourceLocation(FlameReaction.MOD_ID,"fuel/impure_alcohol"));
        new FuelRecipeBuilder(new ItemStack(Items.COAL,1),null,200,200,RecipeRegister.FUEL_RECIPE_SERIALIZER.get())
                .save(consumer,new ResourceLocation(FlameReaction.MOD_ID,"fuel/coal"));
        
        //脚手架合成
        var ironStick = ItemList.item_instance.get("iron_stick");
        ShapedRecipeBuilder.shaped(BlockList.block_instance.get(MetalScaffoldingBlock.NAME),4)
                .pattern("i i")
                .pattern(" i ")
                .pattern("i i")
                .define('i',ironStick)
                //.define('e',Ingredient.EMPTY)
                .unlockedBy(MetalScaffoldingBlock.NAME, InventoryChangeTrigger.TriggerInstance.hasItems(ironStick))
                .save(consumer,new ResourceLocation(FlameReaction.MOD_ID,"block/"+MetalScaffoldingBlock.NAME));
        
        //锻铁粒合成
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.IRON_NUGGET),
                FlameReaction.WROUGHT_IRON_NUGGET,
                20,
                500
                )
                .unlockedBy(Objects.requireNonNull(FlameReaction.WROUGHT_IRON_NUGGET.getRegistryName()).getPath(),InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_NUGGET))
                .save(consumer,new ResourceLocation(FlameReaction.MOD_ID,"cooking/"+FlameReaction.WROUGHT_IRON_NUGGET.getRegistryName().getPath()));
        
        //锻铁锭合成
        new ShapedRecipeBuilder(FlameReaction.WROUGHT_IRON_INGOT,1)
                .pattern("qqq")
                .pattern("qqq")
                .pattern("qqq")
                .define('q',FlameReaction.WROUGHT_IRON_NUGGET)
                .unlockedBy(Objects.requireNonNull(FlameReaction.WROUGHT_IRON_INGOT.getRegistryName()).getPath(),InventoryChangeTrigger.TriggerInstance.hasItems(FlameReaction.WROUGHT_IRON_NUGGET))
                .save(consumer,new ResourceLocation(FlameReaction.MOD_ID,"item/"+FlameReaction.WROUGHT_IRON_INGOT.getRegistryName().getPath()));
        //锻铁棍
        new ShapedRecipeBuilder(FlameReaction.WROUGHT_IRON_STICK,2)
                .pattern("i")
                .pattern("i")
                .define('i',FlameReaction.WROUGHT_IRON_INGOT)
                .unlockedBy(Objects.requireNonNull(FlameReaction.WROUGHT_IRON_STICK.getRegistryName()).getPath(),InventoryChangeTrigger.TriggerInstance.hasItems(FlameReaction.WROUGHT_IRON_INGOT))
                .save(consumer,new ResourceLocation(FlameReaction.MOD_ID,"item/"+FlameReaction.WROUGHT_IRON_STICK.getRegistryName().getPath()));
    
        //展览方块
        var exhibit_block = BlockList.block_instance.get(ExhibitBlock.NAME);
        new ShapedRecipeBuilder(exhibit_block,1)
                .pattern("q q")
                .pattern(" i ")
                .pattern("q q")
                .define('q',FlameReaction.WROUGHT_IRON_INGOT)
                .define('i',Blocks.TINTED_GLASS)
                .unlockedBy(Objects.requireNonNull(exhibit_block.getRegistryName()).getPath(),InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.TINTED_GLASS))
                .save(consumer,new ResourceLocation(FlameReaction.MOD_ID,"itme/"+exhibit_block.getRegistryName().getPath()));
    
        //鞍的合成
        new ShapedRecipeBuilder(FlameReaction.UNIVERSAL_SADDLE,1)
                .pattern("ii")
                .pattern("qw")
                .define('i',ItemList.item_instance.get("iron_plate"))
                .define('q',ItemList.item_instance.get("iron_stick"))
                .define('w',Items.SADDLE)
                .unlockedBy(Objects.requireNonNull(FlameReaction.UNIVERSAL_SADDLE.getRegistryName()).getPath(),InventoryChangeTrigger.TriggerInstance.hasItems(Items.SADDLE))
                .save(consumer,new ResourceLocation(FlameReaction.MOD_ID,"item/"+ FlameReaction.UNIVERSAL_SADDLE.getRegistryName().getPath()));
    
        //锤子合成
        new ShapedRecipeBuilder(FlameReaction.HAMMER,1)
                .pattern("qq ")
                .pattern("qqi")
                .pattern("qq ")
                .define('q',FlameReaction.WROUGHT_IRON_INGOT)
                .define('i',Items.STICK)
                .unlockedBy(Objects.requireNonNull(FlameReaction.HAMMER.getRegistryName()).getPath(),InventoryChangeTrigger.TriggerInstance.hasItems(FlameReaction.WROUGHT_IRON_INGOT))
                .save(consumer,new ResourceLocation(FlameReaction.MOD_ID,"item/"+FlameReaction.HAMMER.getRegistryName().getPath()));
        
        //钳子合成
        new ShapedRecipeBuilder(FlameReaction.PLIERS,1)
                .pattern(" i ")
                .pattern("iwi")
                .pattern("wi ")
                .define('i',FlameReaction.WROUGHT_IRON_STICK)
                .define('w',FlameReaction.WROUGHT_IRON_INGOT)
                .unlockedBy(Objects.requireNonNull(FlameReaction.PLIERS.getRegistryName()).getPath(),InventoryChangeTrigger.TriggerInstance.hasItems(FlameReaction.WROUGHT_IRON_INGOT))
                .save(consumer,new ResourceLocation(FlameReaction.MOD_ID,"item/"+FlameReaction.PLIERS.getRegistryName().getPath()));
        
        //扳手合成
        new ShapedRecipeBuilder(FlameReaction.WRENCH,1)
                .pattern("i i")
                .pattern("iii")
                .pattern(" i ")
                .define('i',FlameReaction.WROUGHT_IRON_INGOT)
                .unlockedBy(Objects.requireNonNull(FlameReaction.WRENCH.getRegistryName()).getPath(),InventoryChangeTrigger.TriggerInstance.hasItems(FlameReaction.WROUGHT_IRON_INGOT))
                .save(consumer,new ResourceLocation(FlameReaction.MOD_ID,"item/"+FlameReaction.WRENCH.getRegistryName().getPath()));
        
        //酿造桶合成
        new ShapedRecipeBuilder(FlameReaction.BREWING_BARREL,1)
                .pattern(" i ")
                .pattern("iwi")
                .pattern(" i ")
                .define('i',ItemList.item_instance.get("iron_plate"))
                .define('w',Blocks.COMPOSTER)
                .unlockedBy(Objects.requireNonNull(FlameReaction.BREWING_BARREL.getRegistryName()).getPath(),InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.COMPOSTER))
                .save(consumer,new ResourceLocation(FlameReaction.MOD_ID,"block/"+FlameReaction.BREWING_BARREL.getRegistryName().getPath()));
        
        //浸洗盆
        new ShapedRecipeBuilder(FlameReaction.DIPPINGBLOCK,1)
                .pattern("i i")
                .pattern("iii")
                .define('i',ItemList.item_instance.get("iron_plate"))
                .unlockedBy(Objects.requireNonNull(FlameReaction.DIPPINGBLOCK.getRegistryName()).getPath(),InventoryChangeTrigger.TriggerInstance.hasItems(ItemList.item_instance.get("iron_plate")))
                .save(consumer,new ResourceLocation(FlameReaction.MOD_ID,"block/"+FlameReaction.DIPPINGBLOCK.getRegistryName().getPath()));
    
        //固体燃烧室
        new ShapedRecipeBuilder(FlameReaction.SOLID_FUEL_BURNING_BOX,1)
                .pattern("iii")
                .pattern("qwq")
                .pattern("qeq")
                .define('i',ItemList.item_instance.get("copper_plate"))
                .define('q',Blocks.STONE_BRICKS)
                .define('w',Blocks.FURNACE)
                .define('e',Blocks.IRON_BLOCK)
                .unlockedBy(Objects.requireNonNull(FlameReaction.SOLID_FUEL_BURNING_BOX.getRegistryName()).getPath(),InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.FURNACE))
                .save(consumer,new ResourceLocation(FlameReaction.MOD_ID,"block/"+FlameReaction.SOLID_FUEL_BURNING_BOX.getRegistryName().getPath()));
    
        //液体燃烧室
        new ShapedRecipeBuilder(FlameReaction.FLUID_FUEL_BURNING_BOX,1)
                .pattern("iii")
                .pattern("qwq")
                .pattern("qeq")
                .define('i',ItemList.item_instance.get("copper_plate"))
                .define('q',Blocks.STONE_BRICKS)
                .define('w',Blocks.FURNACE)
                .define('e',Blocks.COPPER_BLOCK)
                .unlockedBy(Objects.requireNonNull(FlameReaction.FLUID_FUEL_BURNING_BOX.getRegistryName()).getPath(),InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.FURNACE))
                .save(consumer,new ResourceLocation(FlameReaction.MOD_ID,"block/"+FlameReaction.FLUID_FUEL_BURNING_BOX.getRegistryName().getPath()));
    
        //原材料包
        new ShapedRecipeBuilder(FlameReaction.GIFT1,1)
                .pattern("iii")
                .pattern("iwi")
                .pattern("iii")
                .define('i',Ingredient.of(Blocks.STONE,Blocks.DEEPSLATE, Blocks.GRANITE,Blocks.DIORITE,Blocks.ANDESITE))
                .define('w',Items.IRON_NUGGET)
                .unlockedBy(Objects.requireNonNull(FlameReaction.GIFT1.getRegistryName()).getPath(),InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_NUGGET))
                .save(consumer,new ResourceLocation(FlameReaction.MOD_ID,"item/"+FlameReaction.GIFT1.getRegistryName().getPath()));
        
        new ShapedRecipeBuilder(FlameReaction.GIFT2,1)
                .pattern("iii")
                .pattern("iwi")
                .pattern("iii")
                .define('i',Blocks.NETHERRACK)
                .define('w',FlameReaction.GIFT1)
                .unlockedBy(Objects.requireNonNull(FlameReaction.GIFT2.getRegistryName()).getPath(),InventoryChangeTrigger.TriggerInstance.hasItems(FlameReaction.GIFT1))
                .save(consumer,new ResourceLocation(FlameReaction.MOD_ID,"item/"+FlameReaction.GIFT2.getRegistryName().getPath()));
    
        new ShapedRecipeBuilder(FlameReaction.GIFT3,1)
                .pattern("iii")
                .pattern("iwi")
                .pattern("iii")
                .define('i',Blocks.END_STONE)
                .define('w',FlameReaction.GIFT2)
                .unlockedBy(Objects.requireNonNull(FlameReaction.GIFT3.getRegistryName()).getPath(),InventoryChangeTrigger.TriggerInstance.hasItems(FlameReaction.GIFT2))
                .save(consumer,new ResourceLocation(FlameReaction.MOD_ID,"item/"+FlameReaction.GIFT3.getRegistryName().getPath()));
    
        //热能发电机合成
        new ShapedRecipeBuilder(FlameReaction.HEAT_FE_GENERATOR,1)
                .pattern("iei")
                .pattern("www")
                .pattern("iii")
                .define('i',ItemList.item_instance.get("copper_plate"))
                .define('w',Blocks.REDSTONE_BLOCK)
                .define('e',ItemList.item_instance.get("iron_plate"))
                .unlockedBy(Objects.requireNonNull(FlameReaction.HEAT_FE_GENERATOR.getRegistryName()).getPath(),InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.REDSTONE_BLOCK))
                .save(consumer,new ResourceLocation(FlameReaction.MOD_ID,"item/"+FlameReaction.HEAT_FE_GENERATOR.getRegistryName().getPath()));
    
        //集热塔核心合成
        new ShapedRecipeBuilder(FlameReaction.SOLAR_COLLECTOR_TOWER_CENTER,1)
                .pattern("iii")
                .pattern(" w ")
                .pattern("iii")
                .define('i',ItemList.item_instance.get("copper_plate"))
                .define('w',Blocks.COPPER_BLOCK)
                .unlockedBy(Objects.requireNonNull(FlameReaction.SOLAR_COLLECTOR_TOWER_CENTER.getRegistryName()).getPath(),InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.COPPER_BLOCK))
                .save(consumer,new ResourceLocation(FlameReaction.MOD_ID,"item/"+FlameReaction.SOLAR_COLLECTOR_TOWER_CENTER.getRegistryName().getPath()));
    
    
        //反射镜合成
        new ShapedRecipeBuilder(FlameReaction.REFLECTOR,3)
                .pattern("iii")
                .pattern("qqq")
                .pattern("www")
                .define('i',Items.AMETHYST_SHARD)
                .define('q',Blocks.GLASS_PANE)
                .define('w',ItemList.item_instance.get("iron_plate"))
                .unlockedBy(Objects.requireNonNull(FlameReaction.REFLECTOR.getRegistryName()).getPath(),InventoryChangeTrigger.TriggerInstance.hasItems(Blocks.GLASS_PANE))
                .save(consumer,new ResourceLocation(FlameReaction.MOD_ID,"item/"+FlameReaction.REFLECTOR.getRegistryName().getPath()));
    
        //反射器合成
        new ShapedRecipeBuilder(FlameReaction.SOLAR_REFLECTOR,1)
                .pattern("w")
                .pattern("i")
                .pattern("q")
                .define('w',FlameReaction.REFLECTOR)
                .define('i',FlameReaction.WROUGHT_IRON_STICK)
                .define('q',FlameReaction.WROUGHT_IRON_INGOT)
                .unlockedBy(Objects.requireNonNull(FlameReaction.SOLAR_REFLECTOR.getRegistryName()).getPath(),InventoryChangeTrigger.TriggerInstance.hasItems(FlameReaction.REFLECTOR))
                .save(consumer,new ResourceLocation(FlameReaction.MOD_ID,"item/"+FlameReaction.SOLAR_REFLECTOR.getRegistryName().getPath()));
    
        //图标合成
        new ShapedRecipeBuilder(FlameReaction.ICON,1)
                .pattern("i")
                .pattern("w")
                .define('i',ItemList.item_instance.get("rainbow_ingot"))
                .define('w',FlameReaction.ALCOHOL_LAMP)
                .unlockedBy(Objects.requireNonNull(FlameReaction.ICON.getRegistryName()).getPath(),InventoryChangeTrigger.TriggerInstance.hasItems(FlameReaction.ALCOHOL_LAMP))
                .save(consumer,new ResourceLocation(FlameReaction.MOD_ID,"item/"+FlameReaction.ICON.getRegistryName().getPath()));
    
    }
}
