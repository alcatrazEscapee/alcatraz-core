/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.inventory.ingredient;

import java.util.List;
import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.alcatrazescapee.alcatrazcore.util.CoreHelpers;

/**
 * An Ingredient wrapper for an ore dictionary entry
 *
 * @author AlcatrazEscapee
 */
public class IngredientOreDict implements IRecipeIngredient
{
    private final String oreName;
    private final int amount;

    IngredientOreDict(@Nonnull String oreName)
    {
        this(oreName, 1);
    }

    IngredientOreDict(@Nonnull String oreName, int amount)
    {
        this.oreName = oreName;
        this.amount = amount;
    }

    @Nonnull
    @Override
    public String getName()
    {
        return oreName;
    }

    @Override
    @Nonnull
    public List<ItemStack> getStacks()
    {
        return OreDictionary.getOres(oreName, false);
    }

    @Override
    public boolean test(Object obj)
    {
        return obj instanceof ItemStack && CoreHelpers.doesStackMatchOre((ItemStack) obj, oreName) && ((ItemStack) obj).getCount() >= amount;
    }

    @Override
    public boolean matches(IRecipeIngredient other)
    {
        return other instanceof IngredientOreDict && ((IngredientOreDict) other).oreName.equals(this.oreName);
    }
}
