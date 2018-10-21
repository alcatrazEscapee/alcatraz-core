/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore;

import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.alcatrazescapee.alcatrazcore.util.OreDictionaryHelper;
import com.alcatrazescapee.alcatrazcore.util.compat.FireRegistry;
import com.alcatrazescapee.alcatrazcore.util.compat.WrenchRegistry;

@Mod(modid = AlcatrazCore.MOD_ID, version = AlcatrazCore.VERSION, dependencies = AlcatrazCore.DEPENDENCIES, useMetadata = true)
public final class AlcatrazCore
{
    public static final String MOD_ID = "alcatrazcore";
    public static final String VERSION = "GRADLE:VERSION";
    public static final String DEPENDENCIES = "required-after:forge@[14.23.4.2705,15.0.0.0);";

    @Mod.Instance
    private static AlcatrazCore instance;
    private static Logger logger;

    public static AlcatrazCore getInstance()
    {
        return instance;
    }

    public static Logger getLog()
    {
        return logger;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        OreDictionaryHelper.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        FireRegistry.init();
        WrenchRegistry.init();
    }
}
