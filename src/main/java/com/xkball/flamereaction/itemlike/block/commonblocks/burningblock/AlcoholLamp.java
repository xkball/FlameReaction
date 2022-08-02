package com.xkball.flamereaction.itemlike.block.commonblocks.burningblock;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.crafting.FuelRecipe;
import com.xkball.flamereaction.creativemodetab.CreativeModeTabs;
import com.xkball.flamereaction.eventhandler.register.BlockEntityRegister;
import com.xkball.flamereaction.eventhandler.register.FluidRegister;
import com.xkball.flamereaction.eventhandler.register.RecipeRegister;
import com.xkball.flamereaction.itemlike.block.FRCBlock;
import com.xkball.flamereaction.itemlike.block.blockentity.burningblockentity.AlcoholLampBlockEntity;
import com.xkball.flamereaction.itemlike.block.blockentity.burningblockentity.FluidFuelBurningBoxBlockEntity;
import com.xkball.flamereaction.itemlike.item.itemtags.ItemTags;
import com.xkball.flamereaction.part.material.FlammableChemicalMaterials;
import com.xkball.flamereaction.util.ItemList;
import com.xkball.flamereaction.util.LevelUtil;
import com.xkball.flamereaction.util.MaterialType;
import com.xkball.flamereaction.util.PeriodicTableOfElements;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.FluidStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class AlcoholLamp extends AbstractBurningBlock implements FRCBlock {
    
    public static final String NAME = "alcohol_lamp";
   
    public static final BooleanProperty O_COLORED = BooleanProperty.create("o_colored");
    public static final BooleanProperty HAS_IRON_STAND = BooleanProperty.create("has_iron_stand");
    
    
    public AlcoholLamp() {
        super(NAME,BlockBehaviour.Properties
                .of(Material.GLASS)
                .strength(2f,8f)
                .noOcclusion()
                .sound(SoundType.GLASS)
                .lightLevel((blockState) -> blockState.getValue(FIRED)?15:0));
        this.registerDefaultState(this.getStateDefinition().any().setValue(FIRED,Boolean.FALSE).setValue(O_COLORED,Boolean.FALSE).setValue(HAS_IRON_STAND,Boolean.FALSE));
        add();
        regItemBlock();
    }
    
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level pLevel, @NotNull BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide() ? null:createTickerHelper(pBlockEntityType, BlockEntityRegister.ALCOHOL_LAMP_BLOCK_ENTITY.get(), AlcoholLampBlockEntity::serverTick);
    
    }
    
    @Override
    @SuppressWarnings("deprecation")
    public void onRemove(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState blockState1, boolean pIsMoving) {
        if(!level.isClientSide && blockState.getValue(HAS_IRON_STAND) && !pIsMoving && !blockState.is(blockState1.getBlock())){
            LevelUtil.addItem((ServerLevel) level,pos,new ItemStack(FlameReaction.IRON_STAND,1));
        }
        super.onRemove(blockState, level, pos, blockState1, pIsMoving);
    }
    
    public void regItemBlock(){
        var bi = new BlockItem(this,new Item.Properties().fireResistant().tab(CreativeModeTabs.FLAME_REACTION_GROUP));
        bi.setRegistryName(FlameReaction.MOD_ID, NAME);
        ItemList.addItem(bi);
    }
    
    @Override
    @SuppressWarnings("deprecation")
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return box(4,0,4,12,14,12);
    }
    
    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(O_COLORED,
                HAS_IRON_STAND);
    }
    
    
    
    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new AlcoholLampBlockEntity(pos,state);
    }
    
    //左键灭火
    //见PlayerLeftClickBlockHandler类
    
    @Override
    @SuppressWarnings("deprecation")
    public @NotNull InteractionResult use(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos pos,
                                          @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult result) {
        
        //放入铁架
        //放入燃料
        //染色
        //取出铁架（用钳子
        //点火
        if(!level.isClientSide){
            var item = player.getItemInHand(hand);
            var entity = level.getBlockEntity(pos);
            
            if(item.is(FlameReaction.IRON_STAND)){
                item.shrink(1);
                blockState = blockState.setValue(HAS_IRON_STAND,Boolean.TRUE);
                level.setBlock(pos,blockState,Block.UPDATE_ALL);
                return InteractionResult.SUCCESS;
            }
            if(item.is(FlameReaction.PLIERS) && blockState.getValue(HAS_IRON_STAND)){
                blockState = blockState.setValue(HAS_IRON_STAND,Boolean.FALSE);
                level.setBlock(pos,blockState,Block.UPDATE_ALL);
                LevelUtil.addItem((ServerLevel) level,pos,new ItemStack(FlameReaction.IRON_STAND));
                return InteractionResult.SUCCESS;
            }
            
            if(entity instanceof AlcoholLampBlockEntity alcoholLampBlockEntity
                    &&(item.is(ItemTags.getMaterialTag(MaterialType.STICK, PeriodicTableOfElements.Fe))
                    || item.is(ItemTags.getMaterialTag(MaterialType.STICK,PeriodicTableOfElements.Pt)))
            && item.hasTag()
            && Objects.requireNonNull(item.getTag()).contains("color")){
                var color = item.getTag().getInt("color");
                if(color != 0){
                    alcoholLampBlockEntity.setColor(color);
                    if(color == FlammableChemicalMaterials.IRON_SALT.getColor().getRGB()
                    || color == FlammableChemicalMaterials.POTASSIUM_SALT.getColor().getRGB()){
                        blockState = blockState.setValue(O_COLORED,Boolean.FALSE);
                    }
                    else {
                        blockState = blockState.setValue(O_COLORED,Boolean.TRUE);
                    }
                    level.setBlock(pos,blockState,Block.UPDATE_ALL);
                    return InteractionResult.SUCCESS;
                }
            }
    
            //点火
            if(item.is(Items.FLINT_AND_STEEL) ){
                item.hurtAndBreak(1, player, (player1) -> player1.broadcastBreakEvent(hand));
                level.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.4F + 0.8F);
                level.setBlock(pos, blockState.setValue(AlcoholLamp.FIRED, true), 11);
                level.gameEvent(player, GameEvent.BLOCK_PLACE, pos);
                return InteractionResult.SUCCESS;
            }
            //灭火
            if(item.isEmpty() && hand == InteractionHand.MAIN_HAND){
                blockState = blockState.setValue(FIRED,Boolean.FALSE);
                level.setBlock(pos,blockState,Block.UPDATE_ALL);
            }
            //塞东西
            else {
                if(item.getItem() instanceof BucketItem bucketItem){
                    if(bucketItem.getFluid() == FluidRegister.IMPURE_ALCOHOL_FLUID.get()){
                        if(LevelUtil.ioWithBlock((ServerLevel) level,pos,player,item,null)) return InteractionResult.SUCCESS;
                    }
                }
            }
        }
        
        return super.use(blockState, level, pos, player, hand, result);
    }
    
    @Override
    public @NotNull List<String> getInfo(ServerLevel level, BlockPos pos) {
        var entity = level.getBlockEntity(pos);
        if(entity instanceof AlcoholLampBlockEntity blockEntity){
            var fuel = getFluidInfo(blockEntity.getFluid());
            var max = blockEntity.getMaxHeatProduce();
            return List.of(NAME,
                    "燃料: "+fuel,
                    "最大产热: "+max);
        }
        return List.of(NAME);
    }
    
    @Override
    public @NotNull String getChineseTranslate() {
        return "酒精灯";
    }
}
