/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.inventory.ingredient;

import java.util.List;
import java.util.stream.Collectors;
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
    private List<ItemStack> stacks;

    IngredientOreDict(@Nonnull String oreName)
    {
        this(oreName, 1);
    }

    IngredientOreDict(@Nonnull String oreName, int amount)
    {
        this.oreName = oreName;
        this.amount = amount;
        this.stacks = null;
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
        if (stacks == null)
        {
            stacks = OreDictionary.getOres(oreName, false).stream().map(ItemStack::copy).peek(x -> x.setCount(amount)).collect(Collectors.toList());
        }
        return stacks;
    }

    @Override
    public boolean test(Object obj)
    {
        return testIgnoreCount(obj) && ((ItemStack) obj).getCount() >= amount;
    }

    @Override
    public boolean testIgnoreCount(Object obj)
    {
        return obj instanceof ItemStack && CoreHelpers.doesStackMatchOre((ItemStack) obj, oreName);
    }

    @Override
    public boolean matches(IRecipeIngredient other)
    {
        return other instanceof IngredientOreDict && ((IngredientOreDict) other).oreName.equals(this.oreName);
    }
}
