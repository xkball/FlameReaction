package com.xkball.flamereaction.itemlike.item.commonitem;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.creativemodetab.CreativeModeTabs;
import com.xkball.flamereaction.itemlike.item.FRCItem;
import com.xkball.flamereaction.part.material.FlammableChemicalMaterials;
import com.xkball.flamereaction.util.*;
import com.xkball.flamereaction.util.translateutil.TranslateUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Gift extends Item implements FRCItem {
    
    public static final String NAME1 = "material_gift_t1";
    public static final String NAME2 = "material_gift_t2";
    public static final String NAME3 = "material_gift_t3";
    
    public static final Map<Item,Integer> gift1;
    public static final Map<Item,Integer> gift2;
    public static final Map<Item,Integer> gift3;
    
    public static final TranslatableComponent TOOLTIP = TranslateUtil.create("gift_item/tooltip","右键打开(摸奖)","try right chick");
    
    private final String ch;
    private final Map<Item,Integer> gift;
    private int count;
    
    public Gift(String e, String c, Map<Item,Integer> gift) {
        super( new Item.Properties().tab(CreativeModeTabs.FLAME_REACTION_GROUP)
                .fireResistant()
                .setNoRepair()
                .rarity(Rarity.RARE));
        this.setRegistryName(new ResourceLocation(FlameReaction.MOD_ID,e));
        this.ch = c;
        this.gift = gift;
        add();
        count = count(gift);
    }
    
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        var self = pPlayer.getItemInHand(pUsedHand);
        if(!pLevel.isClientSide){
            var i = 0;
            while (i<100){
                for(Item item : gift.keySet()){
                    float rate = (float)gift.get(item)/(float) count;
                    if(MathUtil.randomBoolean(rate)){
                        self.shrink(1);
                        if(!pPlayer.addItem(new ItemStack(item))){
                            pPlayer.drop(new ItemStack(item),true);
                        }
                        return InteractionResultHolder.success(self);
                    }
                }
                i++;
            }
        }
        return InteractionResultHolder.pass(self);
    }
    
    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(TOOLTIP.withStyle(ChatFormatting.GRAY));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    
    @Override
    public @NotNull String getChineseTranslate() {
        return ch;
    }
    
    public static <K> int count(Map<K,Integer> kIntegerMap){
        int count = 0;
        for(int i:kIntegerMap.values()){
            count = count+i;
        }
        return count;
    }
    
    
    static {
        
        gift1 = new LinkedHashMap<>();
        gift2 = new LinkedHashMap<>();
        gift3 = new LinkedHashMap<>();
        
        gift1.put(ItemList.getMaterialItem(PeriodicTableOfElements.K, MaterialType.INGOT),10);
        gift1.put(ItemList.getMaterialItem(PeriodicTableOfElements.Li, MaterialType.INGOT),10);
        gift1.put(ItemList.getMaterialItem(PeriodicTableOfElements.Na, MaterialType.INGOT),10);
        gift1.put(ItemList.getMaterialItem(PeriodicTableOfElements.Ca, MaterialType.INGOT),10);
        gift1.put(ItemList.getMaterialItem(PeriodicTableOfElements.B, MaterialType.INGOT),10);
        gift1.put(ItemList.getMaterialItem(PeriodicTableOfElements.Zn, MaterialType.INGOT),10);
        gift1.put(ItemList.getMaterialItem(FlammableChemicalMaterials.COPPER_SULFATE, MaterialType.CHEMICAL),10);
        gift1.put(ItemList.getMaterialItem(FlammableChemicalMaterials.SODIUM_CHLORIDE,MaterialType.CHEMICAL),10);
        gift1.put(ItemList.getMaterialItem(FlammableChemicalMaterials.CALCIUM_SALT,MaterialType.CHEMICAL),10);
        gift1.put(ItemList.getMaterialItem(FlammableChemicalMaterials.LEAD_SALT,MaterialType.CHEMICAL),10);
        gift1.put(ItemList.getMaterialItem(FlammableChemicalMaterials.ZINC_SALT,MaterialType.CHEMICAL),10);
        gift1.put(ItemList.getMaterialItem(FlammableChemicalMaterials.IRON_SALT,MaterialType.CHEMICAL),10);
        gift1.put(ItemList.getMaterialItem(FlammableChemicalMaterials.POTASSIUM_SALT,MaterialType.CHEMICAL),10);
        
        gift2.put(ItemList.getMaterialItem(FlammableChemicalMaterials.BORIC_ACID,MaterialType.CHEMICAL),10);
        gift2.put(ItemList.getMaterialItem(FlammableChemicalMaterials.SELENIUM_DIOXIDE,MaterialType.CHEMICAL),10);
        gift2.put(ItemList.getMaterialItem(FlammableChemicalMaterials.BARIUM_SALT,MaterialType.CHEMICAL),10);
        gift2.put(ItemList.getMaterialItem(FlammableChemicalMaterials.CESIUM_SALT,MaterialType.CHEMICAL),10);
        gift2.put(ItemList.getMaterialItem(FlammableChemicalMaterials.MANGANESE_SALT,MaterialType.CHEMICAL),10);
        gift2.put(ItemList.getMaterialItem(FlammableChemicalMaterials.MOLYBDENUM_SALT,MaterialType.CHEMICAL),10);
        gift2.put(ItemList.getMaterialItem(FlammableChemicalMaterials.ANTIMONY_SALT,MaterialType.CHEMICAL),10);
        gift2.put(ItemList.getMaterialItem(FlammableChemicalMaterials.STRONTIUM_SALT,MaterialType.CHEMICAL),10);
        gift2.put(ItemList.getMaterialItem(PeriodicTableOfElements.Ba, MaterialType.INGOT),10);
        gift2.put(ItemList.getMaterialItem(PeriodicTableOfElements.Ce, MaterialType.INGOT),10);
        gift2.put(ItemList.getMaterialItem(PeriodicTableOfElements.Mn, MaterialType.INGOT),10);
        gift2.put(ItemList.getMaterialItem(PeriodicTableOfElements.Mo, MaterialType.INGOT),10);
        gift2.put(ItemList.getMaterialItem(PeriodicTableOfElements.Sb, MaterialType.INGOT),10);
        gift2.put(ItemList.getMaterialItem(PeriodicTableOfElements.Sr, MaterialType.INGOT),10);
    
        gift3.put(ItemList.getMaterialItem(PeriodicTableOfElements.Pd, MaterialType.INGOT),10);
        gift3.put(ItemList.getMaterialItem(PeriodicTableOfElements.Rb, MaterialType.INGOT),10);
        gift3.put(ItemList.getMaterialItem(PeriodicTableOfElements.Pt, MaterialType.INGOT),10);
        gift3.put(ItemList.getMaterialItem(FlammableChemicalMaterials.RUBIDIUM_SALT,MaterialType.CHEMICAL),10);
        gift3.put(ItemList.getMaterialItem(FlammableChemicalMaterials.INDIUM_SALT,MaterialType.CHEMICAL),10);
        gift3.put(ItemList.getMaterialItem(PeriodicTableOfElements.Rainbow, MaterialType.INGOT),1);
        gift3.put(ItemList.getMaterialItem(PeriodicTableOfElements.Rainbow, MaterialType.STICK),1);
        
    }
}
