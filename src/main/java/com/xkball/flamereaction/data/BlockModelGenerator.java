package com.xkball.flamereaction.data;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.itemlike.block.commonblocks.*;
import com.xkball.flamereaction.itemlike.block.commonblocks.burningblock.AlcoholLamp;
import com.xkball.flamereaction.itemlike.block.commonblocks.burningblock.FluidFuelBurningBox;
import com.xkball.flamereaction.itemlike.block.commonblocks.burningblock.SolidFuelBurningBox;
import com.xkball.flamereaction.util.BlockList;
import com.xkball.flamereaction.itemlike.block.materialblock.MaterialBlock;
import com.xkball.flamereaction.itemlike.block.materialblock.MaterialBlocks;
import com.xkball.flamereaction.itemlike.block.materialblock.MetalScaffoldingBlock;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Objects;
import java.util.function.BiConsumer;

public class BlockModelGenerator extends BlockStateProvider {
    
    final ExistingFileHelper helper;
    
    public BlockModelGenerator(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, FlameReaction.MOD_ID, exFileHelper);
        this.helper = exFileHelper;
    }
    
    @Override
    protected void registerStatesAndModels() {
        for(Block block : BlockList.block_instance.values()){
            if(block instanceof MaterialBlocks mb){
                //this.simpleBlock(mb,
                var mod = this.materialCubeAll(mb,((MaterialBlock)block).getMaterialKind().getName());
                this.simpleBlock(block,mod);
                
                //);
                this.simpleBlockItem(mb,mod);
            }
            if(block instanceof MetalScaffoldingBlock){
                this.simpleBlockItem(block, getBlockModel("deprecated/scaffolding_common_button"));
            }
            if(block instanceof ExhibitBlock){
                this.exhibitBlock(block);
                this.simpleBlockItem(block, getBlockModel("exhibit_block_unlock"));
            }
            if(block instanceof ForgingTable){
                var model = getBlockModel("forging_table");
                this.simpleBlock(block,model);
                this.simpleBlockItem(block,model);
            }
            if(block instanceof BrewingBarrel){
                var model = getBlockModel(BrewingBarrel.NAME);
                this.simpleBlock(block,model);
                this.simpleBlockItem(block,model);
            }
            if(block instanceof DippingBlock){
                var model = getBlockModel(DippingBlock.NAME);
                this.simpleBlock(block,model);
                this.simpleBlockItem(block,model);
            }
            if(block instanceof SolidFuelBurningBox){
                var model = getBlockModel(SolidFuelBurningBox.NAME);
               // this.horizontalBlock(block,model);
                this.simpleBlockItem(block,model);
            }
            if(block instanceof FluidFuelBurningBox){
                var model = getBlockModel(FluidFuelBurningBox.NAME);
                this.simpleBlockItem(block,model);
            }
            this.simpleBlockItem(FlameReaction.ALCOHOL_LAMP,getBlockModel("alcohol_lamp_item"));
            if(block instanceof HeatFeGenerator){
                var model = getBlockModel(HeatFeGenerator.NAME);
                this.simpleBlock(block,model);
                this.simpleBlockItem(block,model);
            }
            if(block instanceof SolarCollectorTowerCenter){
                var model = getBlockModel(SolarCollectorTowerCenter.NAME);
                this.simpleBlock(block,model);
                this.simpleBlockItem(block,model);
            }
            if(block instanceof SolarReflector){
                var mod1 = getBlockModel("iron_column");
                var mod2 = getBlockModel("reflector");
                this.simpleBlock(block,mod1);
                this.simpleBlockItem(block,mod2);
            }
        }
    }
    
    
    
    //已经由手写代替
//    public void scaffoldingBlock(Block block){
//        var center = new ModelFile.ExistingModelFile(new ResourceLocation(ModelProvider.BLOCK_FOLDER+"/scaffolding_center"),helper);
//        var button = new ModelFile.ExistingModelFile(new ResourceLocation(ModelProvider.BLOCK_FOLDER+"/scaffolding_button"),helper);
//        var side = new ModelFile.ExistingModelFile(new ResourceLocation(ModelProvider.BLOCK_FOLDER+"/scaffolding_side"),helper);
//        var sideFix = new ModelFile.ExistingModelFile(new ResourceLocation(ModelProvider.BLOCK_FOLDER+"/scaffolding_side_fix"),helper);
//        var sideFixC = new ModelFile.ExistingModelFile(new ResourceLocation(ModelProvider.BLOCK_FOLDER+"/scaffolding_side_fix_x"),helper);
//        var sideConE = new ModelFile.ExistingModelFile(new ResourceLocation(ModelProvider.BLOCK_FOLDER+"/scaffolding_side_conE"),helper);
//        var sideConX = new ModelFile.ExistingModelFile(new ResourceLocation(ModelProvider.BLOCK_FOLDER+"/scaffolding_side_con"),helper);
//        var builder = getMultipartBuilder(block);
//        builder.part().modelFile(center);
//    }
    
    public ModelFile materialCubeAll(Block block,String type){
        var path = ModelProvider.BLOCK_FOLDER+"/common/"+type;
        var md = models().withExistingParent(getName(block),"block")
                .texture("particle",path)
                .texture("down", path)
                .texture("up", path)
                .texture("north", path)
                .texture("south", path)
                .texture("east", path)
                .texture("west", path);
    
        var emd = md.element();
                //.from(0,0,0)
                //.to(16,16,16)
        emd.allFaces(inFace(path));
//        for (Direction direction : Direction.values()){
//            emd.faces(inFace(direction,path));
//        }
        
        return md;
    }
    public static BiConsumer<Direction, ModelBuilder<BlockModelBuilder>.ElementBuilder.FaceBuilder> inFace(String texture) {
        return ($, f) ->
                f.tintindex(0)
                        //.cullface(direction)
                        .texture("#"+$.getName());
        
    }
    
    public void exhibitBlock(Block block){
        getVariantBuilder(block)
                .partialState().with(ExhibitBlock.IS_LOCKED,Boolean.TRUE).modelForState()
                    .modelFile(getBlockModel("exhibit_block_locked")).addModel()
                .partialState().with(ExhibitBlock.IS_LOCKED,Boolean.FALSE).modelForState()
                .modelFile(getBlockModel("exhibit_block_unlock")).addModel();
                
    }
    public static String getName(Block block){
        return Objects.requireNonNull(block.getRegistryName()).getPath();
    }
    
    public ModelFile getBlockModel(String string){
        return new ModelFile.UncheckedModelFile(new ResourceLocation(FlameReaction.MOD_ID,"block/"+string));
    }
}
