/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.recipe;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@ParametersAreNonnullByDefault
public interface IRecipeCore
{
    boolean isEqual(IRecipeCore recipe);

    boolean isEqual(ItemStack stack);

    ItemStack consumeInput(ItemStack stack);

    Object getOutput();

    Object getInput();

    String getName();

    ResourceLocation getID();
}
