package com.xkball.flamereaction.itemlike.block.commonblocks;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.creativemodetab.CreativeModeTabs;
import com.xkball.flamereaction.itemlike.block.FRCBlock;
import com.xkball.flamereaction.itemlike.block.FRCInfo;
import com.xkball.flamereaction.itemlike.block.blockentity.ForgingTableBlockEntity;
import com.xkball.flamereaction.itemlike.item.itemtags.ItemTags;
import com.xkball.flamereaction.util.ItemList;
import com.xkball.flamereaction.util.LevelUtil;
import com.xkball.flamereaction.util.translateutil.TranslateUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;

public class ForgingTable extends BaseEntityBlock implements FRCBlock, FRCInfo {
    
    public static final String NAME = "forging_table";
    public static final TranslatableComponent TOOLTIP = TranslateUtil.create("forging_table_tooltip","不能输出至漏斗","cannot put out items to hopper" );
    public static Item BLOCK_ITEM;
    
    public ForgingTable() {
        super(Properties.of(Material.WOOD)
                .sound(SoundType.WOOD)
                .strength(2.5f)
                .noOcclusion());
        this.setRegistryName(new ResourceLocation(FlameReaction.MOD_ID,NAME));
        regItemBlock();
        add();
    }
    
    public void regItemBlock(){
        var bi = new BlockItem(this,new Item.Properties().fireResistant().tab(CreativeModeTabs.FLAME_REACTION_GROUP)) {
            @Override
            public void appendHoverText(@Nonnull ItemStack p_41421_, @Nullable Level p_41422_,
                                        @Nonnull List<Component> components, @Nonnull TooltipFlag p_41424_) {
                components.add(TOOLTIP.withStyle(ChatFormatting.RED));
        
            }
        };
        bi.setRegistryName(FlameReaction.MOD_ID, NAME);
        ItemList.addItem(bi);
        BLOCK_ITEM = bi;
    }
    
    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos blockPos, @Nonnull BlockState blockState){
        return new ForgingTableBlockEntity(blockPos,blockState);
    }
    
    @Nonnull
    @Override
    public RenderShape getRenderShape(@Nonnull BlockState state) {
        return RenderShape.MODEL;
    }
    
    @Override
    public boolean isPossibleToRespawnInThis() {
        return false;
    }
    
    @Override
    @SuppressWarnings("deprecation")
    public void onRemove(@NotNull BlockState bs1, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState bs2, boolean p_60519_) {
        if (!bs1.is(bs2.getBlock())) {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof ForgingTableBlockEntity) {
                Containers.dropContents(level, pos, (Container) blockentity);
                level.updateNeighbourForOutputSignal(pos, this);
            }
        }
    }
    
    @Override
    @SuppressWarnings("deprecation")
    public @NotNull VoxelShape getShape(@NotNull BlockState p_60555_, @NotNull BlockGetter p_60556_, @NotNull BlockPos p_60557_, @NotNull CollisionContext p_60558_) {
        return Block.box(0,0,0,16,6,16);
    }
    
    @Override
    @SuppressWarnings("deprecation")
    public @NotNull InteractionResult use(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        if(!level.isClientSide){
            var EBlockEntity = level.getBlockEntity(pos);
            if(EBlockEntity instanceof ForgingTableBlockEntity blockEntity){
                var left = player.getItemInHand(InteractionHand.OFF_HAND);
                var right = player.getMainHandItem();
                //放物品
                if(blockEntity.hasItem()){
                    if(!left.isEmpty()){
                        blockEntity.setItem(0,left.split(1));
                        return InteractionResult.SUCCESS;
                    }
                    if(!right.isEmpty()){
                        blockEntity.setItem(0,right.split(1));
                        return InteractionResult.SUCCESS;
                    }
                }
                //取物品
                else if(right.isEmpty()){
                    var item = blockEntity.getItem(0).copy();
                    blockEntity.setItem(0, ItemStack.EMPTY);
                    player.addItem(item.split(1));
                    return InteractionResult.SUCCESS;
                }
                //搞配方
                else {
                    blockEntity.tryDoRecipe(right, (ServerLevel) level,pos,player);
                    if(right.is(ItemTags.HAMMER)){
                        level.playSound(null,pos,SoundEvents.ANVIL_PLACE, SoundSource.BLOCKS,8F,1F);
                    }

                }
            }
        }
        else {
            var right = player.getMainHandItem();
            var entity = level.getBlockEntity(pos);
            if(right.is(ItemTags.HAMMER) && entity instanceof ForgingTableBlockEntity blockEntity){
                
                var in = blockEntity.getItem(0);
                var particle = !in.isEmpty()?
                        new ItemParticleOption(ParticleTypes.ITEM,in.copy())
                        : new BlockParticleOption(ParticleTypes.BLOCK,blockState);
                LevelUtil.addParticles(level,pos,particle);
            }
        }
        return InteractionResult.SUCCESS;
    }
    
    @Override
    public @NotNull List<String> getInfo(ServerLevel level, BlockPos pos) {
        var e = level.getBlockEntity(pos);
        if(e instanceof ForgingTableBlockEntity entity){
            String item = "内容物： "+entity.getItem(0);
            return List.of(NAME,item);
        }
        return List.of(NAME);
    }
    
    @Override
    public @NotNull String getChineseTranslate() {
        return "锻造桌";
    }
}
