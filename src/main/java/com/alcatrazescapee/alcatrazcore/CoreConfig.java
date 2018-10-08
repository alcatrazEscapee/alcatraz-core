/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore;

import net.minecraftforge.common.config.Config;

import static com.alcatrazescapee.alcatrazcore.AlcatrazCore.MOD_ID;

@Config(modid = MOD_ID)
public final class CoreConfig
{
    @Config.Comment({"When Advanced tooltips are enabled, show extra information about the item", "(Ore dictionary values, NBT data, registry + translation keys, etc.)"})
    public static boolean showDebugTooltips = false;
}
