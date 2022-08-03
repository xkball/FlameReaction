package com.xkball.flamereaction.itemlike.block.commonblocks;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.creativemodetab.CreativeModeTabs;
import com.xkball.flamereaction.eventhandler.register.BlockEntityRegister;
import com.xkball.flamereaction.itemlike.block.FRCBlock;
import com.xkball.flamereaction.itemlike.block.FRCInfo;
import com.xkball.flamereaction.itemlike.block.blockentity.HeatFeGeneratorBlockEntity;
import com.xkball.flamereaction.itemlike.block.blockentity.burningblockentity.AlcoholLampBlockEntity;
import com.xkball.flamereaction.util.ItemList;
import com.xkball.flamereaction.util.translateutil.TranslateUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;

public class HeatFeGenerator extends BaseEntityBlock implements FRCBlock, FRCInfo {
    
    public static final String NAME = "heat_fe_generator";
    public static final TranslatableComponent TOOLTIP = TranslateUtil.create("heat_fe_generator.tooltip","底端吸收热量,顶端输出能量","input in bottom and output in top");
    
    public HeatFeGenerator() {
        super(Properties.of(Material.STONE)
                .sound(SoundType.STONE)
                .strength(2.5f)
                .noOcclusion());
        this.setRegistryName(new ResourceLocation(FlameReaction.MOD_ID,NAME));
        add();
        regItemBlock();
    }
    
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level pLevel, @NotNull BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide() ? null:createTickerHelper(pBlockEntityType, BlockEntityRegister.HEAT_FE_GENERATOR_BLOCK_ENTITY.get(), HeatFeGeneratorBlockEntity::tick);
        
    }
    
    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
        return RenderShape.MODEL;
    }
    
    public void regItemBlock(){
        var bi = new BlockItem(this,new Item.Properties().fireResistant().tab(CreativeModeTabs.FLAME_REACTION_GROUP)) {
            @Override
            public void appendHoverText(@Nonnull ItemStack p_41421_, @Nullable Level p_41422_,
                                        @Nonnull List<Component> components, @Nonnull TooltipFlag p_41424_) {
                components.add(TOOLTIP.withStyle(ChatFormatting.GRAY));
                
            }
        };
        bi.setRegistryName(FlameReaction.MOD_ID, NAME);
        ItemList.addItem(bi);
    }
    
    @Override
    public @NotNull List<String> getInfo(ServerLevel level, BlockPos pos) {
        var entity = level.getBlockEntity(pos);
        if(entity instanceof HeatFeGeneratorBlockEntity heatFeGeneratorBlockEntity){
            var e = heatFeGeneratorBlockEntity.getEX();
            return List.of(NAME,"能量存储: "+e);
        }
        return List.of(NAME);
    }
    
    @Override
    public @NotNull String getChineseTranslate() {
        return "热能发电机";
    }
    
    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new HeatFeGeneratorBlockEntity(pPos,pState);
    }
}
