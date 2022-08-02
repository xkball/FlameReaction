package com.xkball.flamereaction.itemlike.item.materialitem;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.creativemodetab.CreativeModeTabs;
import com.xkball.flamereaction.itemlike.block.commonblocks.FlameFireBlock;
import com.xkball.flamereaction.itemlike.item.itemtags.ItemTags;
import com.xkball.flamereaction.part.material.IMaterial;
import com.xkball.flamereaction.util.MaterialType;
import com.xkball.flamereaction.util.PeriodicTableOfElements;
import net.minecraft.nbt.IntTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.NotNull;

public class MaterialStick extends MaterialItem{
    public MaterialStick(IMaterial material) {
        super(new Item.Properties()
                .fireResistant()
                .setNoRepair()
                .tab(CreativeModeTabs.MATERIAL_GROUP),
                material);
        this.setRegistryName(FlameReaction.MOD_ID,material.getName()+"_stick");
        add();
    }
    
    @Override
    public MaterialType getMaterialKind() {
        return MaterialType.STICK;
    }
    
    @Override
    public @NotNull String getChineseTranslate() {
        return this.material.getSymbol()+"棍";
    }
    
    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext pContext) {
        var level = pContext.getLevel();
        var pos = pContext.getClickedPos();
        if(!level.isClientSide){
            var item = pContext.getItemInHand();
            var bs = level.getBlockState(pos);
            if(item.getItem() instanceof MaterialItem  &&
                    (item.is(ItemTags.getMaterialTag(MaterialType.STICK, PeriodicTableOfElements.Fe))
                            || item.is(ItemTags.getMaterialTag(MaterialType.STICK,PeriodicTableOfElements.Pt)))
                && bs.getBlock() instanceof FlameFireBlock) {
                var color = bs.getValue(FlameFireBlock.MATERIAL).getColor().getRGB();
                item.addTagElement("color", IntTag.valueOf(color));
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }
}
