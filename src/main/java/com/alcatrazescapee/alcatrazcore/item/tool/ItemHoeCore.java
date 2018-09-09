/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.item.tool;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemHoe;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.alcatrazescapee.alcatrazcore.client.IModelProvider;

@ParametersAreNonnullByDefault
public class ItemHoeCore extends ItemHoe implements IModelProvider
{
    public ItemHoeCore(ToolMaterial material)
    {
        super(material);
    }

    @SideOnly(Side.CLIENT)
    @Override
    @SuppressWarnings("ConstantConditions")
    public void registerModel()
    {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(this.getRegistryName().toString()));
    }
}
