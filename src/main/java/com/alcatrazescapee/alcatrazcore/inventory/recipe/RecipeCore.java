/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.inventory.recipe;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.item.ItemStack;

import com.alcatrazescapee.alcatrazcore.inventory.ingredient.IRecipeIngredient;
import com.alcatrazescapee.alcatrazcore.util.CoreHelpers;

/**
 * A basic implementation of {@link IRecipeCore} for single item stack -> item stack recipes
 *
 * @author AlcatrazEscapee
 */
@ParametersAreNonnullByDefault
public abstract class RecipeCore implements IRecipeCore
{
    protected final IRecipeIngredient ingredient;
    protected final ItemStack outputStack;
    protected final int inputAmount;

    public RecipeCore(ItemStack outputStack, ItemStack inputStack)
    {
        this.outputStack = outputStack;
        this.inputAmount = inputStack.isEmpty() ? 0 : inputStack.getCount();
        this.ingredient = IRecipeIngredient.of(inputStack);
    }

    public RecipeCore(ItemStack outputStack, String inputOre, int inputAmount)
    {
        this.outputStack = outputStack;
        this.inputAmount = inputAmount;
        this.ingredient = IRecipeIngredient.of(inputOre, inputAmount);
    }

    @Override
    public boolean test(Object input)
    {
        return ingredient.test(input);
    }

    @Override
    public boolean test(Object... inputs)
    {
        throw new UnsupportedOperationException("This recipe does not support access by multiple inputs");
    }

    @Override
    public boolean matches(Object input)
    {
        return input instanceof IRecipeIngredient && ingredient.matches((IRecipeIngredient) input);
    }

    @Override
    public boolean matches(Object... inputs)
    {
        throw new UnsupportedOperationException("This recipe does not support access by multiple inputs");
    }

    public ItemStack consumeInput(ItemStack stack)
    {
        return CoreHelpers.consumeItem(stack, inputAmount);
    }

    @Nonnull
    public ItemStack getOutput()
    {
        return outputStack.copy();
    }

    @Override
    public IRecipeIngredient getInput()
    {
        return ingredient;
    }

    @Override
    public String getName()
    {
        return ingredient.getName();
    }
}
