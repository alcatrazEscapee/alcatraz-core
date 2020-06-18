/*
 * Part of the Alcatraz Core mod by AlcatrazEscapee.
 * Copyright (c) 2020. See the project LICENSE.md for details.
 */

package com.alcatrazescapee.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.Mod;

@Mod(AlcatrazCore.MOD_ID)
public class AlcatrazCore
{

    public static final String MOD_ID = "alcatrazcore";

    private static final Logger LOGGER = LogManager.getLogger();

    public AlcatrazCore()
    {
        LOGGER.info("What is up the world!");
        LOGGER.debug("If you can see this, debug logging is working.");

        Config.init();
    }
}
