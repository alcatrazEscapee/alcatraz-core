/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.inventory;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import com.alcatrazescapee.alcatrazcore.tile.TileInventory;

public class SlotTileCore extends SlotItemHandler
{

    protected final TileInventory te;

    public SlotTileCore(@Nonnull IItemHandler inventory, int idx, int x, int y, @Nonnull TileInventory te)
    {
        super(inventory, idx, x, y);
        this.te = te;
    }

    @Override
    public void onSlotChanged()
    {
        te.setAndUpdateSlots(getSlotIndex());
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack)
    {
        return te.isItemValid(getSlotIndex(), stack);
    }

    @Override
    public int getSlotStackLimit()
    {
        return te.getSlotLimit(getSlotIndex());
    }
}
