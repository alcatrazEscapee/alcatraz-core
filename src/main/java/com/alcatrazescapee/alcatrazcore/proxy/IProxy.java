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

public interface IProxy
{
    IThreadListener getThreadListener(MessageContext context);

    EntityPlayer getPlayer(MessageContext context);

    World getClientWorld();

    EntityPlayer getClientPlayer();

    class WrongSideException extends RuntimeException
    {
        WrongSideException(String message)
        {
            super(message);
        }
    }
}
