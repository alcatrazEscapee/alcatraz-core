/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.client;

import java.util.Arrays;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import com.alcatrazescapee.alcatrazcore.AlcatrazCore;
import com.alcatrazescapee.alcatrazcore.CoreConfig;

import static net.minecraft.util.text.TextFormatting.AQUA;
import static net.minecraft.util.text.TextFormatting.GRAY;

@Mod.EventBusSubscriber(modid = AlcatrazCore.MOD_ID)
public class ClientEventHandler
{
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onItemTooltip(ItemTooltipEvent event)
    {
        ItemStack stack = event.getItemStack();
        List<String> tt = event.getToolTip();

        if (event.getFlags().isAdvanced() && CoreConfig.GENERAL.showDebugTooltips)
        {
            // Translation Key
            tt.add(AQUA + "Translation Key: " + GRAY + stack.getTranslationKey());

            // NBT Information
            if (stack.hasTagCompound())
            {
                //noinspection ConstantConditions
                tt.add(AQUA + "NBT: " + GRAY + stack.getTagCompound().toString());
            }

            // Ore Dictionary Names
            int[] ids = OreDictionary.getOreIDs(stack);
            if (ids != null && ids.length != 0)
            {
                tt.add(AQUA + "Ore Dictionary Names:");
                Arrays.stream(ids).mapToObj(OreDictionary::getOreName).sorted().map(x -> GRAY + x).forEachOrdered(tt::add);
            }
        }
    }
}
