package com.xkball.flamereaction.itemlike.item.materialitem;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.creativemodetab.CreativeModeTabs;
import com.xkball.flamereaction.itemlike.block.commonblocks.FlameFireBlock;
import com.xkball.flamereaction.itemlike.item.ColoredFlammableItem;
import com.xkball.flamereaction.itemlike.item.FRCItem;
import com.xkball.flamereaction.part.material.FlammableChemicalMaterials;
import com.xkball.flamereaction.util.MaterialType;
import com.xkball.flamereaction.util.translateutil.TranslateUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.List;
import java.util.Objects;

public class ColoredFlameItem extends Item implements ColoredFlammableItem, FRCItem {
    
    public static final TranslatableComponent TOOLTIP = TranslateUtil.create("colored_flame_item_tooltip1","试着把它扔进火里吧！","try to throw it into fire!");
    
    private final FlammableChemicalMaterials material;
    public ColoredFlameItem(FlammableChemicalMaterials material, String name) {
        super(new Item.Properties()
                //.fireResistant()
                .setNoRepair()
                .tab(CreativeModeTabs.MATERIAL_GROUP));
        this.material = material;
        this.setRegistryName(FlameReaction.MOD_ID,name);
        add();
    }
    
    public MaterialType getMaterialKind(){
        return MaterialType.CHEMICAL;
    }
    
    public FlammableChemicalMaterials getMaterial() {
        return material;
    }
    
    @Override
    public void appendHoverText(@Nonnull ItemStack p_41421_, @Nullable Level p_41422_,
                                @Nonnull List<Component> components, @Nonnull TooltipFlag p_41424_) {
        components.add(TranslateUtil.create(this.getMaterialKind().getName()+"_"+this.getMaterial().getSymbol(),
                this.getMaterialKind().getChinese()+": "+this.getMaterial().getSymbol(),
                this.getMaterialKind().getName()+": "+this.getMaterial().getSymbol() ).withStyle(ChatFormatting.GRAY));
        components.add(TOOLTIP.withStyle(ChatFormatting.GRAY));
        
    }
    
    @Override
    public void onDestroyed(ItemEntity itemEntity, DamageSource damageSource) {
        var level = itemEntity.getLevel();
        if(!level.isClientSide){
            var pos = itemEntity.getOnPos();
            var mPos = pos.mutable().move(Direction.DOWN);
            if(damageSource.isFire() && level.getBlockState(pos).is(BlockTags.FIRE) && level.getBlockState(mPos).isFaceSturdy(level,mPos,Direction.UP)){
                var blockState = FlameReaction.FLAME_FIRE_BLOCK.defaultBlockState()
                        .setValue(FlameFireBlock.IS_OBVIOUS_COLORED,this.getMaterial().isObvious())
                        .setValue(FlameFireBlock.MATERIAL,this.getMaterial());
                level.setBlock(pos,blockState, Block.UPDATE_ALL);
            }
            
        }
        super.onDestroyed(itemEntity, damageSource);
    }
    
    @Override
    public @Nonnull InteractionResult useOn(@Nonnull UseOnContext useOnContext) {
       var level = useOnContext.getLevel();
       if(!level.isClientSide){
           var pos = useOnContext.getClickedPos();
           var mPos = pos.mutable().move(Direction.DOWN);
           if(level.getBlockState(pos).is(BlockTags.FIRE) && level.getBlockState(mPos).isFaceSturdy(level,mPos,Direction.UP)){
               var itemStack = useOnContext.getItemInHand();
               var blockState = FlameReaction.FLAME_FIRE_BLOCK.defaultBlockState()
                       .setValue(FlameFireBlock.IS_OBVIOUS_COLORED,this.getMaterial().isObvious())
                       .setValue(FlameFireBlock.MATERIAL,this.getMaterial());
               level.setBlock(pos,blockState, Block.UPDATE_ALL);
               if (!Objects.requireNonNull(useOnContext.getPlayer()).getAbilities().instabuild) {
                   itemStack.shrink(1);
               }
           }
       }
       
       return InteractionResult.SUCCESS;
    }
    
    @Override
    public Color getFlamedColor() {
        return material.getColor();
    }
}

