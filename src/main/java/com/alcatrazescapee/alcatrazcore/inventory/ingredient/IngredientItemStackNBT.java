/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.inventory.ingredient;

import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;

import com.alcatrazescapee.alcatrazcore.util.CoreHelpers;

/**
 * An ingredient wrapper for an {@link ItemStack} that respects NBT
 *
 * @author AlcatrazEscapee
 * @since 1.0.2
 */
public class IngredientItemStackNBT extends IngredientItemStack
{
    IngredientItemStackNBT(@Nonnull ItemStack stack)
    {
        super(stack);
    }

    @Override
    public boolean testIgnoreCount(Object obj)
    {
        return obj instanceof ItemStack && CoreHelpers.doStacksMatchUseNBT(stack, (ItemStack) obj);
    }

    @Override
    public boolean matches(IRecipeIngredient other)
    {
        return other instanceof IngredientItemStack && CoreHelpers.doStacksMatchUseNBT(stack, ((IngredientItemStack) other).stack);
    }
}
