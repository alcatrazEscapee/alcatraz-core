/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.inventory.recipe;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * An Interface that wraps a list of recipes.
 *
 * @param <T> The recipe type. This should be unique
 * @author AlcatrazEscapee
 */
public interface IRecipeManager<T extends IRecipeCore>
{
    /**
     * @param recipe A recipe to add to the manager
     */
    void add(T recipe);

    /**
     * @return the list of all recipes.
     */
    @Nonnull
    List<T> getAll();

    /**
     * Use get with a list type instead
     */
    @Nullable
    @Deprecated
    T get(Object... inputs);

    /**
     * Gets a recipe for the specified input.
     *
     * @param input The input. Can be an IRecipeIngredient, ItemStack, etc.
     * @return The first recipe that matches the input, null if no recipe matches
     */
    @Nullable
    T get(Object input);

    @Deprecated
    void remove(Object... inputs);

    void remove(Object input);

    @Deprecated
    default boolean isRecipe(Object... inputs)
    {
        return false;
    }

    default boolean isRecipe(Object input)
    {
        return get(input) != null;
    }

}
