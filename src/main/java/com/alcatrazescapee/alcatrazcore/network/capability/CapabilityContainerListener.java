/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.network.capability;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.Capability;

import com.alcatrazescapee.alcatrazcore.AlcatrazCore;
import com.alcatrazescapee.alcatrazcore.network.PacketTContainerUpdate;

/**
 * This is a {@link IContainerListener} which will monitor containers and send any capability data changes
 *
 * @param <T> is the capability interface / instance type
 * @author Choonster
 * @author AlcatrazEscapee
 */
@ParametersAreNonnullByDefault
public abstract class CapabilityContainerListener<T> implements IContainerListener
{
    private final EntityPlayerMP player;
    private final Capability<T> capability;
    private final EnumFacing targetFace;

    public CapabilityContainerListener(EntityPlayerMP player, Capability<T> capability, @Nullable EnumFacing targetFace)
    {
        this.player = player;
        this.capability = capability;
        this.targetFace = targetFace;
    }

    /**
     * This is called to send the entire container.
     * It does not update every tick
     */
    @Override
    public void sendAllContents(final Container container, final NonNullList<ItemStack> items)
    {
        // Filter out any items from the list that shouldn't be synced
        final NonNullList<ItemStack> syncableItemsList = NonNullList.withSize(items.size(), ItemStack.EMPTY);
        for (int index = 0; index < syncableItemsList.size(); index++)
        {
            final ItemStack stack = syncableItemsList.get(index);
            if (shouldSyncItem(stack))
            {
                syncableItemsList.set(index, stack);
            }
            else
            {
                syncableItemsList.set(index, ItemStack.EMPTY);
            }
        }

        final PacketTContainerUpdate<T, ?> message = createBulkUpdateMessage(container.windowId, syncableItemsList);
        if (message.hasData())
        {
            AlcatrazCore.getNetwork().sendTo(message, player);
        }
    }

    /**
     * This is called to send a single slot contents. It uses a modified packet factory method to accept a capability instance
     * The default container implementation will only call this when a non-capability change occurs
     * Any container that is modifying the capability needs to change this on every tick (i.e. in {@link Container#detectAndSendChanges))
     */
    @Override
    public void sendSlotContents(Container container, int slotIndex, ItemStack stack)
    {
        if (!shouldSyncItem(stack)) return;

        final T cap = stack.getCapability(capability, targetFace);
        if (cap == null) return;

        final PacketTContainerUpdate<T, ?> message = createSingleUpdateMessage(container.windowId, slotIndex, cap);
        if (message.hasData())
        { // Don't send the message if there's nothing to update
            AlcatrazCore.getNetwork().sendTo(message, player);
        }
    }

    @Override
    public void sendWindowProperty(Container container, int ID, int value)
    {
    }

    @Override
    public void sendAllWindowProperties(Container container, IInventory inventory)
    {
    }

    protected abstract PacketTContainerUpdate<T, ?> createBulkUpdateMessage(int containerID, NonNullList<ItemStack> items);

    protected abstract PacketTContainerUpdate<T, ?> createSingleUpdateMessage(int containerID, int slotID, T capability);

    protected boolean shouldSyncItem(ItemStack stack)
    {
        return stack.hasCapability(capability, targetFace);
    }
}
