/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.proxy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClientProxy implements IProxy
{
    @Nonnull
    @Override
    public IThreadListener getThreadListener(MessageContext context)
    {
        if (context.side.isClient())
        {
            return Minecraft.getMinecraft();
        }
        else
        {
            return context.getServerHandler().player.server;
        }
    }

    @Override
    @Nullable
    public EntityPlayer getPlayer(MessageContext context)
    {
        if (context.side.isClient())
        {
            return Minecraft.getMinecraft().player;
        }
        else
        {
            return context.getServerHandler().player;
        }
    }

    @Override
    public World getClientWorld()
    {
        return Minecraft.getMinecraft().world;
    }

    @Override
    public EntityPlayer getClientPlayer()
    {
        return Minecraft.getMinecraft().player;
    }
}
