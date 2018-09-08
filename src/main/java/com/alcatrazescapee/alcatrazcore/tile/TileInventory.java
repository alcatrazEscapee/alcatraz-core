/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.tile;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import com.alcatrazescapee.alcatrazcore.util.CoreHelpers;

@ParametersAreNonnullByDefault
public class TileInventory extends TileCore
{
    protected final ItemStackHandler inventory;

    public TileInventory(int numSlots)
    {
        inventory = new ItemStackHandler(numSlots);
    }

    public void setAndUpdateSlots(int slot)
    {
        markDirty();
    }

    public int getSlotLimit(int slot)
    {
        return 64;
    }

    public boolean isItemValid(int slot, ItemStack stack)
    {
        return true;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        inventory.deserializeNBT(nbt.getCompoundTag("inventory"));
        super.readFromNBT(nbt);
    }

    @Override
    @Nonnull
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        nbt.setTag("inventory", inventory.serializeNBT());
        return super.writeToNBT(nbt);
    }

    @Override
    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        NBTTagCompound nbt = getUpdateTag();
        return new SPacketUpdateTileEntity(this.pos, 1, nbt);
    }

    @Override
    @Nonnull
    public NBTTagCompound getUpdateTag()
    {
        NBTTagCompound nbt = new NBTTagCompound();
        writeToNBT(nbt);
        return nbt;
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
        NBTTagCompound nbt = pkt.getNbtCompound();
        handleUpdateTag(nbt);
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag)
    {
        this.readFromNBT(tag);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        return (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing == null) || super.hasCapability(capability, facing);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        return (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing == null) ? (T) inventory : super.getCapability(capability, facing);
    }

    @Override
    public void onBreakBlock()
    {
        for (int i = 0; i < inventory.getSlots(); i++)
        {
            CoreHelpers.dropItemInWorld(world, pos, inventory.getStackInSlot(i));
        }
        super.onBreakBlock();
    }
}
