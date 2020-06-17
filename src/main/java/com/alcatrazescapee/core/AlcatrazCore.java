package com.alcatrazescapee.core;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(AlcatrazCore.MOD_ID)
public class AlcatrazCore {

    public static final String MOD_ID = "alcatrazcore";

    private static final Logger LOGGER = LogManager.getLogger();

    public AlcatrazCore()
    {
        LOGGER.info("What is up the world!");
    }
}
