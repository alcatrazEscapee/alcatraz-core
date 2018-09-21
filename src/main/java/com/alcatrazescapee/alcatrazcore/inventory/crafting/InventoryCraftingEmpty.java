/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.inventory.crafting;

import net.minecraft.inventory.InventoryCrafting;

import com.alcatrazescapee.alcatrazcore.inventory.container.ContainerEmpty;

public class InventoryCraftingEmpty extends InventoryCrafting
{
    private static final ContainerEmpty nullContainer = new ContainerEmpty();

    public InventoryCraftingEmpty(int width, int height)
    {
        super(nullContainer, width, height);
    }
}
