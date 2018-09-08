/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.item.tool;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.ParametersAreNonnullByDefault;

import com.google.common.collect.Sets;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.alcatrazescapee.alcatrazcore.client.IModelProvider;

@ParametersAreNonnullByDefault
public class ItemToolCore extends ItemTool implements IModelProvider
{
    protected final int harvestLevel;
    protected Set<Material> effectiveMaterials = new HashSet<>();

    public ItemToolCore(ToolMaterial material)
    {
        super(material, Sets.newHashSet());
        harvestLevel = material.getHarvestLevel();
    }

    public ItemToolCore(ToolMaterial material, float damage, float speed)
    {
        super(damage, speed, material, Sets.newHashSet());
        harvestLevel = material.getHarvestLevel();
    }

    @SideOnly(Side.CLIENT)
    @Override
    @SuppressWarnings("ConstantConditions")
    public void registerModel()
    {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(this.getRegistryName().toString()));
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state)
    {
        return effectiveMaterials.contains(state.getMaterial()) ? this.efficiency : super.getDestroySpeed(stack, state);
    }

    @Override
    public boolean canHarvestBlock(IBlockState state)
    {
        return effectiveMaterials.contains(state.getMaterial()) || harvestLevel > state.getBlock().getHarvestLevel(state);
    }

    protected void addToolClass(ToolClass clazz)
    {
        setHarvestLevel(clazz.name().toLowerCase(), harvestLevel);
    }

    protected enum ToolClass
    {
        PICKAXE,
        SHOVEL,
        AXE
    }
}
