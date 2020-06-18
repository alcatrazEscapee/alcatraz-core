/*
 * Part of the Alcatraz Core mod by AlcatrazEscapee.
 * Copyright (c) 2020. See the project LICENSE.md for details.
 */

package com.alcatrazescapee.core.client;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.alcatrazescapee.core.Config;

import static com.alcatrazescapee.core.AlcatrazCore.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeEventHandler
{
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onItemTooltipEvent(ItemTooltipEvent event)
    {
        if (event.getFlags().isAdvanced() && Config.COMMON.debugItemTooltips.get())
        {
            ItemStack stack = event.getItemStack();
            List<ITextComponent> queuedTooltips = new ArrayList<>();
            stack.getItem().getTags().forEach(tag -> queuedTooltips.add(new StringTextComponent(TextFormatting.WHITE + "Tag: " + TextFormatting.GRAY + tag)));
            if (stack.hasTag())
            {
                queuedTooltips.add(new StringTextComponent(TextFormatting.WHITE + "NBT: " + TextFormatting.GRAY + stack.getTag()));
            }
            stack.getToolTypes().forEach(tool -> queuedTooltips.add(new StringTextComponent(TextFormatting.WHITE + "Tool Type: " + TextFormatting.GRAY + tool.getName())));

            if (!queuedTooltips.isEmpty())
            {
                event.getToolTip().add(new StringTextComponent(TextFormatting.DARK_GREEN + "Debug:"));
                event.getToolTip().addAll(queuedTooltips);
            }
        }
    }
}
