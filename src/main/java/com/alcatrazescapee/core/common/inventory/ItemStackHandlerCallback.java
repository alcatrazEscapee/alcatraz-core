/*
 * Part of the Primal Alchemy mod by AlcatrazEscapee.
 * Copyright (c) 2019. See the project LICENSE.md for details.
 */

package com.alcatrazescapee.core.common.inventory;

import com.alcatrazescapee.core.common.container.ItemStackContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

/**
 * This is an item stack handler which delegates some logic back to the provided callback
 * It can also be used as a pass-through callback itself, for example, when using a slot which needs to call back to the original instance:
 * - {@link ItemStackItemHandler} is the callback for the contained {@link ItemStackHandlerCallback}
 * - In a {@link ItemStackContainer}, the capability is used as the callback to {@link SlotCallback}s, which then delegates all the way back to the capability provider.
 *
 * @since 2.0.0
 */
public class ItemStackHandlerCallback extends ItemStackHandler implements ISlotCallback
{
    private final ISlotCallback callback;

    public ItemStackHandlerCallback(ISlotCallback callback, int slots)
    {
        super(slots);
        this.callback = callback;
    }

    @Override
    public int getSlotLimit(int slot)
    {
        return callback.getSlotStackLimit(slot);
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack)
    {
        return callback.isItemValid(slot, stack);
    }

    @Override
    protected void onContentsChanged(int slot)
    {
        callback.setAndUpdateSlots(slot);
    }

    @Override
    public int getSlotStackLimit(int slot)
    {
        return callback.getSlotStackLimit(slot);
    }

    @Override
    public void setAndUpdateSlots(int slot)
    {
        callback.setAndUpdateSlots(slot);
    }

    @Override
    public void onSlotTake(PlayerEntity player, int slot, ItemStack stack)
    {
        callback.onSlotTake(player, slot, stack);
    }
}
