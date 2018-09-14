/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.inventory.capability;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;

import com.alcatrazescapee.alcatrazcore.tile.TileInventory;

public class ItemHandlerWrapper extends ItemStackHandler
{
    private final ItemStackHandler internalInventory;
    private final TileInventory tile;

    private boolean[] extractSlots;
    private boolean[] insertSlots;

    public ItemHandlerWrapper(ItemStackHandler inventory, TileInventory tile)
    {

        this.internalInventory = inventory;
        this.tile = tile;

        extractSlots = new boolean[inventory.getSlots()];
        insertSlots = new boolean[inventory.getSlots()];
    }

    public ItemHandlerWrapper addInsertSlot(int... slots)
    {
        for (int s : slots)
        {
            if (s >= 0 && s < insertSlots.length)
                insertSlots[s] = true;
        }
        return this;
    }

    public ItemHandlerWrapper addExtractSlot(int... slots)
    {
        for (int s : slots)
        {
            if (s >= 0 && s < extractSlots.length)
                extractSlots[s] = true;
        }
        return this;
    }

    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack)
    {
        internalInventory.setStackInSlot(slot, stack);
    }

    @Override
    public int getSlots()
    {
        return internalInventory.getSlots();
    }

    @Override
    @Nonnull
    public ItemStack getStackInSlot(int slot)
    {
        return internalInventory.getStackInSlot(slot);
    }

    // Return stack to prevent insertion
    @Override
    @Nonnull
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate)
    {
        if (insertSlots[slot] && tile.isItemValid(slot, stack))
        {
            return internalInventory.insertItem(slot, stack, simulate);
        }
        return stack;
    }

    // Return ItemStack.EMPTY to prevent extraction
    @Override
    @Nonnull
    public ItemStack extractItem(int slot, int amount, boolean simulate)
    {
        if (extractSlots[slot])
        {
            return internalInventory.extractItem(slot, amount, simulate);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public int getSlotLimit(int slot)
    {
        return internalInventory.getSlotLimit(slot);
    }

    @Override
    public NBTTagCompound serializeNBT()
    {
        return internalInventory.serializeNBT();
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt)
    {
        internalInventory.deserializeNBT(nbt);
    }
}
