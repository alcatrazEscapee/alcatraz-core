/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.client.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.alcatrazescapee.alcatrazcore.tile.TileCore;

@SideOnly(Side.CLIENT)
public abstract class GuiContainerTileCore<T extends TileCore> extends GuiContainerCore
{
    protected final T tile;

    public GuiContainerTileCore(T tile, Container container, InventoryPlayer playerInv, ResourceLocation background, String titleKey)
    {
        super(container, playerInv, background, titleKey);

        this.tile = tile;
    }
}
