/*
 * Part of the Alcatraz Core mod by AlcatrazEscapee.
 * Copyright (c) 2020. See the project LICENSE.md for details.
 */

package com.alcatrazescapee.core.common.inventory;

import java.util.Arrays;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

/**
 * A wrapper for item handlers, which can be used to expose certain sides to automation
 *
 * @since 2.0.0
 */
public class SidedItemStackHandler implements IItemHandlerModifiable
{
    public static SidedItemStackHandler insert(IItemHandlerModifiable internal, int... slots)
    {
        return new SidedItemStackHandler(internal)
        {
            @Override
            protected boolean canInsert(int slot, ItemStack stack)
            {
                return Arrays.stream(slots).anyMatch(allowed -> allowed == slot);
            }
        };
    }

    public static SidedItemStackHandler extract(IItemHandlerModifiable internal, int... slots)
    {
        return new SidedItemStackHandler(internal)
        {
            @Override
            protected boolean canExtract(int slot, int amount)
            {
                return Arrays.stream(slots).anyMatch(allowed -> allowed == slot);
            }
        };
    }

    private final IItemHandlerModifiable internal;

    protected SidedItemStackHandler(IItemHandlerModifiable internal)
    {
        this.internal = internal;
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack)
    {
        internal.setStackInSlot(slot, stack);
    }

    @Override
    public int getSlots()
    {
        return internal.getSlots();
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return internal.getStackInSlot(slot);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
    {
        if (canInsert(slot, stack))
        {
            return internal.insertItem(slot, stack, simulate);
        }
        return stack;
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate)
    {
        if (canExtract(slot, amount))
        {
            return internal.extractItem(slot, amount, simulate);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public int getSlotLimit(int slot)
    {
        return internal.getSlotLimit(slot);
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack)
    {
        return internal.isItemValid(slot, stack);
    }

    protected boolean canInsert(int slot, ItemStack stack)
    {
        return false;
    }

    protected boolean canExtract(int slot, int amount)
    {
        return false;
    }
}
