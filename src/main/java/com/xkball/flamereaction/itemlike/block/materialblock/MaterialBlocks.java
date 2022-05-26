package com.xkball.flamereaction.itemlike.block.materialblock;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.itemlike.item.ItemList;
import com.xkball.flamereaction.itemgroup.Groups;
import com.xkball.flamereaction.part.material.IMaterial;
import com.xkball.flamereaction.util.MaterialType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class MaterialBlocks extends MaterialBlock{
    public MaterialBlocks(IMaterial material){
        super(BlockBehaviour.Properties
                .of(Material.METAL)
                .requiresCorrectToolForDrops()
                        .strength(2f,8f)
                ,material);
        this.setRegistryName(FlameReaction.MOD_ID,material.getName()+"_block");
        add();
        regItemBlock();
    }
    
    public void regItemBlock(){
        var bi = new BlockItem(this,new Item.Properties().fireResistant().tab(Groups.MATERIAL_GROUP));
        bi.setRegistryName(FlameReaction.MOD_ID,this.getMaterial().getName()+"_block");
        ItemList.addItem(bi);
    }
    
    @Override
    public MaterialType getMaterialKind(){
        return MaterialType.BLOCK;
    }
}
