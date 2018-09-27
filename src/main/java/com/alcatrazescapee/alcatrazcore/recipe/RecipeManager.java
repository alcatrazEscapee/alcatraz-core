/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.item.ItemStack;

public class RecipeManager<T extends IRecipeCore> implements IRecipeManager<T>
{
    private final List<T> recipes;

    public RecipeManager()
    {
        recipes = new ArrayList<>();
    }

    public void add(T recipe)
    {
        recipes.add(recipe);
    }

    @Nullable
    public T get(ItemStack stack)
    {
        return recipes.stream().filter(x -> x.isEqual(stack)).findFirst().orElse(null);
    }

    @Nonnull
    @Override
    public List<T> getAll()
    {
        return Collections.unmodifiableList(recipes);
    }

    @Override
    public void remove(ItemStack stack)
    {
        recipes.removeIf(x -> x.isEqual(stack));
    }

    @Override
    public void remove(T recipe)
    {
        recipes.removeIf(x -> x.isEqual(recipe));
    }
}
