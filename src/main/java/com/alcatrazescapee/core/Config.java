/*
 * Part of the Alcatraz Core mod by AlcatrazEscapee.
 * Copyright (c) 2020. See the project LICENSE.md for details.
 */

package com.alcatrazescapee.core;

import org.apache.commons.lang3.tuple.Pair;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class Config
{
    public static final CommonConfig COMMON;
    private static final ForgeConfigSpec COMMON_SPEC;

    static
    {
        Pair<CommonConfig, ForgeConfigSpec> commonPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);

        COMMON_SPEC = commonPair.getRight();
        COMMON = commonPair.getLeft();
    }

    public static void init()
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC);
    }

    public static final class CommonConfig
    {
        public final ForgeConfigSpec.BooleanValue debugItemTooltips;

        private CommonConfig(ForgeConfigSpec.Builder builder)
        {
            debugItemTooltips = builder.comment("Enable some extra advanced tooltips on item stacks (NBT, Tags, etc.). Useful for debugging.").define("debugItemTooltips", false);
        }
    }
}
