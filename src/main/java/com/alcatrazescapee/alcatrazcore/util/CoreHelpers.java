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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import com.alcatrazescapee.alcatrazcore.util.collections.ImmutablePair;
import mcp.MethodsReturnNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@SuppressWarnings({"WeakerAccess", "unused"})
public final class CoreHelpers
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

    public static void giveItemToPlayer(World world, EntityPlayer player, ItemStack stack)
    {
        if (!player.addItemStackToInventory(stack))
        {
            dropItemInWorldExact(world, player.getPosition(), stack);
        }
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
     * @return true if the stacks have the same item and metadata, or if one has a wildcard metadata
     */
    public static boolean doStacksMatch(ItemStack stack1, ItemStack stack2)
    {
        if (stack1.isEmpty())
            return stack2.isEmpty();
        if (stack2.isEmpty())
            return false;
        if (stack1.getItem() != stack2.getItem())
            return false;
        if (stack1.getMetadata() == OreDictionary.WILDCARD_VALUE || stack2.getMetadata() == OreDictionary.WILDCARD_VALUE)
            return true;
        return stack1.getMetadata() == stack2.getMetadata();
    }

    /**
     * @return true if the stacks have the same item and metadata
     */
    public static boolean doStacksMatchIgnoreWildcard(ItemStack stack1, ItemStack stack2)
    {
        if (stack1.isEmpty())
            return stack2.isEmpty();
        return stack1.isItemEqual(stack2);
    }

    /**
     * @return true if the stacks have the same item and metadata, or if one has a wildcard metadata, and they have the same NBT
     */
    public static boolean doStacksMatchUseNBT(ItemStack stack1, ItemStack stack2)
    {
        return doStacksMatch(stack1, stack2) && ItemStack.areItemStackTagsEqual(stack1, stack2);
    }

    /**
     * @return true if one or both of the stacks is empty, or if the stacks match (excluding wildcards)
     */
    public static boolean canMergeStacks(ItemStack stack1, ItemStack stack2)
    {
        return stack1.isEmpty() || stack2.isEmpty() || doStacksMatchIgnoreWildcard(stack1, stack2);
    }

    /**
     * @return true if one or both of the stacks is empty, or if the stacks match (excluding wildcards), and the have the same NBT
     */
    public static boolean canMergeStacksUseNBT(ItemStack stack1, ItemStack stack2)
    {
        return stack1.isEmpty() || stack2.isEmpty() || doStacksMatchIgnoreWildcard(stack1, stack2) && ItemStack.areItemStackTagsEqual(stack1, stack2);
    }

    /**
     * Merges stack2 into stack1
     *
     * @return the merged stack. Excess is ignored
     */
    public static ItemStack mergeStacks(ItemStack stack1, ItemStack stack2)
    {
        if (stack1.isEmpty())
            return stack2;
        if (stack2.isEmpty())
            return stack1;
        int amount = Math.min(stack1.getMaxStackSize(), stack1.getCount() + stack2.getCount());
        stack1.setCount(amount);
        return stack1;
    }

    /**
     * Merges stack2 into stack1, accounting for excess
     * @return A pair consisting of the merged stack (key), and the excess stack (value)
     */
    public static ImmutablePair<ItemStack, ItemStack> mergeStacksWithResult(ItemStack stack1, ItemStack stack2)
    {
        if (stack1.isEmpty())
            return ImmutablePair.of(stack2, ItemStack.EMPTY);
        if (stack2.isEmpty())
            return ImmutablePair.of(stack1, ItemStack.EMPTY);

        int amount = stack1.getCount() + stack2.getCount();
        int maxAmount = stack1.getMaxStackSize();
        stack1.setCount(Math.min(maxAmount, amount));
        if (amount <= maxAmount)
            return ImmutablePair.of(stack1, ItemStack.EMPTY);

        stack2.setCount(amount - maxAmount);
        return ImmutablePair.of(stack1, stack2);
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

    public static boolean doesStackMatchOre(ItemStack stack, int id)
    {
        if (stack.isEmpty()) return false;
        int[] ids = OreDictionary.getOreIDs(stack);
        for (int i : ids)
        {
            if (id == i)
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

    /**
     * @param ore An ore dictionary entry
     * @return true if the entry exists, and it has at least one stack associated to it
     */
    public static boolean doesOreHaveStack(String ore)
    {
        if (!OreDictionary.doesOreNameExist(ore)) return false;
        NonNullList<ItemStack> stacks = OreDictionary.getOres(ore, false);
        return !stacks.isEmpty();
    }

    /**
     * @param ore An ore dictionary entry
     * @return The first stack associated to that ore. Used for @link RecipeIngredient
     */
    public static ItemStack getStackForOre(String ore)
    {
        if (OreDictionary.doesOreNameExist(ore))
        {
            NonNullList<ItemStack> stacks = OreDictionary.getOres(ore, false);
            if (!stacks.isEmpty())
            {
                return stacks.get(0);
            }
        }
        return ItemStack.EMPTY;
    }

    /**
     * Used to get an ore stack with a preferred mod ID. Used for dynamic recipe output
     *
     * @param ore            The ore name
     * @param preferredModID The mod ID to prefer. If an item with this mod ID is not found, it will default to the first entry
     */
    public static ItemStack getStackForOre(String ore, String preferredModID)
    {
        if (OreDictionary.doesOreNameExist(ore))
        {
            NonNullList<ItemStack> stacks = OreDictionary.getOres(ore, false);
            if (!stacks.isEmpty())
            {
                if (stacks.size() == 1)
                {
                    return stacks.get(0);
                }
                //noinspection ConstantConditions
                return stacks.stream()
                        .filter(x -> x.getItem().getRegistryName().getNamespace().equals(preferredModID))
                        .findFirst()
                        .orElse(stacks.get(0));
            }
        }
        return ItemStack.EMPTY;
    }

    public static String getOreName(ItemStack stack)
    {
        int id = getOreID(stack);
        if (id >= 0)
        {
            return OreDictionary.getOreName(id);
        }
        return "";
    }

    public static int getOreID(ItemStack stack)
    {
        int[] ids = OreDictionary.getOreIDs(stack);
        if (ids.length >= 1)
        {
            return ids[0];
        }
        return -1;
    }

    public static ItemStack getStackByRegistryName(String name, int amount, int meta)
    {
        Item item = Item.getByNameOrId(name);
        if (item == null)
        {
            return ItemStack.EMPTY;
        }
        return new ItemStack(item, amount, meta);
    }

    public static boolean doStacksMatch(@Nullable FluidStack stack1, @Nullable FluidStack stack2)
    {
        if (stack1 == null)
            return stack2 == null;
        if (stack2 == null)
            return false;
        return stack1.getFluid() == stack2.getFluid();
    }

    public static boolean doStacksMatchUseNBT(@Nullable FluidStack stack1, @Nullable FluidStack stack2)
    {
        return stack1 != null ? stack1.isFluidEqual(stack2) : stack2 == null;
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
