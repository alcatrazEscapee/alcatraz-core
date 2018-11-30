/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ServerProxy implements IProxy
{
    @Override
    public IThreadListener getThreadListener(MessageContext context)
    {
        if (context.side.isServer())
        {
            return context.getServerHandler().player.server;
        }
        else
        {
            throw new WrongSideException("Tried to get the IThreadListener from a client-side MessageContext on the dedicated server");
        }
    }

    @Override
    public EntityPlayer getPlayer(MessageContext context)
    {
        if (context.side.isServer())
        {
            return context.getServerHandler().player;
        }
        else
        {
            throw new WrongSideException("Tried to get the player from a client-side MessageContext on the dedicated server");
        }
    }

    @Override
    public World getClientWorld()
    {
        throw new WrongSideException("Tried to get the world from a client-side MessageContext on the dedicated server");
    }

    @Override
    public EntityPlayer getClientPlayer()
    {
        throw new WrongSideException("Tried to get the client player on a dedicated server");
    }
}