/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.inventory.ingredient;

import java.util.List;
import javax.annotation.Nonnull;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import com.alcatrazescapee.alcatrazcore.util.CoreHelpers;

import static net.minecraftforge.oredict.OreDictionary.EMPTY_LIST;

/**
 * An ingredient wrapper for a {@link FluidStack}
 *
 * @author AlcatrazEscapee
 */
public class IngredientFluidStack implements IRecipeIngredient
{
    private final FluidStack stack;

    IngredientFluidStack(@Nonnull Fluid fluid, int amount)
    {
        this(new FluidStack(fluid, amount));
    }

    IngredientFluidStack(@Nonnull FluidStack stack)
    {
        this.stack = stack;
    }

    @Override
    @Nonnull
    public String getName()
    {
        return stack.getUnlocalizedName();
    }

    @Nonnull
    @Override
    public List<ItemStack> getStacks()
    {
        return EMPTY_LIST;
    }

    @Override
    public boolean test(Object obj)
    {
        return obj instanceof FluidStack && CoreHelpers.doStacksMatch(stack, (FluidStack) obj) && ((FluidStack) obj).amount >= stack.amount;
    }

    @Override
    public boolean matches(IRecipeIngredient other)
    {
        return other instanceof IngredientFluidStack && CoreHelpers.doStacksMatch(stack, ((IngredientFluidStack) other).stack);
    }
}
