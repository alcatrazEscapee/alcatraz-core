/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.network;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import com.alcatrazescapee.alcatrazcore.network.capability.CapabilityContainerListener;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import io.netty.buffer.ByteBuf;

/**
 * This is a template for a packet which is sent to the client to sync capability data
 * It is used by {@link CapabilityContainerListener}
 *
 * @param <T> The capability instance / interface
 * @param <D> A data type to serialize the capability instance to
 * @author Choonster
 * @author AlcatrazEscapee
 */
@ParametersAreNonnullByDefault
public abstract class PacketTContainerUpdate<T, D> implements IMessage
{
    protected final Capability<T> capability;

    final TIntObjectMap<D> capabilityData = new TIntObjectHashMap<>();
    EnumFacing targetFace;
    int windowID;

    protected PacketTContainerUpdate(Capability<T> capability)
    {
        this.capability = capability;
    }

    protected PacketTContainerUpdate(Capability<T> capability, @Nullable EnumFacing targetFace, int windowID, int slotID, T instance)
    {
        this.capability = capability;
        this.targetFace = targetFace;
        this.windowID = windowID;

        final D data = readCapability(instance);
        if (data != null)
        {
            capabilityData.put(slotID, data);
        }
    }

    protected PacketTContainerUpdate(Capability<T> capability, @Nullable EnumFacing targetFace, int windowID, NonNullList<ItemStack> items)
    {
        this.capability = capability;
        this.targetFace = targetFace;
        this.windowID = windowID;

        for (int i = 0; i < items.size(); i++)
        {
            final T cap = items.get(i).getCapability(capability, targetFace);

            if (cap != null)
            {
                final D data = readCapability(cap);

                if (data != null)
                {
                    capabilityData.put(i, data);
                }
            }
        }
    }

    @Override
    public final void fromBytes(final ByteBuf buf)
    {
        windowID = buf.readInt();

        final int facingIndex = buf.readByte();
        if (facingIndex >= 0)
        {
            targetFace = EnumFacing.byIndex(facingIndex);
        }
        else
        {
            targetFace = null;
        }

        final int numEntries = buf.readInt();
        for (int i = 0; i < numEntries; i++)
        {
            final int index = buf.readInt();
            final D data = deserializeCapability(buf);
            capabilityData.put(index, data);
        }
    }

    @Override
    public final void toBytes(final ByteBuf buf)
    {
        buf.writeInt(windowID);

        if (targetFace != null)
        {
            buf.writeByte(targetFace.getIndex());
        }
        else
        {
            buf.writeByte(-1);
        }

        buf.writeInt(capabilityData.size());
        capabilityData.forEachEntry((index, data) -> {
            buf.writeInt(index);
            serializeCapability(buf, data);
            return true;
        });
    }

    public final boolean hasData()
    {
        return !capabilityData.isEmpty();
    }

    public abstract D readCapability(final T instance);

    public abstract void serializeCapability(final ByteBuf buf, final D data);

    public abstract D deserializeCapability(final ByteBuf buf);

    @ParametersAreNonnullByDefault
    public abstract static class Handler<T, D, M extends PacketTContainerUpdate<T, D>> implements IMessageHandler<M, IMessage>
    {
        @Override
        public IMessage onMessage(final M message, final MessageContext ctx)
        {
            if (!message.hasData()) return null; // Don't do anything if no data was sent

            Minecraft.getMinecraft().addScheduledTask(() -> {
                final EntityPlayer player = Minecraft.getMinecraft().player;
                final Container container;

                if (message.windowID == 0)
                {
                    container = player.inventoryContainer;
                }
                else if (message.windowID == player.openContainer.windowId)
                {
                    container = player.openContainer;
                }
                else
                {
                    return;
                }

                message.capabilityData.forEachEntry((index, data) -> {
                    final ItemStack stack = container.getSlot(index).getStack();
                    final T cap = stack.getCapability(message.capability, message.targetFace);

                    if (cap != null)
                    {
                        final ItemStack newStack = stack.copy();

                        final T newCap = newStack.getCapability(message.capability, message.targetFace);
                        if (newCap != null)
                        {
                            applyCapability(newCap, data);

                            if (!cap.equals(newCap))
                            {
                                container.putStackInSlot(index, newStack);
                            }
                        }
                    }

                    return true;
                });
            });

            return null;
        }

        public abstract void applyCapability(T instance, D data);
    }
}
