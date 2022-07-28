package com.xkball.flamereaction.data;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.itemlike.block.materialblock.MaterialBlock;
import com.xkball.flamereaction.util.BlockList;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;


public class BlockTagGenerator extends BlockTagsProvider {
    
    public BlockTagGenerator(DataGenerator p_126511_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_126511_, FlameReaction.MOD_ID, existingFileHelper);
    }
    
    
    @Override
    public void addTags(){
        var scaffolding = BlockList.block_instance.get("iron_scaffolding_block");
        this.tag(BlockTags.CLIMBABLE).add(scaffolding);
        this.tag(BlockTags.FALL_DAMAGE_RESETTING).add(scaffolding);
        this.tag(com.xkball.flamereaction.itemlike.block.blocktags.BlockTags.COLORED_FLAMMABLE)
                .add(BlockList.block_instance.get("platinum_block"))
                .add(Blocks.IRON_BLOCK);
        var materialList = BlockList.block_instance.values().stream().filter(block -> block instanceof MaterialBlock).toList();
        for(Block block : materialList){
            addDigInPickAxe(block);
        }
        this.tag(com.xkball.flamereaction.itemlike.block.blocktags.BlockTags.SCAFFOLDING).add(scaffolding);
        this.tag(com.xkball.flamereaction.itemlike.block.blocktags.BlockTags.MINEABLE_WRENCH).add(scaffolding)
                .add(FlameReaction.FLUID_FUEL_BURNING_BOX)
                .add(FlameReaction.SOLID_FUEL_BURNING_BOX)
                .add(FlameReaction.DIPPINGBLOCK);
        addDigInPickAxe(FlameReaction.DIPPINGBLOCK);
        
    }
    
    public void addDigInPickAxe(Block block){
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block);
    }
}
