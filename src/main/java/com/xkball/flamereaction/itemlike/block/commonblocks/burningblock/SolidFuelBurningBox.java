package com.xkball.flamereaction.itemlike.block.commonblocks.burningblock;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.crafting.FuelRecipe;
import com.xkball.flamereaction.creativemodetab.CreativeModeTabs;
import com.xkball.flamereaction.eventhandler.register.BlockEntityRegister;
import com.xkball.flamereaction.eventhandler.register.RecipeRegister;
import com.xkball.flamereaction.itemlike.block.blockentity.burningblockentity.SolidFuelBurningBoxBlockEntity;
import com.xkball.flamereaction.util.ItemList;
import com.xkball.flamereaction.util.LevelUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Random;

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
        add();
    }
    
    public void regItemBlock(){
        var bi = new BlockItem(this,new Item.Properties().fireResistant().tab(CreativeModeTabs.FLAME_REACTION_GROUP)){
            @Override
            public void appendHoverText(@NotNull ItemStack p_40572_, @Nullable Level p_40573_, @NotNull List<Component> components, @NotNull TooltipFlag p_40575_) {
                super.appendHoverText(p_40572_, p_40573_, components, p_40575_);
                components.add(AbstractBurningBlock.tooltip1.withStyle(ChatFormatting.GRAY));
                components.add(AbstractBurningBlock.tooltip2.withStyle(ChatFormatting.GRAY));
            }
        };
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
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }
    
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
        return level.isClientSide() ? null:createTickerHelper(blockEntityType, BlockEntityRegister.SOLID_FUEL_BURNING_BOX_BLOCK_ENTITY.get(),SolidFuelBurningBoxBlockEntity::serverTick);
    }
    
    @Override
    public @NotNull List<String> getInfo(ServerLevel level, BlockPos pos) {
        var entity = level.getBlockEntity(pos);
        if(entity instanceof SolidFuelBurningBoxBlockEntity solidFuelBurningBoxBlockEntity){
            var fuel = solidFuelBurningBoxBlockEntity.getItems().toString();
            var max = solidFuelBurningBoxBlockEntity.getMaxHeatProduce();
            return List.of(NAME,
                    "燃料: "+fuel,
                    "最大产热: "+max);
        }
        
        return List.of(NAME);
    }
    
    @Override
    public @NotNull String getChineseTranslate() {
        return "固体燃烧室";
    }
    
    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos blockPos, @NotNull BlockState blockState) {
        return new SolidFuelBurningBoxBlockEntity(blockPos,blockState);
    }
    
    
    @Override
    @SuppressWarnings("deprecation")
    public void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull Random random) {
        if(state.is(FlameReaction.SOLID_FUEL_BURNING_BOX)){
            state = state.setValue(FIRED,Boolean.FALSE);
            level.setBlock(pos,state,Block.UPDATE_ALL);
        }
    }
    
    @Override
    @SuppressWarnings("deprecation")
    public void onRemove(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pNewState, boolean pIsMoving) {
        if(!pLevel.isClientSide && pState.getBlock() != pNewState.getBlock()){
            var entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof SolidFuelBurningBoxBlockEntity entity1){
                LevelUtil.addItem((ServerLevel) pLevel,pPos,entity1.getItems());
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }
    
    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos pos, @NotNull Block block, @NotNull BlockPos pos1, boolean b) {
        var face = blockState.getValue(SolidFuelBurningBox.FACING);
        if(!level.isClientSide&&
                blockState.is(FlameReaction.SOLID_FUEL_BURNING_BOX)&&
                pos.relative(face).equals(pos1)
        ){
           var entity = level.getBlockEntity(pos);
            if(entity instanceof SolidFuelBurningBoxBlockEntity entity1
                && level.getBlockState(pos1).isFaceSturdy(level,pos1,face.getOpposite())){
                var i = entity1.getTimeLast();
                if (!level.isClientSide()) {
                    level.scheduleTick(pos, this, i>0?1:i);
                }
            }
        }
        
        //super.neighborChanged(blockState, level, pos, block, pos1, b);
    }
    
    @Override
    @SuppressWarnings("deprecation")
    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos,
                                          @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult blockHitResult) {
        if(!level.isClientSide &&
                state.is(FlameReaction.SOLID_FUEL_BURNING_BOX) &&
                level.getBlockEntity(pos) instanceof SolidFuelBurningBoxBlockEntity sB &&
                sB.getCoolDown()<=0)
        {
            var item = player.getItemInHand(hand);
            //点火
            if(item.is(Items.FLINT_AND_STEEL) ){
                item.hurtAndBreak(1, player, (player1) -> player1.broadcastBreakEvent(hand));
                level.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                level.setBlock(pos, state.setValue(SolidFuelBurningBox.FIRED, true), 11);
                level.gameEvent(player, GameEvent.BLOCK_PLACE, pos);
                return InteractionResult.SUCCESS;
            }
            //塞东西
            else {
                if( blockHitResult.getDirection() == state.getValue(SolidFuelBurningBox.FACING)){
                    var recipe = level.getRecipeManager().getAllRecipesFor(RecipeRegister.FUEL_RECIPE_TYPE.get());
                    var list = recipe.stream().filter(Objects::nonNull).map(FuelRecipe::getItemFuel).map(ItemStack::getItem).toList();
                    if(list.contains(item.getItem())){
                        if(LevelUtil.ioWithBlock((ServerLevel) level,pos,player,item,state.getValue(SolidFuelBurningBox.FACING))) return InteractionResult.SUCCESS;
                    
                    }
                }
            }
        }
        
        return InteractionResult.PASS;
    }
}
