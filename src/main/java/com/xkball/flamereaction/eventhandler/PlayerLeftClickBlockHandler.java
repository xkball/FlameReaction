package com.xkball.flamereaction.eventhandler;


import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.itemlike.block.commonblocks.burningblock.AlcoholLamp;
import com.xkball.flamereaction.util.MathUtil;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerLeftClickBlockHandler {
    
    @SubscribeEvent
    public static void OnPlayLeftClickBlock( final PlayerInteractEvent.LeftClickBlock event){
        var player = event.getPlayer();
        var level = player.getLevel();
        if(!level.isClientSide){
            var pos = event.getPos();
            var block = level.getBlockState(pos);
            //给酒精灯灭火
            if(block.is(FlameReaction.ALCOHOL_LAMP) && block.getValue(AlcoholLamp.FIRED) && MathUtil.randomBoolean(3)){
                block = block.setValue(AlcoholLamp.FIRED,Boolean.FALSE);
                level.setBlock(pos,block, Block.UPDATE_ALL);
            }
        }
    }
}
