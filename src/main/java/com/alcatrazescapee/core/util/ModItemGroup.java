/*
 * Part of the Primal Alchemy mod by AlcatrazEscapee.
 * Copyright (c) 2019. See the project LICENSE.md for details.
 */

package com.alcatrazescapee.core.util;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.Lazy;

import java.util.function.Supplier;

/**
 * Simple implementation of {@link ItemGroup} which uses a lazily initialized icon stack.
 *
 * @since 2.0.0
 */
public class ModItemGroup extends ItemGroup
{
    private final Lazy<ItemStack> iconStack;

    public ModItemGroup(String label, Supplier<ItemStack> iconStack)
    {
        super(label);
        this.iconStack = Lazy.of(iconStack);
    }

    @Override
    public ItemStack createIcon()
    {
        return iconStack.get();
    }
}
