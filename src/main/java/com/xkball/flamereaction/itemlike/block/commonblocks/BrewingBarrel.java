package com.xkball.flamereaction.itemlike.block.commonblocks;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.crafting.ISingleToSingleRecipe;
import com.xkball.flamereaction.crafting.SingleToFluidRecipe;
import com.xkball.flamereaction.eventhandler.register.BlockEntityRegister;
import com.xkball.flamereaction.eventhandler.register.RecipeRegister;
import com.xkball.flamereaction.creativemodetab.CreativeModeTabs;
import com.xkball.flamereaction.itemlike.block.FRCBlock;
import com.xkball.flamereaction.itemlike.block.blockentity.BrewingBarrelBlockEntity;
import com.xkball.flamereaction.util.ItemList;
import com.xkball.flamereaction.util.LevelUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fluids.FluidUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BrewingBarrel extends BaseEntityBlock implements FRCBlock {
    
    public static final String NAME = "brewing_barrel";
    public BrewingBarrel() {
        super(BlockBehaviour.Properties
                .of(Material.HEAVY_METAL)
                .strength(3f,8f)
                .noOcclusion()
                .sound(SoundType.METAL)
                );
        this.setRegistryName(new ResourceLocation(FlameReaction.MOD_ID,NAME));
        add();
        regItemBlock();
    }
    
    
    public void regItemBlock(){
        var bi = new BlockItem(this,new Item.Properties().fireResistant().tab(CreativeModeTabs.FLAME_REACTION_GROUP));
        bi.setRegistryName(FlameReaction.MOD_ID, NAME);
        ItemList.addItem(bi);
    }
    
    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState p_49232_) {
        return RenderShape.MODEL;
    }
    
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState blockState, @NotNull BlockEntityType<T> blockEntityType) {
        return !level.isClientSide ?
                createTickerHelper(blockEntityType, BlockEntityRegister.BREWING_BARREL_BLOCK_ENTITY.get(),BrewingBarrelBlockEntity::tick) : null;
    }
    
    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState blockState) {
        return new BrewingBarrelBlockEntity(pos,blockState);
    }
    
    @Override
    @SuppressWarnings("deprecation")
    public @NotNull InteractionResult use(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult p_60508_) {
        if(!level.isClientSide){
            var blockEntity = level.getBlockEntity(pos);
            var recipe = level.getRecipeManager().getAllRecipesFor(RecipeRegister.SINGLE_TO_SINGLE_TYPE.get());
            var list = recipe.stream().filter((r)-> r instanceof SingleToFluidRecipe).map(ISingleToSingleRecipe::getInput).toList();
            if(blockEntity instanceof Container container){
                if(!FluidUtil.interactWithFluidHandler(player,hand,level,pos,null)){
                    //装桶
                    LevelUtil.fillBucket((ServerLevel) level,pos,player,player.getMainHandItem());
                    //放物品
                    LevelUtil.containerInput((ServerLevel) level,pos,container,player.getOffhandItem(),player.getMainHandItem(), (i)->list.contains(i.getItem()));
                }
            }
        }
        return InteractionResult.SUCCESS;
    }
    
    
}
