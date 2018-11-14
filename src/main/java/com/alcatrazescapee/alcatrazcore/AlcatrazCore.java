/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore;

import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.ICrashCallable;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.alcatrazescapee.alcatrazcore.util.OreDictionaryHelper;
import com.alcatrazescapee.alcatrazcore.util.compat.FireRegistry;
import com.alcatrazescapee.alcatrazcore.util.compat.WrenchRegistry;

import static com.alcatrazescapee.alcatrazcore.AlcatrazCore.*;

/**
 * A Library mod that does various helpers things
 *
 * @author AlcatrazEscapee
 */
@SuppressWarnings({"WeakerAccess", "unused"})
@Mod(modid = MOD_ID, version = VERSION, dependencies = DEPENDENCIES, name = MOD_NAME, useMetadata = true, certificateFingerprint = "3c2d6be715971d1ed58a028cdb3fae72987fc934")
public final class AlcatrazCore
{
    public static final String MOD_ID = "alcatrazcore";
    public static final String MOD_NAME = "Alcatraz Core";
    public static final String VERSION = "GRADLE:VERSION";

    private static final String FORGE_MIN = "14.23.4.2705";
    private static final String FORGE_MAX = "15.0.0.0";

    public static final String DEPENDENCIES = "required-after:forge@[" + FORGE_MIN + "," + FORGE_MAX + ");";

    @Mod.Instance
    private static AlcatrazCore instance;
    private static Logger log;
    private static boolean isSignedBuild = true;

    public static AlcatrazCore getInstance()
    {
        return instance;
    }

    public static Logger getLog()
    {
        return log;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        log = event.getModLog();
        if (!isSignedBuild)
            log.warn("You are not running an official build. This version will NOT be supported by the author.");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        if (!isSignedBuild)
            log.warn("You are not running an official build. This version will NOT be supported by the author.");

        OreDictionaryHelper.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        if (!isSignedBuild)
            log.warn("You are not running an official build. This version will NOT be supported by the author.");

        FireRegistry.init();
        WrenchRegistry.init();
    }

    @Mod.EventHandler
    public void onFingerprintViolation(FMLFingerprintViolationEvent event)
    {
        isSignedBuild = false;
        FMLCommonHandler.instance().registerCrashCallable(new ICrashCallable()
        {
            @Override
            public String getLabel()
            {
                return MOD_NAME;
            }

            @Override
            public String call()
            {
                return "You are not running an official build. This version will NOT be supported by the author.";
            }
        });
    }
}
