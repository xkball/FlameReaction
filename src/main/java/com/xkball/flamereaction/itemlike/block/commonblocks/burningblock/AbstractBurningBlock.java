package com.xkball.flamereaction.itemlike.block.commonblocks.burningblock;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.itemlike.block.FRCBlock;
import com.xkball.flamereaction.itemlike.block.FRCInfo;
import com.xkball.flamereaction.util.translateutil.TranslateUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractBurningBlock extends BaseEntityBlock implements FRCBlock, FRCInfo {
    
    public static final BooleanProperty FIRED = BooleanProperty.create("fired");
    
    public static final TranslatableComponent tooltip1 = TranslateUtil.create("tooltip.burning_box1",
            "使用打火石或火柴点燃",
            "use flint_and_steel to fire");
    public static final TranslatableComponent tooltip2 = TranslateUtil.create("tooltip.burning_box2",
            "在面前放置固体方块熄灭",
            "place a stable block before it to melt down"
    );
    
    protected AbstractBurningBlock(String name,Properties p) {
        super(p);
        this.setRegistryName(new ResourceLocation(FlameReaction.MOD_ID,name));
    }
    
    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        builder.add(FIRED);
    }
    
    public static void enable(ServerLevel level, BlockPos pos,BlockState state){
        state = state.setValue(FIRED,Boolean.TRUE);
        level.setBlock(pos,state,Block.UPDATE_ALL);
    }
    
    public static void disable(ServerLevel level, BlockPos pos,BlockState state){
        state = state.setValue(FIRED,Boolean.FALSE);
        level.setBlock(pos,state,Block.UPDATE_ALL);
    }
    
    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState p_49232_) {
        return RenderShape.MODEL;
    }
}
