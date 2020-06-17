/*
 * Part of the Primal Alchemy mod by AlcatrazEscapee.
 * Copyright (c) 2019. See the project LICENSE.md for details.
 */

package com.alcatrazescapee.core.common.tileentity;

import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.items.ItemStackHandler;

/**
 * An extension of {@link InventoryTileEntity} which has a custom name
 *
 * @since 2.0.0
 */
public abstract class DeviceTileEntity extends InventoryTileEntity implements INamedContainerProvider
{
    protected ITextComponent customName, defaultName;

    public DeviceTileEntity(TileEntityType<?> type, int inventorySlots, ITextComponent defaultName)
    {
        super(type, inventorySlots);
        this.defaultName = defaultName;
    }

    public DeviceTileEntity(TileEntityType<?> type, ItemStackHandler inventory, ITextComponent defaultName)
    {
        super(type, inventory);
        this.defaultName = defaultName;
    }

    @Override
    public ITextComponent getDisplayName()
    {
        return customName != null ? customName : defaultName;
    }

    public void setCustomName(ITextComponent customName)
    {
        this.customName = customName;
    }
}
