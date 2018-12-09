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

import com.alcatrazescapee.alcatrazcore.AlcatrazCore;
import com.alcatrazescapee.alcatrazcore.util.CoreHelpers;

public final class FireRegistry
{
    private static List<ItemStack> fireStarters;

    public static boolean isFireStarter(ItemStack stack)
    {
        for (ItemStack fireStarter : fireStarters)
            if (CoreHelpers.doStacksMatch(stack, fireStarter))
                return true;
        return false;
    }

    public static void init()
    {
        fireStarters = new ArrayList<>();
        fireStarters.add(new ItemStack(Items.FLINT_AND_STEEL, 1, OreDictionary.WILDCARD_VALUE));

        if (Loader.isModLoaded("notreepunching"))
        {
            AlcatrazCore.getLog().info("Adding fire starters from No Tree Punching");
            fireStarters.add(CoreHelpers.getStackByRegistryName("notreepunching:fire_starter", OreDictionary.WILDCARD_VALUE));
        }
        // todo: more fire starter items!

        fireStarters.removeIf(ItemStack::isEmpty);
    }

    public static void addFireStarter(ItemStack stack)
    {
        fireStarters.add(stack);
    }
}
