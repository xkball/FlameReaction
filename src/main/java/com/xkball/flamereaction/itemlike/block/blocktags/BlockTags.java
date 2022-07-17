package com.xkball.flamereaction.itemlike.block.blocktags;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.part.material.BasicMaterial;
import com.xkball.flamereaction.part.material.IMaterial;
import com.xkball.flamereaction.util.MaterialType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.HashMap;

public class BlockTags {
    
    public static final HashMap<String, TagKey<Block>> blockTagsList = new HashMap<>();
    public static final TagKey<Block> COLORED_FLAMMABLE = tag("flammable");
    public static final TagKey<Block> MINEABLE_PLIERS = tag("mineable/pliers");
    public static final TagKey<Block> MINEABLE_WRENCH = tag("mineable_wrench");
    public static final TagKey<Block> SCAFFOLDING = tag(FlameReaction.MOD_ID,"scaffolding");
    
    
    private static TagKey<Block> tag(String name)
    {
        return net.minecraft.tags.BlockTags.create(new ResourceLocation("forge", name));
    }
    
    public static TagKey<Block> tag(String modID,String string){
        return net.minecraft.tags.BlockTags.create(new ResourceLocation(modID,string));
    }
    
    public static void init(){
        if(blockTagsList.size() == 0){
            for(IMaterial material : BasicMaterial.commonMaterials){
                    addMaterialTag(MaterialType.BLOCK,material);
            }
            add(COLORED_FLAMMABLE);
            add(MINEABLE_PLIERS);
            add(MINEABLE_WRENCH);
            add(SCAFFOLDING);
        }
    }
    
    public static void addMaterialTag(MaterialType materialType,IMaterial material){
        add(tag(materialType.getName()+"/"+material.getName()));
    }
    
    public static TagKey<Block> getMaterialTag(MaterialType materialType, IMaterial material){
        return blockTagsList.get(materialType.getName()+"/"+material.getName());
    }
    
    public static void add(TagKey<Block> tagKey){
        blockTagsList.put(tagKey.location().getPath(),tagKey);
    }
    
}
