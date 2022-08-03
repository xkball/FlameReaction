package com.xkball.flamereaction.itemlike.block.commonblocks;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.creativemodetab.CreativeModeTabs;
import com.xkball.flamereaction.eventhandler.register.BlockEntityRegister;
import com.xkball.flamereaction.itemlike.block.FRCBlock;
import com.xkball.flamereaction.itemlike.block.FRCInfo;
import com.xkball.flamereaction.itemlike.block.blockentity.HeatFeGeneratorBlockEntity;
import com.xkball.flamereaction.itemlike.block.blockentity.SolarReflectorBlockEntity;
import com.xkball.flamereaction.util.ItemList;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SolarReflector extends BaseEntityBlock implements FRCBlock, FRCInfo {
    
    public static final String NAME = "solar_reflector";
    
    public SolarReflector() {
        super(Properties.of(Material.STONE)
                .sound(SoundType.STONE)
                .strength(2.5f)
                .noOcclusion()
                .lightLevel((bs) -> 15));
        this.setRegistryName(new ResourceLocation(FlameReaction.MOD_ID,NAME));
        add();
        regItemBlock();
    }
    
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level pLevel, @NotNull BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide() ? null:createTickerHelper(pBlockEntityType, BlockEntityRegister.SOLAR_REFLECTOR_BLOCK_ENTITY.get(), SolarReflectorBlockEntity::tick);
        
    }
    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
        return RenderShape.MODEL;
    }
    
    @Override
    @SuppressWarnings("deprecation")
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return box(6,0,6,10,24,10);
    }
    
    public void regItemBlock(){
        var bi = new BlockItem(this,new Item.Properties().fireResistant().tab(CreativeModeTabs.FLAME_REACTION_GROUP));
        bi.setRegistryName(FlameReaction.MOD_ID, NAME);
        ItemList.addItem(bi);
    }
    
    @Override
    public @NotNull List<String> getInfo(ServerLevel level, BlockPos pos) {
        var entity = level.getBlockEntity(pos);
        if( entity instanceof SolarReflectorBlockEntity se){
            var xyz = se.getPX()+" "+se.getPY()+" "+se.getPZ();
            var light = level.getBrightness(LightLayer.SKY,pos);
            return List.of(NAME,"指向位置: "+xyz,"产热: "+light);
        }
        return List.of(NAME);
    }
    
    @Override
    public @NotNull String getChineseTranslate() {
        return "太阳能反射镜";
    }
    
    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new SolarReflectorBlockEntity(pPos,pState);
    }
}
