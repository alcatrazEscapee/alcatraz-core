/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.alcatrazescapee.alcatrazcore.client.IModelProvider;

public class BlockFluidCore extends BlockFluidClassic implements IModelProvider
{
    public BlockFluidCore(Fluid fluid, Material material)
    {
        super(fluid, material);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel()
    {
        ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(LEVEL).build());
    }
}
