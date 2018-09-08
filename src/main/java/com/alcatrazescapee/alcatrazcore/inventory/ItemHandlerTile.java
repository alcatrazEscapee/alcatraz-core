/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.inventory;

import javax.annotation.Nonnull;

import net.minecraftforge.items.ItemStackHandler;

import com.alcatrazescapee.alcatrazcore.tile.TileInventory;

public class ItemHandlerTile extends ItemStackHandler
{
    protected final TileInventory tile;

    public ItemHandlerTile(@Nonnull TileInventory tile, int size)
    {
        super(size);

        this.tile = tile;
    }

    @Override
    public int getSlotLimit(int slot)
    {
        return tile.getSlotLimit(slot);
    }

    @Override
    protected void onContentsChanged(int slot)
    {
        tile.setAndUpdateSlots(slot);
    }

}
