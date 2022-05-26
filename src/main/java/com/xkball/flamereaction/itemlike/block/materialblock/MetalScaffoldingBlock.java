package com.xkball.flamereaction.itemlike.block.materialblock;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.itemgroup.Groups;
import com.xkball.flamereaction.itemlike.item.ItemList;
import com.xkball.flamereaction.itemlike.itemblock.MetalScaffoldingBlockItem;
import com.xkball.flamereaction.part.material.IMaterial;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;

public class MetalScaffoldingBlock extends MaterialBlock implements SimpleWaterloggedBlock {
    
    public static final IntegerProperty SCAFFOLDING_COUNT
            = IntegerProperty.create("scaffolding_count",0,4);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty BOTTOM = BlockStateProperties.BOTTOM;
    
    public MetalScaffoldingBlock(IMaterial material) {
        super(BlockBehaviour.Properties
                        .of(Material.METAL)
                        .strength(1f,8f)
                ,material);
        this.setRegistryName(FlameReaction.MOD_ID,material.getName()+"_scaffolding_block");
        add();
        regItemBlock();
    }
    
    public void regItemBlock(){
        var bi = new MetalScaffoldingBlockItem(this,new Item.Properties().fireResistant().tab(Groups.MATERIAL_GROUP));
        bi.setRegistryName(FlameReaction.MOD_ID,this.getMaterial().getName()+"_scaffolding_block");
        ItemList.addItem(bi);
    }
    
    @Override
    public void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder){
        builder.add(SCAFFOLDING_COUNT,BOTTOM,WATERLOGGED);
        super.createBlockStateDefinition(builder);
    }
    
    public boolean isStale(Level level, BlockPos blockPos){
        boolean flag = false;
        if(!level.isClientSide()){
            
            for(Direction direction : Direction.values()){
                BlockState bs = level.getBlockState(blockPos.mutable().move(direction));
                if(bs.getBlock() instanceof MetalScaffoldingBlock){
                    flag = true;
                    break;
                }
            }
        }
        
        return flag;
    }
    
    
}
