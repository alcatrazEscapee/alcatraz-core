/*
 * Part of the Alcatraz Core mod by AlcatrazEscapee.
 * Copyright (c) 2020. See the project LICENSE.md for details.
 */

package com.alcatrazescapee.core.common.tileentity;

import javax.annotation.Nullable;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import com.alcatrazescapee.core.common.inventory.ISlotCallback;
import com.alcatrazescapee.core.common.inventory.ItemStackHandlerCallback;
import com.alcatrazescapee.core.util.CoreHelpers;

/**
 * A tile entity that has a single basic inventory
 * Exposes the capability to the {@code null} side, other sides are left up to the implementing TE
 *
 * @since 2.0.0
 */
public abstract class InventoryTileEntity extends ModTileEntity implements ISlotCallback
{
    protected final ItemStackHandler inventory;
    protected final LazyOptional<IItemHandler> inventoryCapability;

    public InventoryTileEntity(TileEntityType<?> type, int inventorySlots)
    {
        super(type);

        this.inventory = new ItemStackHandlerCallback(this, inventorySlots);
        this.inventoryCapability = LazyOptional.of(() -> inventory);
    }

    public InventoryTileEntity(TileEntityType<?> type, ItemStackHandler inventory)
    {
        super(type);

        this.inventory = inventory;
        this.inventoryCapability = LazyOptional.of(() -> inventory);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side)
    {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && side == null)
        {
            return inventoryCapability.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void read(CompoundNBT nbt)
    {
        inventory.deserializeNBT(nbt.getCompound("inventory"));
        super.read(nbt);
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt)
    {
        nbt.put("inventory", inventory.serializeNBT());
        return super.write(nbt);
    }

    @Override
    public void setAndUpdateSlots(int slot)
    {
        markDirtyFast();
    }

    public boolean canInteractWith(PlayerEntity player)
    {
        return true;
    }

    public void onReplaced()
    {
        if (world != null && !world.isRemote)
        {
            CoreHelpers.dropInventoryItems(world, pos, inventory);
        }
    }
}

