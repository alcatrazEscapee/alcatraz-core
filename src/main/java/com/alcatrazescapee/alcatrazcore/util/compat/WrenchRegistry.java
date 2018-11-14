/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.util.compat;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;

import com.alcatrazescapee.alcatrazcore.AlcatrazCore;
import com.alcatrazescapee.alcatrazcore.util.CoreHelpers;

public final class WrenchRegistry
{
    private static List<ItemStack> wrenches;

    public static boolean isWrench(ItemStack stack)
    {
        for (ItemStack wrench : wrenches)
            if (CoreHelpers.doStacksMatch(stack, wrench))
                return true;
        return false;
    }

    public static void init()
    {
        wrenches = new ArrayList<>();

        if (Loader.isModLoaded("primalalchemy"))
        {
            AlcatrazCore.getLog().info("Adding wrenches from Primal Alchemy");
            wrenches.add(CoreHelpers.getStackByRegistryName("primalalchemy:alchemy_wrench", OreDictionary.WILDCARD_VALUE));
        }
        // todo: more wrenches!

        wrenches.removeIf(ItemStack::isEmpty);
    }

    public static void addWrench(ItemStack stack)
    {
        wrenches.add(stack);
    }
}
