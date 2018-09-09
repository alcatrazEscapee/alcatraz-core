/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.item;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.alcatrazescapee.alcatrazcore.client.IModelProvider;

@ParametersAreNonnullByDefault
public class ItemArmorCore extends ItemArmor implements IModelProvider
{
    public ItemArmorCore(ArmorMaterial material, EntityEquipmentSlot slot)
    {
        super(material, 0, slot);
    }

    public ItemArmorCore(ArmorMaterial material, String slotName)
    {
        // Possible values: feet, legs, chest, head
        super(material, 0, EntityEquipmentSlot.fromString(slotName));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerModel()
    {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(this.getRegistryName().toString()));
    }
}
