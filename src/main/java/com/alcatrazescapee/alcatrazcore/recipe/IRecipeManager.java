/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.recipe;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;

public interface IRecipeManager<T extends IRecipeCore>
{
    void add(T recipe);

    @Nullable
    T get(ItemStack stack);

    @Nonnull
    List<T> getAll();

    void remove(ItemStack stack);

    void remove(T recipe);

    default boolean isRecipe(ItemStack stack)
    {
        return get(stack) != null;
    }

}
