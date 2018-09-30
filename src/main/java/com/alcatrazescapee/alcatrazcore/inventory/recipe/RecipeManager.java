/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.inventory.recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Default implementation of {@link IRecipeManager}
 *
 * @author AlcatrazEscapee
 */
public class RecipeManager<T extends IRecipeCore> implements IRecipeManager<T>
{
    private final List<T> recipes;

    public RecipeManager()
    {
        recipes = new ArrayList<>();
    }

    public RecipeManager(int size)
    {
        recipes = new ArrayList<>(size);
    }

    @Override
    public void add(T recipe)
    {
        recipes.add(recipe);
    }

    @Nullable
    @Override
    public T get(Object... inputs)
    {
        return recipes.stream().filter(x -> x.test(inputs)).findFirst().orElse(null);
    }

    @Nullable
    @Override
    public T get(Object input)
    {
        return recipes.stream().filter(x -> x.test(input)).findFirst().orElse(null);
    }

    @Nonnull
    @Override
    public List<T> getAll()
    {
        return Collections.unmodifiableList(recipes);
    }

    @Override
    public void remove(Object... inputs)
    {
        recipes.removeIf(x -> x.matches(inputs));
    }

    @Override
    public void remove(Object input)
    {
        recipes.removeIf(x -> x.matches(input));
    }
}
