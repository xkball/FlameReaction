package com.xkball.flamereaction.itemlike.block.commonblocks;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.creativemodetab.CreativeModeTabs;
import com.xkball.flamereaction.eventhandler.register.BlockEntityRegister;
import com.xkball.flamereaction.itemlike.block.FRCBlock;
import com.xkball.flamereaction.itemlike.block.FRCInfo;
import com.xkball.flamereaction.itemlike.block.blockentity.HeatFeGeneratorBlockEntity;
import com.xkball.flamereaction.itemlike.block.blockentity.SolarCollectorTowerCenterBlockEntity;
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

public class SolarCollectorTowerCenter extends BaseEntityBlock implements FRCBlock, FRCInfo {
    
    public static final String NAME = "solar_collector_tower_center";
    public static final TranslatableComponent TOOLTIP = TranslateUtil.create("solar_collector_tower_center.tooltip","使用扳手右键连接集热塔核心与反射镜","Use the wrench to connect the tower center and the solar reflector");
    
    public SolarCollectorTowerCenter() {
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
        return pLevel.isClientSide() ? null:createTickerHelper(pBlockEntityType, BlockEntityRegister.SOLAR_COLLECTOR_TOWER_CENTER_BLOCK_ENTITY.get(), SolarCollectorTowerCenterBlockEntity::tick);
        
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
        if(entity instanceof SolarCollectorTowerCenterBlockEntity se){
            var heatBuf = se.getHeatBuf().toString();
            return List.of(NAME,"当前接受热量: "+heatBuf);
        }
        return List.of(NAME);
    }
    
    @Override
    public @NotNull String getChineseTranslate() {
        return "太阳能集热塔核心";
    }
    
    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new SolarCollectorTowerCenterBlockEntity(pPos,pState);
    }
}
