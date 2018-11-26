/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.network.capability;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;

import com.alcatrazescapee.alcatrazcore.AlcatrazCore;
import com.alcatrazescapee.alcatrazcore.network.PacketTContainerUpdate;

import static com.alcatrazescapee.alcatrazcore.AlcatrazCore.MOD_ID;

/**
 * This is a manager for syncing item stack capability data
 * To register a syncable item stack capability requires two calls:
 * 1. Register a listener factory with {@link CapabilityContainerListenerManager#registerContainerListenerFactory}
 * 2. Register an update packet with {@link CapabilityContainerListenerManager#registerTContainerPacket}
 * These will require implementations of their respective classes:
 * PacketTContainerUpdate.Handler, PacketTContainerUpdate, and CapabilityContainerListener
 *
 * @author Choonster
 * @author AlcatrazEscapee
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class CapabilityContainerListenerManager
{
    private static final Set<Function<EntityPlayerMP, CapabilityContainerListener<?>>> containerListenerFactories = new HashSet<>();

    public static void registerContainerListenerFactory(Function<EntityPlayerMP, CapabilityContainerListener<?>> factory)
    {
        containerListenerFactories.add(factory);
    }

    public static <T, D, M extends PacketTContainerUpdate<T, D>> void registerTContainerPacket(Class<M> packet, PacketTContainerUpdate.Handler<T, D, M> packetHandler)
    {
        AlcatrazCore.registerMessage(packetHandler, packet, Side.CLIENT);
    }

    private static void addListeners(EntityPlayerMP player, Container container)
    {
        containerListenerFactories.forEach(
                factory -> container.addListener(factory.apply(player))
        );
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, value = Side.SERVER)
    private static class EventHandler
    {
        @SubscribeEvent
        public static void onPlayerLoggedInEvent(final PlayerEvent.PlayerLoggedInEvent event)
        {
            if (event.player instanceof EntityPlayerMP)
            {
                final EntityPlayerMP player = (EntityPlayerMP) event.player;
                addListeners(player, player.inventoryContainer);
            }
        }

        @SubscribeEvent
        public static void onPlayerCloneEvent(final net.minecraftforge.event.entity.player.PlayerEvent.Clone event)
        {
            if (event.getEntityPlayer() instanceof EntityPlayerMP)
            {
                final EntityPlayerMP player = (EntityPlayerMP) event.getEntityPlayer();
                addListeners(player, player.inventoryContainer);
            }
        }

        @SubscribeEvent
        public static void onContainerOpenEvent(final PlayerContainerEvent.Open event)
        {
            if (event.getEntityPlayer() instanceof EntityPlayerMP)
            {
                final EntityPlayerMP player = (EntityPlayerMP) event.getEntityPlayer();
                addListeners(player, event.getContainer());
            }
        }
    }
}
