/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.tile;

/**
 * An interface that is implemented by a tile entity to send field information to clients
 * Used in conjunction with {@link com.alcatrazescapee.alcatrazcore.inventory.container.ContainerTileInventory}
 *
 * @author AlcatrazEscapee
 */
public interface ITileFields
{
    int getFieldCount();

    int getField(int ID);

    void setField(int ID, int value);
}
