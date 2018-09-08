/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package alcatrazcore;

import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import alcatrazcore.util.OreDictionaryHelper;

@Mod(modid = AlcatrazCore.MOD_ID, version = AlcatrazCore.VERSION, dependencies = AlcatrazCore.DEPENDENCIES, useMetadata = true)
public class AlcatrazCore
{
    public static final String MOD_ID = "alcatrazcore";
    public static final String MOD_NAME = "Alcatraz Core";
    public static final String VERSION = "GRADLE:VERSION";
    private static final String FORGE_VERSION = "GRADLE:FORGE_VERSION";
    private static final String FORGE_VERSION_MAX = "15.0.0.0";
    private static final String FORGE_REQUIRED = "required-after:forge@[" + FORGE_VERSION + "," + FORGE_VERSION_MAX + ");";
    public static final String DEPENDENCIES = FORGE_REQUIRED;

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
    }

}
