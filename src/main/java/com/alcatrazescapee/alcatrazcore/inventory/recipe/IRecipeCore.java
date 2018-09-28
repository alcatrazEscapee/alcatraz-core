/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.inventory.recipe;

import java.util.function.Predicate;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * An Interface for basic non-crafting recipes
 *
 * @author AlcatrazEscapee
 */
@ParametersAreNonnullByDefault
public interface IRecipeCore extends Predicate<Object>
{
    /**
     * This is used to test a input object against the recipe
     *
     * @param input The input. Typically handled by calling IRecipeIngredient.test(input);
     * @return if the input object matches the recipe input
     */
    boolean test(Object input);

    /**
     * This is used to test a input object against the recipe
     *
     * @param inputs The inputs, in the case that there are multiple inputs
     * @return if the input object matches the recipe inputs
     */
    boolean test(Object... inputs);

    /**
     * This is used to test if a recipe matches an input pattern. Used for checking if two predicates are the same when removing
     *
     * @param input The input. Typically handled by calling IRecipeIngredient.matches(input);
     * @return if the input object matches the recipe input
     */
    boolean matches(Object input);

    /**
     * This is used to test a input object against the recipe
     *
     * @param inputs The input, in the case that there are multiple inputs
     * @return if the input object matches the recipe inputs
     */
    boolean matches(Object... inputs);

    Object getOutput();

    Object getInput();

    String getName();
}
