/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.util.compat;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;

import com.alcatrazescapee.alcatrazcore.util.CoreHelpers;

public final class FireRegistry
{
    private static final List<ItemStack> FIRE_STARTERS = new ArrayList<>();

    public static boolean isFireStarter(ItemStack stack)
    {
        for (ItemStack fireStarter : FIRE_STARTERS)
            if (CoreHelpers.doStacksMatch(stack, fireStarter))
                return true;
        return false;
    }

    public static void init()
    {
        FIRE_STARTERS.clear();
        FIRE_STARTERS.add(new ItemStack(Items.FLINT_AND_STEEL, 1, OreDictionary.WILDCARD_VALUE));

        if (Loader.isModLoaded("notreepunching"))
        {
            FIRE_STARTERS.add(CoreHelpers.getStackByRegistryName("notreepunching:fire_starter", OreDictionary.WILDCARD_VALUE));
        }
        if (Loader.isModLoaded("charcoal_pit"))
        {
            FIRE_STARTERS.add(CoreHelpers.getStackByRegistryName("charcoal_pit:fire_starter", OreDictionary.WILDCARD_VALUE));
        }
        if (Loader.isModLoaded("primal"))
        {
            FIRE_STARTERS.add(CoreHelpers.getStackByRegistryName("primal:fire_bow", OreDictionary.WILDCARD_VALUE));
        }

        FIRE_STARTERS.removeIf(ItemStack::isEmpty);
    }

    public static void addFireStarter(ItemStack stack)
    {
        FIRE_STARTERS.add(stack);
    }
}
