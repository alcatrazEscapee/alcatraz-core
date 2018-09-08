/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.item.tool;

public class ItemPickCore extends ItemToolCore
{
    public ItemPickCore(ToolMaterial material)
    {
        super(material, 1.0F, -2.8F);

        addToolClass(ToolClass.PICKAXE);
    }
}
