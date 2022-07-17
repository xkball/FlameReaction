package com.xkball.flamereaction.itemlike.block.commonblocks;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.creativemodetab.CreativeModeTabs;
import com.xkball.flamereaction.itemlike.block.FRCBlock;
import com.xkball.flamereaction.itemlike.block.blockentity.AlcoholLampBlockEntity;
import com.xkball.flamereaction.util.ItemList;
import com.xkball.flamereaction.util.LevelUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AlcoholLamp extends BaseEntityBlock implements FRCBlock {
    
    public static final String NAME = "alcohol_lamp";
    public static final BooleanProperty FIRED = BooleanProperty.create("fired");
    public static final BooleanProperty O_COLORED = BooleanProperty.create("o_colored");
    public static final BooleanProperty HAS_IRON_STAND = BooleanProperty.create("has_iron_stand");
    
    
    public AlcoholLamp() {
        super(BlockBehaviour.Properties
                .of(Material.GLASS)
                .strength(2f,8f)
                .noOcclusion()
                .sound(SoundType.GLASS)
                .lightLevel((blockState) -> blockState.getValue(FIRED)?15:0));
        this.setRegistryName(new ResourceLocation(FlameReaction.MOD_ID,NAME));
        this.registerDefaultState(this.getStateDefinition().any().setValue(FIRED,Boolean.FALSE).setValue(O_COLORED,Boolean.FALSE).setValue(HAS_IRON_STAND,Boolean.FALSE));
        add();
        regItemBlock();
    }
    
    @Override
    @SuppressWarnings("deprecation")
    public void onRemove(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState blockState1, boolean p_60519_) {
        if(blockState.getValue(HAS_IRON_STAND) && !level.isClientSide){
            LevelUtil.addItem((ServerLevel) level,pos,new ItemStack(ItemList.item_instance.get("iron_stick"),1));
        }
        super.onRemove(blockState, level, pos, blockState1, p_60519_);
    }
    
    public void regItemBlock(){
        var bi = new BlockItem(this,new Item.Properties().fireResistant().tab(CreativeModeTabs.FLAME_REACTION_GROUP));
        bi.setRegistryName(FlameReaction.MOD_ID, NAME);
        ItemList.addItem(bi);
    }
    
    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        builder.add(FIRED,
                O_COLORED,
                HAS_IRON_STAND);
    }
    
    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState p_49232_) {
        return RenderShape.MODEL;
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
        
        
        return super.use(blockState, level, pos, player, hand, result);
    }
}
