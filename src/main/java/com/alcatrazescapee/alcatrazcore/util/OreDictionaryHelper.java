/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.util;

import java.util.Map;
import javax.annotation.Nonnull;

import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import com.google.common.base.Joiner;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import static com.alcatrazescapee.alcatrazcore.AlcatrazCore.MOD_ID;

@SuppressWarnings({"unused", "WeakerAccess"})
public class OreDictionaryHelper
{
    private static final Multimap<Element, String> MAP = LinkedListMultimap.create();
    private static final Converter<String, String> UPPER_UNDERSCORE_TO_LOWER_CAMEL = CaseFormat.UPPER_UNDERSCORE.converterTo(CaseFormat.LOWER_CAMEL);
    private static final Joiner JOINER_UNDERSCORE = Joiner.on('_').skipNulls();
    private static boolean pastInit = false;

    public static void init()
    {
        if (!pastInit)
        {
            for (Map.Entry<Element, String> entry : MAP.entries())
                OreDictionary.registerOre(entry.getValue(), entry.getKey().toStack());
            MAP.clear();
            pastInit = true;
        }
    }

    @Nonnull
    public static ItemStack getStackForName(String name)
    {
        return getStackForName(name, MOD_ID);
    }

    @Nonnull
    public static ItemStack getStackForName(String name, String preferredModID)
    {
        if (OreDictionary.doesOreNameExist(name))
        {
            NonNullList<ItemStack> stacks = OreDictionary.getOres(name);
            if (!stacks.isEmpty())
            {
                if (stacks.size() == 1)
                    return stacks.get(0);

                //noinspection ConstantConditions
                return stacks.stream()
                        .filter(x -> x.getItem().getRegistryName().getNamespace().equals(preferredModID))
                        .findFirst()
                        .orElse(stacks.get(0));
            }
        }
        return ItemStack.EMPTY;
    }

    public static void register(Block element, Object... parts)
    {
        if (pastInit)
            OreDictionary.registerOre(toString(parts), new ItemStack(element, 1, OreDictionary.WILDCARD_VALUE));
        else
            MAP.put(new Element(element), toString(parts));
    }

    public static void register(Item element, Object... parts)
    {
        if (pastInit)
            OreDictionary.registerOre(toString(parts), new ItemStack(element, 1, OreDictionary.WILDCARD_VALUE));
        else
            MAP.put(new Element(element), toString(parts));
    }

    public static void register(ItemStack element, Object... parts)
    {
        if (pastInit)
            OreDictionary.registerOre(toString(parts), element);
        else
            MAP.put(new Element(element.getItem(), element.getMetadata()), toString(parts));
    }

    private static String toString(Object... parts)
    {
        return UPPER_UNDERSCORE_TO_LOWER_CAMEL.convert(JOINER_UNDERSCORE.join(parts));
    }

    private OreDictionaryHelper() {}

    private static class Element
    {
        private Block block;
        private Item item;
        private int meta;

        private Element(Item item, int meta)
        {
            this.item = item;
            this.meta = meta;
        }

        private Element(Item item)
        {
            this(item, -1);
        }

        private Element(Block block)
        {
            this.block = block;
        }

        private ItemStack toStack()
        {
            if (block != null)
                return new ItemStack(block);
            else if (item != null)
                return meta == -1 ? new ItemStack(item, 1, OreDictionary.WILDCARD_VALUE) : new ItemStack(item, meta);
            else
                return ItemStack.EMPTY;
        }
    }
}
