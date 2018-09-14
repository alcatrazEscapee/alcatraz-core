/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.inventory.slot;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotOutput extends SlotItemHandler
{

    public SlotOutput(@Nonnull IItemHandler inventory, int idx, int x, int y)
    {
        super(inventory, idx, x, y);
    }

    @Override
    public boolean isItemValid(@Nonnull ItemStack stack)
    {
        return false;
    }
}
