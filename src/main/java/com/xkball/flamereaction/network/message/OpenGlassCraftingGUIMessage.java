package com.xkball.flamereaction.network.message;

import com.xkball.flamereaction.gui.GlassCraftingScreen;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.UUID;
import java.util.function.Supplier;

public class OpenGlassCraftingGUIMessage {
    
    //意义不明的设计，但是懒得删了，应该没有性能影响
    private final UUID player;
    private final IntList intList;
    
    public OpenGlassCraftingGUIMessage(UUID uuid,IntList intList){
        this.player = uuid;
        this.intList = intList;
    }
    
    public static void encode(OpenGlassCraftingGUIMessage message, FriendlyByteBuf byteBuf){
        byteBuf.writeUUID(message.player);
        for(int i = 0;i<25;i++){
            byteBuf.writeInt(message.intList.getInt(i));
        }
    }
    
    public static OpenGlassCraftingGUIMessage decode(FriendlyByteBuf byteBuf){
        var uuid = byteBuf.readUUID();
        var listBuf = new ArrayList<Integer>();
        for(int i = 0;i<25;i++){
            listBuf.add(byteBuf.readInt());
        }
        return new OpenGlassCraftingGUIMessage(uuid,new IntArrayList(listBuf));
    }
    
    public static void handle(OpenGlassCraftingGUIMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        if (context.getDirection().getReceptionSide().isClient()) {
            context.enqueueWork(() -> handleOpenGui(message));
        }
        context.setPacketHandled(true);
    }
    
    @OnlyIn(Dist.CLIENT)
    public static void handleOpenGui(OpenGlassCraftingGUIMessage message){
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) {
            return;
        }
        if (mc.player != null && mc.player.isAlive()) {
            mc.setScreen(new GlassCraftingScreen(message.intList));
        }
    }
    
}
