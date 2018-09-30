/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.inventory.recipe;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.item.ItemStack;

import com.alcatrazescapee.alcatrazcore.inventory.ingredient.IRecipeIngredient;
import com.alcatrazescapee.alcatrazcore.util.CoreHelpers;

/**
 * A recipe template for recipes with a single item stack input, but multiple item stack outputs
 *
 * @author AlcatrazEscapee
 */
@ParametersAreNonnullByDefault
public abstract class RecipeMultiOutput implements IRecipeCore
{
    protected final IRecipeIngredient ingredient;
    protected final ItemStack[] outputStacks;
    protected final int inputAmount;

    public RecipeMultiOutput(ItemStack inputStack, ItemStack... outputStacks)
    {
        this.outputStacks = outputStacks;
        this.inputAmount = inputStack.getCount();
        this.ingredient = IRecipeIngredient.of(inputStack);
    }

    public RecipeMultiOutput(String inputOre, int inputAmount, ItemStack... outputStacks)
    {
        this.inputAmount = inputAmount;
        this.outputStacks = outputStacks;
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

    @Override
    public ItemStack[] getOutput()
    {
        return outputStacks;
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
