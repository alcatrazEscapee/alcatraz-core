/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.block;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.alcatrazescapee.alcatrazcore.tile.TileCore;
import com.alcatrazescapee.alcatrazcore.util.CoreHelpers;

@ParametersAreNonnullByDefault
public abstract class BlockTileCore extends BlockCore implements ITileEntityProvider
{
    public BlockTileCore(Material material)
    {
        super(material);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
    {
        TileCore te = CoreHelpers.getTE(worldIn, pos, TileCore.class);
        if (te != null) te.onNeighborChange();
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileCore te = CoreHelpers.getTE(worldIn, pos, TileCore.class);
        if (te != null) te.onBreakBlock();
        super.breakBlock(worldIn, pos, state);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return createNewTileEntity(world, state.getBlock().getMetaFromState(state));
    }

    @Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor)
    {
        TileCore te = CoreHelpers.getTE(world, pos, TileCore.class);
        if (te != null) te.onNeighborTileChange();
        super.onNeighborChange(world, pos, neighbor);
    }
}
