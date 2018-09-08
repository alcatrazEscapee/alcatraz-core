/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package alcatrazcore.util;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import mcp.MethodsReturnNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@SuppressWarnings({"WeakerAccess", "unused"})
public class CoreHelpers
{
    @Nullable
    @SuppressWarnings("unchecked")
    public static <TE extends TileEntity> TE getTE(IBlockAccess world, BlockPos pos, Class<TE> clazz)
    {
        TileEntity te = world.getTileEntity(pos);
        if (clazz.isInstance(te)) return (TE) te;
        return null;
    }

    public static void dropItemInWorld(World world, BlockPos pos, ItemStack stack)
    {
        InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
    }

    public static void dropItemInWorldExact(World world, BlockPos pos, ItemStack stack)
    {
        world.spawnEntity(new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack));
    }

    public static void dropItemInWorldExact(World world, double x, double y, double z, ItemStack stack)
    {
        world.spawnEntity(new EntityItem(world, x, y, z, stack));
    }

    public static ItemStack consumeItem(ItemStack stack)
    {
        return consumeItem(stack, 1);
    }

    public static ItemStack consumeItem(ItemStack stack, int amount)
    {
        if (stack.getCount() > amount)
        {
            stack.shrink(amount);
            return stack;
        }
        return ItemStack.EMPTY;
    }

    public static ItemStack consumeItem(EntityPlayer player, ItemStack stack)
    {
        return player.isCreative() ? stack : consumeItem(stack, 1);
    }

    public static ItemStack consumeItem(EntityPlayer player, ItemStack stack, int amount)
    {
        return player.isCreative() ? stack : consumeItem(stack, amount);
    }

    @SuppressWarnings({"ConstantConditions", "SameReturnValue"})
    public static <T> T getNull()
    {
        return null;
    }

}
