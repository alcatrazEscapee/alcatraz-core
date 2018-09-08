/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package alcatrazcore.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import alcatrazcore.block.BlockFluidCore;
import alcatrazcore.client.IModelProvider;

@ParametersAreNonnullByDefault
@SuppressWarnings({"WeakerAccess", "UnusedReturnValue", "unused"})
public class RegistryHelper
{
    private static final Map<String, RegistryHelper> MAP = new HashMap<>();

    @Nonnull
    public static RegistryHelper get(String modID)
    {
        if (!MAP.containsKey(modID))
            MAP.put(modID, new RegistryHelper(modID));
        return MAP.get(modID);
    }

    private final String modID;
    private final List<Block> BLOCKS = new ArrayList<>();
    private final List<Item> ITEMS = new ArrayList<>();
    private final List<SoundEvent> SOUNDS = new ArrayList<>();

    private RegistryHelper(String modID)
    {
        this.modID = modID;
    }

    public void initModels(ModelRegistryEvent event)
    {
        for (Block block : BLOCKS)
            if (block instanceof IModelProvider)
                ((IModelProvider) block).registerModel();

        for (Item item : ITEMS)
            if (item instanceof IModelProvider)
                ((IModelProvider) item).registerModel();

        BLOCKS.clear();
        ITEMS.clear();
    }

    public void initBlocks(RegistryEvent.Register<Block> event)
    {
        IForgeRegistry<Block> registry = event.getRegistry();
        for (Block block : BLOCKS)
            registry.register(block);
    }

    public void initItems(RegistryEvent.Register<Item> event)
    {
        IForgeRegistry<Item> registry = event.getRegistry();
        for (Item item : ITEMS)
            registry.register(item);
    }

    public void initSounds(RegistryEvent.Register<SoundEvent> event)
    {
        IForgeRegistry<SoundEvent> registry = event.getRegistry();
        for (SoundEvent sound : SOUNDS)
            registry.register(sound);
        SOUNDS.clear();
    }

    // Items
    public <T extends Item> T registerItem(T item, String name, CreativeTabs tab)
    {
        item.setCreativeTab(tab);
        return registerItem(item, name);
    }

    public <T extends Item> T registerItem(T item, String name)
    {
        item.setRegistryName(modID + ":" + name.toLowerCase());
        item.setTranslationKey(modID + ":" + name.toLowerCase().replace('/', '.'));
        ITEMS.add(item);
        return item;
    }

    // Tile Entities
    public <T extends TileEntity> void registerTile(Class<T> clazz, String name)
    {
        TileEntity.register(modID + ":" + name.toLowerCase(), clazz);
    }

    // Fluids
    public <T extends Fluid> T registerFluid(T fluid, String name)
    {
        return registerFluid(fluid, name, Material.WATER, BlockFluidCore::new);
    }

    public <T extends Fluid> T registerFluid(T fluid, String name, Material material)
    {
        return registerFluid(fluid, name, material, BlockFluidCore::new);
    }

    // Sounds
    public <T extends SoundEvent> T registerSound(T sound, String name)
    {
        sound.setRegistryName(new ResourceLocation(modID, name));
        SOUNDS.add(sound);
        return sound;
    }

    public SoundEvent registerSound(String name)
    {
        ResourceLocation loc = new ResourceLocation(modID, name);
        SoundEvent sound = new SoundEvent(loc);
        sound.setRegistryName(loc);
        SOUNDS.add(sound);
        return sound;
    }

    public <T extends Fluid> T registerFluid(T fluid, String name, Material material, @Nullable BiFunction<T, Material, BlockFluidBase> fluidBlockSupplier)
    {
        fluid.setUnlocalizedName(modID + "." + name.toLowerCase().replace('/', '.'));
        FluidRegistry.registerFluid(fluid);
        if (fluidBlockSupplier != null)
        {
            BlockFluidBase fluidBlock = fluidBlockSupplier.apply(fluid, material);
            if (fluidBlock != null)
            {
                registerBlock(fluidBlock, null, "fluid/" + fluid.getName());
            }
        }

        return fluid;
    }

    // Blocks
    public <T extends Block> T registerBlock(T block, String name, CreativeTabs tab)
    {
        return registerBlock(block, ItemBlock::new, name, tab);
    }

    public <T extends Block> T registerBlock(T block, String name)
    {
        return registerBlock(block, ItemBlock::new, name);
    }

    public <T extends Block> T registerBlock(T block, @Nullable Function<Block, ItemBlock> itemBlockSupplier, String name, CreativeTabs tab)
    {
        block.setCreativeTab(tab);
        return registerBlock(block, itemBlockSupplier, name);
    }

    public <T extends Block> T registerBlock(T block, @Nullable Function<Block, ItemBlock> itemBlockSupplier, String name)
    {
        block.setRegistryName(modID + ":" + name);
        block.setTranslationKey(modID + ":" + name.replace('/', '.'));
        BLOCKS.add(block);
        if (itemBlockSupplier != null)
        {
            ItemBlock itemBlock = itemBlockSupplier.apply(block);
            if (itemBlock != null)
            {
                itemBlock.setRegistryName(modID + ":" + name);
                itemBlock.setTranslationKey(modID + ":" + name.replace('/', '.'));
                ITEMS.add(itemBlock);
            }
        }
        return block;
    }
}
