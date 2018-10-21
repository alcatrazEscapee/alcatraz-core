/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.inventory.container;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.alcatrazescapee.alcatrazcore.tile.ITileFields;
import com.alcatrazescapee.alcatrazcore.tile.TileInventory;

public abstract class ContainerTileInventory<T extends TileInventory> extends Container
{
    protected final T tile;
    protected final InventoryPlayer playerInv;

    private int[] cachedFields;

    public ContainerTileInventory(InventoryPlayer playerInv, T tile)
    {
        this(playerInv, tile, 0, 0);
    }

    public ContainerTileInventory(InventoryPlayer playerInv, T tile, int playerSlotOffsetX, int playerSlotOffsetY)
    {
        this.tile = tile;
        this.playerInv = playerInv;

        addContainerSlots();
        addPlayerInventorySlots(playerInv, playerSlotOffsetX, playerSlotOffsetY);
    }

    @Override
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        if (tile instanceof ITileFields)
        {
            ITileFields tileFields = (ITileFields) tile;
            boolean allFieldsHaveChanged = false;
            boolean fieldHasChanged[] = new boolean[tileFields.getFieldCount()];

            if (cachedFields == null)
            {
                cachedFields = new int[tileFields.getFieldCount()];
                allFieldsHaveChanged = true;
            }

            for (int i = 0; i < cachedFields.length; ++i)
            {
                if (allFieldsHaveChanged || cachedFields[i] != tileFields.getField(i))
                {
                    cachedFields[i] = tileFields.getField(i);
                    fieldHasChanged[i] = true;
                }
            }

            // go through the list of listeners (players using this container) and update them if necessary
            for (IContainerListener listener : this.listeners)
            {
                for (int fieldID = 0; fieldID < tileFields.getFieldCount(); ++fieldID)
                {
                    if (fieldHasChanged[fieldID])
                    {
                        // Note that although sendWindowProperty takes 2 ints on a server these are truncated to shorts
                        listener.sendWindowProperty(this, fieldID, cachedFields[fieldID]);
                    }
                }
            }
        }
    }

    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer player)
    {
        return true;
    }

    /**
     * @return EMPTY if nothing changed, otherwise return the original stack (a copy of)
     */
    @Override
    @Nonnull
    public ItemStack transferStackInSlot(EntityPlayer player, int index)
    {
        // Slot that was clicked
        Slot slot = inventorySlots.get(index);
        if (slot == null || !slot.getHasStack())
            return ItemStack.EMPTY;

        ItemStack stack = slot.getStack();
        ItemStack stackCopy = stack.copy();
        int containerSlots = inventorySlots.size() - player.inventory.mainInventory.size(); // number of slots in the container

        if (index < containerSlots)
        {
            // Transfer out of the container
            if (!this.mergeItemStack(stack, containerSlots, inventorySlots.size(), true))
            {
                return ItemStack.EMPTY;
            }
            tile.setAndUpdateSlots(index);
        }
        else
        {
            // Transfer into the container
            for (int i = 0; i < containerSlots; i++)
            {
                if (inventorySlots.get(i).isItemValid(stack))
                {
                    if (this.mergeItemStack(stack, i, i + 1, false))
                    {
                        tile.setAndUpdateSlots(i);
                    }
                }
            }
        }

        // Required
        if (stack.getCount() == 0)
        {
            slot.putStack(ItemStack.EMPTY);
        }
        else
        {
            slot.onSlotChanged();
        }
        if (stack.getCount() == stackCopy.getCount())
        {
            return ItemStack.EMPTY;
        }
        slot.onTake(player, stack);
        return stackCopy;
    }

    // Called when a progress bar update is received from the server. The two values (id and data) are the same two
    // values given to sendWindowProperty.  In this case we are using fields so we just pass them to the tileEntity.
    @SideOnly(Side.CLIENT)
    @Override
    public void updateProgressBar(int id, int data)
    {
        if (tile instanceof ITileFields)
        {
            ((ITileFields) tile).setField(id, data);
        }
    }

    protected abstract void addContainerSlots();

    protected void addPlayerInventorySlots(InventoryPlayer playerInv, int offsetX, int offsetY)
    {
        // Add Player Inventory Slots
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, offsetX + 8 + j * 18, offsetY + 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; k++)
        {
            addSlotToContainer(new Slot(playerInv, k, offsetX + 8 + k * 18, offsetY + 142));
        }
    }
}
