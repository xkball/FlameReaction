package com.xkball.flamereaction.itemlike.block.commonblocks.burningblock;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.creativemodetab.CreativeModeTabs;
import com.xkball.flamereaction.util.ItemList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SolidFuelBurningBox extends AbstractBurningBlock{
    
    public static final String NAME = "solid_fuel_burning_box";
    
    public static final DirectionProperty FACING = DirectionProperty.create("facing", List.of(Direction.EAST,Direction.SOUTH,Direction.WEST,Direction.NORTH));
    
    public SolidFuelBurningBox() {
        super(NAME, BlockBehaviour.Properties
                .of(Material.STONE)
                .strength(2f,8f)
                .noOcclusion()
                .sound(SoundType.STONE)
                .lightLevel((blockState) -> blockState.getValue(FIRED)?15:0));
        this.registerDefaultState(this.getStateDefinition().any().setValue(FIRED,Boolean.FALSE).setValue(FACING,Direction.EAST));
        regItemBlock();
    }
    
    public void regItemBlock(){
        var bi = new BlockItem(this,new Item.Properties().fireResistant().tab(CreativeModeTabs.FLAME_REACTION_GROUP));
        bi.setRegistryName(FlameReaction.MOD_ID, NAME);
        ItemList.addItem(bi);
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }
    
    @Nullable
    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
        var direction = context.getNearestLookingDirection();
        direction = (direction==Direction.DOWN||direction==Direction.UP) ? Direction.EAST:direction;
        var bs = super.getStateForPlacement(context);
        if (bs != null) {
            bs.setValue(FACING,direction);
        }
        return bs;
    }
    
    //todo 补完信息
    @Override
    public @NotNull List<String> getInfo(ServerLevel level, BlockPos pos) {
        return List.of(NAME);
    }
    
    @Override
    public @NotNull String getChineseTranslate() {
        return "固体燃烧室";
    }
    
    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return null;
    }
}
