/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.oredict.OreDictionary;

import mcp.MethodsReturnNonnullByDefault;

import static com.alcatrazescapee.alcatrazcore.AlcatrazCore.MOD_ID;

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

    /**
     * Alternative to world#getTopSolidOrLiquidBlock() This will skip liquid blocks
     *
     * @param world The world
     * @param pos   The block pos (only x and z coordinates)
     * @return The block pos with all coordinates
     */
    public static BlockPos getTopSolidBlock(World world, BlockPos pos)
    {
        Chunk chunk = world.getChunk(pos);
        BlockPos blockpos;
        BlockPos blockpos1;

        for (blockpos = new BlockPos(pos.getX(), chunk.getTopFilledSegment() + 16, pos.getZ()); blockpos.getY() >= 0; blockpos = blockpos1)
        {
            blockpos1 = blockpos.down();
            IBlockState state = chunk.getBlockState(blockpos1);

            if (state.getMaterial().blocksMovement() && !state.getBlock().isLeaves(state, world, blockpos1) && !state.getBlock().isFoliage(world, blockpos1) && !state.getMaterial().isLiquid())
            {
                break;
            }
        }

        return blockpos;
    }

    /**
     * Alternative to world#getTopSolidOrLiquidBlock() This will stop at the first non air block
     *
     * @param world The world
     * @param pos   The block pos (only x and z coordinates)
     * @return The block pos with all coordinates
     */
    public static BlockPos getTopNonAirBlock(World world, BlockPos pos)
    {
        Chunk chunk = world.getChunk(pos);
        BlockPos blockpos;
        BlockPos blockpos1;

        for (blockpos = new BlockPos(pos.getX(), chunk.getTopFilledSegment() + 16, pos.getZ()); blockpos.getY() >= 0; blockpos = blockpos1)
        {
            blockpos1 = blockpos.down();
            IBlockState state = chunk.getBlockState(blockpos1);

            if (state.getBlock() != Blocks.AIR)
            {
                break;
            }
        }

        return blockpos;
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

    /**
     * @param stack An item stack
     * @param name An ore dictionary name
     * @return true if the stack has an ore dictionary name equal to the provided parameter name
     */
    public static boolean doesStackMatchOre(ItemStack stack, String name)
    {
        if (stack.isEmpty()) return false;
        int[] ids = OreDictionary.getOreIDs(stack);
        for (int id : ids)
        {
            String oreName = OreDictionary.getOreName(id);
            if (name.equals(oreName))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * @param stack An item stack
     * @param prefix An ore dictionary prefix
     * @return true if the stack has an ore dictionary entry that starts with the provided prefix (i.e. "ingotGold' has the prefix 'ingot')
     */
    public static boolean doesStackMatchOrePrefix(ItemStack stack, String prefix)
    {
        if (stack.isEmpty()) return false;
        int[] ids = OreDictionary.getOreIDs(stack);
        for (int id : ids)
        {
            String oreName = OreDictionary.getOreName(id);
            if (oreName.length() >= prefix.length())
            {
                if (oreName.substring(0, prefix.length()).equals(prefix))
                {
                    return true;
                }
            }
        }
        return false;
    }

    // This both checks if an ore dictionary entry exists, and it it has at least one itemstack

    /**
     * @param ore An ore dictionary entry
     * @return true if the entry exists, and it has at least one stack associated to it
     */
    public static boolean doesOreHaveStack(String ore)
    {
        if (!OreDictionary.doesOreNameExist(ore)) return false;
        NonNullList<ItemStack> stacks = OreDictionary.getOres(ore);
        return !stacks.isEmpty();
    }

    public static ItemStack getStackForName(String name)
    {
        return getStackForName(name, MOD_ID);
    }

    public static ItemStack getStackForName(String name, String preferredModID)
    {
        if (OreDictionary.doesOreNameExist(name))
        {
            NonNullList<ItemStack> stacks = OreDictionary.getOres(name);
            if (!stacks.isEmpty())
            {
                if (stacks.size() == 1)
                    return stacks.get(0);

                //noinspection ConstantConditions
                return stacks.stream()
                        .filter(x -> x.getItem().getRegistryName().getNamespace().equals(preferredModID))
                        .findFirst()
                        .orElse(stacks.get(0));
            }
        }
        return ItemStack.EMPTY;
    }

    /**
     * @return null, but with @Nonnull. Used for fields that are static final = null but injected into later, to avoid IDE warnings
     */
    @Nonnull
    @SuppressWarnings({"ConstantConditions", "SameReturnValue"})
    public static <T> T getNull()
    {
        return null;
    }

}
