package com.xkball.flamereaction.data;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.itemlike.block.BlockList;
import com.xkball.flamereaction.itemlike.block.materialblock.MaterialBlock;
import com.xkball.flamereaction.itemlike.block.materialblock.MaterialBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Objects;
import java.util.function.BiConsumer;

public class BlockModelGenerator extends BlockStateProvider {
    
    public BlockModelGenerator(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, FlameReaction.MOD_ID, exFileHelper);
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
        }
    }
    
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
        {
            
            f.tintindex(0)
                    //.cullface(direction)
                    .texture("#"+$.getName());
        };
        
    }
    public static String getName(Block block){
        return Objects.requireNonNull(block.getRegistryName()).getPath();
    }
}
