/*
 * Part of the Alcatraz Core mod by AlcatrazEscapee.
 * Copyright (c) 2020. See the project LICENSE.md for details.
 */

package com.alcatrazescapee.core.common.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

/**
 * This is a callback for various methods on an ItemStackHandler.
 * Methods are default to support overriding as many or as little as necessary
 *
 * {@link ItemStackHandlerCallback}
 *
 * @since 2.0.0
 */
public interface ISlotCallback
{
    /**
     * Gets the slot stack size
     */
    default int getSlotStackLimit(int slot)
    {
        return 64;
    }

    /**
     * Checks if an item is valid for a slot
     *
     * @return true if the item can be inserted
     */
    default boolean isItemValid(int slot, ItemStack stack)
    {
        return true;
    }

    /**
     * Called when a slot changed
     *
     * @param slot the slot index, or -1 if the call method had no specific slot
     */
    default void setAndUpdateSlots(int slot) {}

    /**
     * Called when a slot is taken from
     */
    default void onSlotTake(PlayerEntity player, int slot, ItemStack stack) {}
}
