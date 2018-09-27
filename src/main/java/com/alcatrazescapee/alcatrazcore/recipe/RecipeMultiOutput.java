/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.recipe;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.alcatrazescapee.alcatrazcore.util.CoreHelpers;

@ParametersAreNonnullByDefault
public abstract class RecipeMultiOutput implements IRecipeCore
{
    private final ItemStack[] outputStacks;
    private final int inputAmount;

    private ItemStack inputStack = ItemStack.EMPTY;
    private String inputOre = "";
    private int inputOreID = -1;

    public RecipeMultiOutput(ItemStack inputStack, ItemStack... outputStacks)
    {
        this.inputStack = inputStack;
        this.outputStacks = outputStacks;

        inputAmount = inputStack.getCount();
    }

    public RecipeMultiOutput(String inputOre, int inputAmount, ItemStack... outputStacks)
    {
        this.inputOre = inputOre;
        this.inputOreID = OreDictionary.getOreID(inputOre);
        this.inputAmount = inputAmount;
        this.outputStacks = outputStacks;
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

    @Override
    public ItemStack[] getOutput()
    {
        return outputStacks;
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
