package com.xkball.flamereaction.eventhandler.register;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.creativemodetab.CreativeModeTabs;
import com.xkball.flamereaction.itemlike.block.commonblocks.UnstableFluidBlock;
import com.xkball.flamereaction.itemlike.item.commonitem.FRCBucketItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

import static net.minecraft.world.item.Items.BUCKET;

public class FluidRegister {
    public static final ResourceLocation STILL_TEXTURE = new ResourceLocation("block/water_still");
    public static final ResourceLocation FLOWING_TEXTURE = new ResourceLocation("block/water_flow");
    public static final Color IMPURE_ALCOHOL_COLOR = new Color(200,200,200);
    
    public static final DeferredRegister<Block> FLUID_BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,FlameReaction.MOD_ID);
    public static final DeferredRegister<Item> FLUID_BUCKETS = DeferredRegister.create(ForgeRegistries.ITEMS,FlameReaction.MOD_ID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, FlameReaction.MOD_ID);
    public static final RegistryObject<LiquidBlock> IMPURE_ALCOHOL_FLUID_BLOCK = FLUID_BLOCKS.register(UnstableFluidBlock.IMPURE_ALCOHOL_FLUID_NAME+"_block",() -> new UnstableFluidBlock(FluidRegister.IMPURE_ALCOHOL_FLUID,UnstableFluidBlock.IMPURE_ALCOHOL_FLUID_NAME){
        @Override
        public @NotNull String getChineseTranslate() {
            return "不纯的酒精";
        }
    });
    public static final RegistryObject<Item> IMPURE_ALCOHOL_FLUID_BUCKET = FLUID_BUCKETS.register(FRCBucketItem.IMPURE_ALCOHOL_BUCKET,() -> new FRCBucketItem(FluidRegister.IMPURE_ALCOHOL_FLUID
            , new Item.Properties().tab(CreativeModeTabs.FLAME_REACTION_GROUP).craftRemainder(BUCKET).fireResistant().stacksTo(1),
            FRCBucketItem.IMPURE_ALCOHOL_BUCKET) {
        @Override
        public @NotNull String getChineseTranslate() {
            return "不纯的酒精桶";
        }
    });
    public static final RegistryObject<FlowingFluid> IMPURE_ALCOHOL_FLUID = FLUIDS.register("impure_alcohol_fluid", () -> new ForgeFlowingFluid.Source(FluidRegister.IMPURE_ALCOHOL_FLUID_PROPERTIES));
    public static final RegistryObject<FlowingFluid> IMPURE_ALCOHOL_FLUID_FLOWING = FLUIDS.register("impure_obsidian_fluid_flowing", () -> new ForgeFlowingFluid.Flowing(FluidRegister.IMPURE_ALCOHOL_FLUID_PROPERTIES));
    public static final ForgeFlowingFluid.Properties IMPURE_ALCOHOL_FLUID_PROPERTIES
            = new ForgeFlowingFluid.Properties(IMPURE_ALCOHOL_FLUID, IMPURE_ALCOHOL_FLUID_FLOWING, FluidAttributes.builder(STILL_TEXTURE, FLOWING_TEXTURE)
            .color(IMPURE_ALCOHOL_COLOR.getRGB()).density(4000).viscosity(4000))
            .bucket(IMPURE_ALCOHOL_FLUID_BUCKET)
            .block(IMPURE_ALCOHOL_FLUID_BLOCK)
            .slopeFindDistance(3).explosionResistance(100F);
    
    
}
