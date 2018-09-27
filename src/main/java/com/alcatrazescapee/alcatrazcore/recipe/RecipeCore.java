/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.recipe;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.alcatrazescapee.alcatrazcore.util.CoreHelpers;

@ParametersAreNonnullByDefault
public abstract class RecipeCore implements IRecipeCore
{
    private final ItemStack outputStack;
    private final int inputAmount;

    private ItemStack inputStack = ItemStack.EMPTY;
    private int inputOreID = -1;
    private String inputOre = "";

    public RecipeCore(ItemStack outputStack, ItemStack inputStack)
    {
        this.inputStack = inputStack;
        this.outputStack = outputStack;
        this.inputAmount = inputStack.isEmpty() ? 0 : inputStack.getCount();
    }

    public RecipeCore(ItemStack outputStack, String inputOre, int inputAmount)
    {
        this.outputStack = outputStack;
        this.inputOre = inputOre;
        this.inputOreID = OreDictionary.getOreID(inputOre);
        this.inputAmount = inputAmount;
    }

    @Override
    public boolean isEqual(IRecipeCore recipe)
    {
        return isEqual((ItemStack) recipe.getOutput());
    }

    @Override
    public boolean isEqual(ItemStack stack)
    {
        return inputOreID == -1 ? CoreHelpers.doStacksMatch(stack, inputStack) : CoreHelpers.doesStackMatchOre(stack, inputOreID);
    }

    @Override
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
    public Object getInput()
    {
        return inputOreID == -1 ? inputStack : inputOre;
    }

    @Override
    public String getName()
    {
        return inputOreID == -1 ? inputStack.getDisplayName() : inputOre;
    }
}
