package com.xkball.flamereaction.network;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.network.message.GlassCraftingMessage;
import com.xkball.flamereaction.network.message.OpenGlassCraftingGUIMessage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

//copy自车万女仆
public class NetworkHandler {
    
    private static final String VERSION = "1.0.0";
    
    public static final SimpleChannel CHANNEL =
            NetworkRegistry.newSimpleChannel(new ResourceLocation(FlameReaction.MOD_ID, "network"),
            () -> VERSION, i -> i.equals(VERSION), i -> i.equals(VERSION));
    
    public static void init(){
        CHANNEL.registerMessage(0, OpenGlassCraftingGUIMessage.class,
                OpenGlassCraftingGUIMessage::encode,
                OpenGlassCraftingGUIMessage::decode,
                OpenGlassCraftingGUIMessage::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT));
        CHANNEL.registerMessage(1, GlassCraftingMessage.class,
                GlassCraftingMessage::encode,
                GlassCraftingMessage::decode,
                GlassCraftingMessage::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER));
    }
    
    public static void sendToClientPlayer(Object message, Player player) {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), message);
    }
}
