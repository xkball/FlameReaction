package com.xkball.flamereaction.itemlike.block.commonblocks;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.eventhandler.register.BlockEntityRegister;
import com.xkball.flamereaction.creativemodetab.CreativeModeTabs;
import com.xkball.flamereaction.itemlike.block.FRCBlock;
import com.xkball.flamereaction.itemlike.block.FRCInfo;
import com.xkball.flamereaction.itemlike.block.blockentity.ExhibitBlockEntity;
import com.xkball.flamereaction.itemlike.item.commonitem.tool.ExhibitBlockKey;
import com.xkball.flamereaction.itemlike.item.commonitem.tool.Wrench;
import com.xkball.flamereaction.util.ItemList;
import com.xkball.flamereaction.util.translateutil.TranslateUtil;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ExhibitBlock extends BaseEntityBlock implements FRCBlock, FRCInfo {
    
    public static final BooleanProperty IS_LOCKED = BooleanProperty.create("is_locked");
    public static final String NAME = "exhibit_block";
    public static final TranslatableComponent EXHIBIT_SUCCEED = new TranslatableComponent(TranslateUtil.PREFIX+"exhibit_block_tip1");
    public static final TranslatableComponent EXHIBIT_CLEAR = new TranslatableComponent(TranslateUtil.PREFIX+"exhibit_block_tip2");
    public static final TranslatableComponent EXHIBIT_SET_INFO_SUCCEED = new TranslatableComponent(TranslateUtil.PREFIX+"exhibit_block_tip3");
    public static final TranslatableComponent EXHIBIT_SET_INFO_CLEAR = new TranslatableComponent(TranslateUtil.PREFIX+"exhibit_block_tip4");
    public static final TranslatableComponent EXHIBIT_LOCKED = new TranslatableComponent(TranslateUtil.PREFIX+"exhibit_block_tip5");
    public static final TranslatableComponent EXHIBIT_UNLOCKED = new TranslatableComponent(TranslateUtil.PREFIX+"exhibit_block_tip6");
    
    
    public ExhibitBlock() {
        super(BlockBehaviour.Properties
                .of(Material.GLASS)
                .strength(4f,8f)
                .noOcclusion()
                .sound(SoundType.GLASS)
                .lightLevel((blockState) -> 15));
        this.setRegistryName(FlameReaction.MOD_ID,NAME);
        this.registerDefaultState(this.getStateDefinition().any().setValue(IS_LOCKED,Boolean.FALSE));
        add();
        regItemBlock();
    }
    
    public void regItemBlock(){
        var bi = new BlockItem(this,new Item.Properties().fireResistant().tab(CreativeModeTabs.FLAME_REACTION_GROUP));
        bi.setRegistryName(FlameReaction.MOD_ID, NAME);
        ItemList.addItem(bi);
    }
  
    
    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder){
        builder.add(
                IS_LOCKED
        );
        //super.createBlockStateDefinition(builder);
    }
    
    @Nonnull
    @Override
    public RenderShape getRenderShape(@Nonnull BlockState state) {
        return RenderShape.MODEL;
    }
    
    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos blockPos, @Nonnull BlockState blockState){
        return new ExhibitBlockEntity(blockPos,blockState);
    }
    
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
        return level.isClientSide() ? createTickerHelper(blockEntityType, BlockEntityRegister.EXHIBIT_BLOCK_ENTITY.get(), ExhibitBlockEntity::tick) : null;
    }
    
    @Override
    @SuppressWarnings("deprecation")
    public @Nonnull InteractionResult use(@Nonnull BlockState blockState, @Nonnull Level level, @Nonnull BlockPos blockPos,
                                          @Nonnull Player player, @Nonnull InteractionHand interactionHand, @Nonnull BlockHitResult blockHitResult){
        var block_entity = (ExhibitBlockEntity)level.getBlockEntity(blockPos);
        if(!level.isClientSide && block_entity != null){
            //var block_entity = (ExhibitBlockEntity)level.getBlockEntity(blockPos);
            //在副手
            
//            if(interactionHand == InteractionHand.OFF_HAND && player.getOffhandItem().getItem() instanceof ExhibitBlockKey){
//                block_entity.setLikeItemEntity(!block_entity.isLikeItemEntity());
//            }
            //在主手
            var item_input = player.getMainHandItem();
            if(interactionHand == InteractionHand.MAIN_HAND){
                //改变状态
                if(item_input.getItem() instanceof ExhibitBlockKey){
                    if(!player.isShiftKeyDown()){
                        blockState = blockState.cycle(IS_LOCKED);
                        var b = level.setBlock(blockPos,blockState,Block.UPDATE_CLIENTS);
                        if (b && blockState.getValue(IS_LOCKED)) {
                            ((ServerPlayer) player).sendMessage(EXHIBIT_LOCKED, ChatType.GAME_INFO, Util.NIL_UUID);
                        } else {
                            ((ServerPlayer) player).sendMessage(EXHIBIT_UNLOCKED, ChatType.GAME_INFO, Util.NIL_UUID);
                        }
                        return b?InteractionResult.SUCCESS:InteractionResult.FAIL;
                    }
//                    if( player.isShiftKeyDown() && block_entity.getItem().getItem() != Items.AIR){
//                        block_entity.setLikeItemEntity(!block_entity.isLikeItemEntity());
//                        return InteractionResult.SUCCESS;
//                    }
                    
                }
                if(blockState.getValue(IS_LOCKED)){
                    if(item_input.getItem() instanceof Wrench) return InteractionResult.PASS;
                    var s = block_entity.getIntroduction();
                    if(s != null) ((ServerPlayer)player).sendMessage(Component.nullToEmpty(s), ChatType.CHAT, Util.NIL_UUID);
                    return InteractionResult.SUCCESS;
                }
                //逐级取消
                if(!blockState.getValue(IS_LOCKED) && item_input.is(Items.AIR) ){
                    if(block_entity.getIntroduction() != null){
                        block_entity.setIntroduction(null);
                        ((ServerPlayer)player).sendMessage(EXHIBIT_SET_INFO_CLEAR, ChatType.GAME_INFO, Util.NIL_UUID);
                        return InteractionResult.SUCCESS;
                    }
                    if(block_entity.getItem().getItem() !=  Items.AIR){
                        block_entity.input(ItemStack.EMPTY,null);
                        ((ServerPlayer)player).sendMessage(EXHIBIT_CLEAR, ChatType.GAME_INFO, Util.NIL_UUID);
                        return InteractionResult.SUCCESS;
                    }
                    //block_entity.clearItem();
                }
                //逐级设置
                if(!blockState.getValue(IS_LOCKED) && !item_input.is(Items.AIR)){
                    if(block_entity.getItem().getItem() == Items.AIR || block_entity.getItem() == ItemStack.EMPTY){
                        var dir = blockHitResult.getDirection().getAxis();
                        var item = new ItemStack(item_input.getItem(),1);
                        block_entity.input(item,dir);
                        ((ServerPlayer)player).sendMessage(EXHIBIT_SUCCEED, ChatType.GAME_INFO, Util.NIL_UUID);
                        return InteractionResult.SUCCESS;
                    }
                    if(block_entity.getIntroduction() == null){
                        block_entity.setIntroduction(item_input.getDisplayName().getString());
                        ((ServerPlayer)player).sendMessage(EXHIBIT_SET_INFO_SUCCEED, ChatType.GAME_INFO, Util.NIL_UUID);
                    }
                    return InteractionResult.SUCCESS;
    
                }
                //LogUtils.getLogger().info("233  "+ item_input.toString());
            }
        }
        return InteractionResult.PASS;
    }
    
    @Override
    @SuppressWarnings("deprecation")
    public @Nonnull VoxelShape getVisualShape(@Nonnull BlockState p_60479_, @Nonnull BlockGetter p_60480_,@Nonnull BlockPos  p_60481_,@Nonnull CollisionContext p_60482_) {
        return Shapes.empty();
    }
    
    
    @Override
    public @NotNull List<String> getInfo(ServerLevel level, BlockPos pos) {
        var bs = level.getBlockState(pos);
        var entity = level.getBlockEntity(pos);
        
        if(entity instanceof ExhibitBlockEntity entity1){
            String state = "是否上锁: "+ bs.getValue(IS_LOCKED);
            String item = "展示物品: "+ entity1.getItem();
            String axis = "显示轴向: "+ entity1.getAxis();
            String mode =  "显示模式: " +(entity1.isLikeItemEntity()?"活动":"静止");
            String info = "介绍信息: "+entity1.getIntroduction();
            return List.of(NAME,state,item,axis,mode,info);
        }
        
        return List.of(NAME);
    }
    
    @Override
    public @NotNull String getChineseTranslate() {
        return "展示方块";
    }
}
