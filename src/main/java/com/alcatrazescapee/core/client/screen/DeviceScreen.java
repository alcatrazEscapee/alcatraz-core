/*
 * Part of the Alcatraz Core mod by AlcatrazEscapee.
 * Copyright (c) 2020. See the project LICENSE.md for details.
 */

package com.alcatrazescapee.core.client.screen;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import com.alcatrazescapee.core.common.container.DeviceContainer;
import com.alcatrazescapee.core.common.tileentity.InventoryTileEntity;

/**
 * Screen for a {@link InventoryTileEntity} and {@link DeviceContainer}
 *
 * @since 2.0.0
 */
public class DeviceScreen<T extends InventoryTileEntity, C extends DeviceContainer<T>> extends ModContainerScreen<C>
{
    protected final T tile;

    public DeviceScreen(C container, PlayerInventory playerInventory, ITextComponent name, ResourceLocation texture)
    {
        super(container, playerInventory, name, texture);
        this.tile = container.getTileEntity();
    }
}
