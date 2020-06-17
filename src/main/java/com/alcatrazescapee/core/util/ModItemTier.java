package com.alcatrazescapee.core.util;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

/**
 * Mod-usable implementation of {@link IItemTier}
 *
 * @since 2.0.0
 */
public class ModItemTier implements IItemTier
{
    private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final LazyValue<Ingredient> repairMaterial;

    public ModItemTier(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability)
    {
        this(harvestLevel, maxUses, efficiency, attackDamage, enchantability, () -> Ingredient.EMPTY);
    }

    public ModItemTier(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability, java.util.function.Supplier<Ingredient> repairMaterial)
    {
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairMaterial = new LazyValue<>(repairMaterial);
    }

    public int getMaxUses()
    {
        return maxUses;
    }

    public float getEfficiency()
    {
        return efficiency;
    }

    public float getAttackDamage()
    {
        return attackDamage;
    }

    public int getHarvestLevel()
    {
        return harvestLevel;
    }

    public int getEnchantability()
    {
        return enchantability;
    }

    public Ingredient getRepairMaterial()
    {
        return repairMaterial.getValue();
    }
}
