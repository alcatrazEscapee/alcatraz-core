/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package alcatrazcore.item.tool;

import net.minecraft.block.material.Material;

public class ItemAxeCore extends ItemToolCore
{
    public ItemAxeCore(ToolMaterial material)
    {
        this(material, material.getAttackDamage(), -3.0F);
    }

    public ItemAxeCore(ToolMaterial material, float damage, float speed)
    {
        super(material, damage, speed);

        addToolClass(ToolClass.AXE);
        effectiveMaterials.add(Material.WOOD);
        effectiveMaterials.add(Material.PLANTS);
        effectiveMaterials.add(Material.VINE);
    }
}
