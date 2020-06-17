/*
 * Part of the Primal Alchemy mod by AlcatrazEscapee.
 * Copyright (c) 2019. See the project LICENSE.md for details.
 */

package com.alcatrazescapee.core.common.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/**
 * A wrapper around a single {@link ItemStack}, for use in recipe querying without inserting into an inventory.
 * This inventory is immutable.
 *
 * @since 2.0.0
 */
public class ItemStackWrapper implements IInventory
{
    private final ItemStack stack;

    public ItemStackWrapper(ItemStack stack)
    {
        this.stack = stack;
    }

    public ItemStack getStack()
    {
        return stack;
    }

    @Override
    public int getSizeInventory()
    {
        return 1;
    }

    @Override
    public boolean isEmpty()
    {
        return stack.isEmpty();
    }

    @Override
    public ItemStack getStackInSlot(int index)
    {
        return stack.copy();
    }

    @Override
    public ItemStack decrStackSize(int index, int count)
    {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeStackFromSlot(int index)
    {
        return ItemStack.EMPTY;
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {}

    @Override
    public void markDirty() {}

    @Override
    public boolean isUsableByPlayer(PlayerEntity player)
    {
        return true;
    }

    @Override
    public void clear() {}
}
