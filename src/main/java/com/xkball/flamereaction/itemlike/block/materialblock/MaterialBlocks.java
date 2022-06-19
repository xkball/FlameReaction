package com.xkball.flamereaction.itemlike.block.materialblock;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.util.ItemList;
import com.xkball.flamereaction.itemgroup.Groups;
import com.xkball.flamereaction.part.material.IMaterial;
import com.xkball.flamereaction.util.MaterialType;
import com.xkball.flamereaction.util.translateutil.TranslateUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;

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
        var bi = new BlockItem(this,new Item.Properties().fireResistant().tab(Groups.MATERIAL_GROUP)){
            @Override
            public void appendHoverText(@Nonnull ItemStack p_41421_, @Nullable Level p_41422_,
                                        @Nonnull List<Component> components, @Nonnull TooltipFlag p_41424_) {
                components.add(TranslateUtil.create("material: "+getMaterial().getSymbol(),"材料: "+getMaterial().getSymbol() ).withStyle(ChatFormatting.GRAY));
        
            }
        };
        bi.setRegistryName(FlameReaction.MOD_ID,this.getMaterial().getName()+"_block");
        ItemList.addItem(bi);
    }
    
    
    
    @Override
    public MaterialType getMaterialKind(){
        return MaterialType.BLOCK;
    }
}
