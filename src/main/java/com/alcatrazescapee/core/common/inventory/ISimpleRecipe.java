/*
 * Part of the Alcatraz Core mod by AlcatrazEscapee.
 * Copyright (c) 2020. See the project LICENSE.md for details.
 */

package com.alcatrazescapee.core.common.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

/**
 * Default implementation of possible {@link IRecipe} methods
 *
 * @since 2.0.0
 */
public interface ISimpleRecipe<C extends IInventory> extends IRecipe<C>
{
    @Override
    default ItemStack getCraftingResult(C inv)
    {
        return getRecipeOutput().copy();
    }

    @Override
    default boolean canFit(int width, int height)
    {
        return true;
    }

    @Override
    default boolean isDynamic()
    {
        return true;
    }
}
