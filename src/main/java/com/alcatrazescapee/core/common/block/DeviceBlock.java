/*
 * Part of the Primal Alchemy mod by AlcatrazEscapee.
 * Copyright (c) 2019. See the project LICENSE.md for details.
 */

package com.alcatrazescapee.core.common.block;

import com.alcatrazescapee.core.common.tileentity.InventoryTileEntity;
import com.alcatrazescapee.core.util.CoreHelpers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * A block with a tile entity
 *
 * @since 2.0.0
 */
public class DeviceBlock extends Block
{
    public DeviceBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving)
    {
        CoreHelpers.getTE(worldIn, pos, InventoryTileEntity.class).ifPresent(tile -> {
            tile.onReplaced();
            worldIn.updateComparatorOutputLevel(pos, this);
        });
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }
}
