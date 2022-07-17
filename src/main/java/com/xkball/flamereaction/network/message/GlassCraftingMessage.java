package com.xkball.flamereaction.network.message;

import com.xkball.flamereaction.FlameReaction;
import com.xkball.flamereaction.crafting.GlassCraftingRecipe;
import com.xkball.flamereaction.crafting.util.IntListContainer;
import com.xkball.flamereaction.eventhandler.register.RecipeRegister;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Block;
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
                    var level = player.getLevel();
                    var item = player.getMainHandItem();
                    var recipe = level.getRecipeManager().getAllRecipesFor(RecipeRegister.GLASS_CRAFTING_TYPE.get());
                    for(GlassCraftingRecipe recipe1:recipe){
                        if(recipe1.matches(new IntListContainer() {
                            @Override
                            public IntList getIntList() {
                                return IntArrayList.wrap(message.recipe);
                            }
    
                            @Override
                            public Block getTarget() {
                                return FlameReaction.FLAME_FIRE_BLOCK;
                            }
                        },level)){
                            LOGGER.info("adknaoiejfcaiowngciahnecl vaskuec hao");
                        }
                    }
                    item.addTagElement("hasGlass", IntTag.valueOf(1));
                    item.addTagElement("list",new IntArrayTag(message.recipe));
                }
            });
        }
        context.setPacketHandled(true);
    }
}
