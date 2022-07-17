package com.xkball.flamereaction.eventhandler;


import com.xkball.flamereaction.itemlike.block.commonblocks.ExhibitBlock;
import com.xkball.flamereaction.itemlike.block.blockentity.ExhibitBlockEntity;
import com.xkball.flamereaction.itemlike.item.commonitem.tool.ExhibitBlockKey;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerShiftEventHandler {
    
    
    @SubscribeEvent
    public static void onPlayerShift(PlayerInteractEvent.RightClickBlock rightClickBlock){
        var player = rightClickBlock.getPlayer();
        var level = player.getLevel();
        if(player.isShiftKeyDown() && !level.isClientSide){
            var item = player.getMainHandItem();
            if(item.getItem() instanceof ExhibitBlockKey){
                var pos = rightClickBlock.getPos();
                var state = level.getBlockState(pos);
                var block_entity = level.getBlockEntity(pos);
                if(block_entity instanceof ExhibitBlockEntity be && !state.getValue(ExhibitBlock.IS_LOCKED)){
                    be.setLikeItemEntity(!be.isLikeItemEntity());
                }
            }
        }
    }
}
