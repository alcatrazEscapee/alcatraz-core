/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.inventory.ingredient;

import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

/**
 * Ingredient Interface for use in non-crafting style recipes
 *
 * @author AlcatrazEscapee
 */
public interface IRecipeIngredient extends Predicate<Object>
{
    static IRecipeIngredient of(ItemStack stack)
    {
        return new IngredientItemStack(stack);
    }

    static IRecipeIngredient of(String oreName)
    {
        return new IngredientOreDict(oreName);
    }

    static IRecipeIngredient of(String oreName, int amount)
    {
        return new IngredientOreDict(oreName, amount);
    }

    static IRecipeIngredient of(Fluid fluid, int amount)
    {
        return new IngredientFluidStack(fluid, amount);
    }

    static IRecipeIngredient of(FluidStack stack)
    {
        return new IngredientFluidStack(stack);
    }

    @Nonnull
    String getName();

    @Nonnull
    List<ItemStack> getStacks();

    /**
     * Used to test whether an input object matches this ingredient
     *
     * @param input This is typically an item stack. The individual ingredient type will to a cast to the relevant type here.
     */
    @Override
    boolean test(Object input);

    /**
     * Used to test whether another ingredient matches this one, for purpose of recipe removal
     *
     * @param other Another ingredient. Return true if this is the same as the current one.
     */
    boolean matches(IRecipeIngredient other);
}
