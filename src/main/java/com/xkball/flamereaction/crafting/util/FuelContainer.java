package com.xkball.flamereaction.crafting.util;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public interface FuelContainer extends Container {
    
    ItemStack getItem();
    FluidStack getFluid();
    Logger LOGGER = LogManager.getLogger();
    String WARN = "IntListContainer is not design to be a container(it just help build recipes).If this warn showed,it may means something wrong";
    
    
    @Override
    default int getContainerSize() {
        LOGGER.warn(WARN);
        return 0;
    }
    
    @Override
    default boolean isEmpty() {
        LOGGER.warn(WARN);
        return false;
    }
    
    @Override
    default @NotNull ItemStack getItem(int p_18941_) {
        LOGGER.warn(WARN);
        return ItemStack.EMPTY;
    }
    
    @Override
    default @NotNull ItemStack removeItem(int p_18942_, int p_18943_) {
        LOGGER.warn(WARN);
        return ItemStack.EMPTY;
    }
    
    @Override
    default @NotNull ItemStack removeItemNoUpdate(int p_18951_) {
        LOGGER.warn(WARN);
        return ItemStack.EMPTY;
    }
    
    @Override
    default void setItem(int p_18944_, @NotNull ItemStack p_18945_) {
        LOGGER.warn(WARN);
    }
    
    @Override
    default void setChanged() {
        LOGGER.warn(WARN);
    }
    
    @Override
    default boolean stillValid(@NotNull Player p_18946_) {
        LOGGER.warn(WARN);
        return false;
    }
    
    @Override
    default void clearContent() {
        LOGGER.warn(WARN);
    }
}
