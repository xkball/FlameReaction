package com.xkball.flamereaction.network.message;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.util.LevelUtil;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.function.Supplier;

@SuppressWarnings("ClassCanBeRecord")
public class GlassCraftingMessage {
    private static final Logger LOGGER = LogManager.getLogger();
    
    private final int[] recipe;
    
    public GlassCraftingMessage(int[] recipe) {
        this.recipe = recipe;
    }
    
    public static void encode(GlassCraftingMessage message, FriendlyByteBuf byteBuf){
        byteBuf.writeVarIntArray(message.recipe);
    }
    
    public static GlassCraftingMessage decode(FriendlyByteBuf byteBuf){
        return new GlassCraftingMessage(byteBuf.readVarIntArray());
    }
    
    public static void handle(GlassCraftingMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        if (context.getDirection().getReceptionSide().isServer()) {
            context.enqueueWork(() -> {
                var player = context.getSender();
                
                LOGGER.info("get"+ Arrays.toString(message.recipe));
                
                if(player != null && player.isAlive() && player.getMainHandItem().is(FlameReaction.PLIERS)){
                    var item = player.getMainHandItem();
                    
                    //一为有物品
                    LevelUtil.addBooleanTagToItem(item,"has_item",true);
                    item.addTagElement("list",new IntArrayTag(message.recipe));
                }
            });
        }
        context.setPacketHandled(true);
    }
}
