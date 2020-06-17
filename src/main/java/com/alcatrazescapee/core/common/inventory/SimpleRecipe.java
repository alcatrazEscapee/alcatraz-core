/*
 * Part of the Primal Alchemy mod by AlcatrazEscapee.
 * Copyright (c) 2019. See the project LICENSE.md for details.
 */

package com.alcatrazescapee.core.common.inventory;

import com.google.gson.JsonObject;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;

import javax.annotation.Nullable;

/**
 * Simple 1 -> 1 recipe
 *
 * @since 2.0.0
 */
public abstract class SimpleRecipe<C extends IInventory> implements ISimpleRecipe<C>
{
    protected final ResourceLocation id;
    protected final Ingredient ingredient;
    protected final ItemStack output;
    protected final NonNullList<Ingredient> ingredientList;

    protected SimpleRecipe(ResourceLocation id, Ingredient ingredient, ItemStack output)
    {
        this.id = id;
        this.ingredient = ingredient;
        this.output = output;
        this.ingredientList = NonNullList.withSize(1, ingredient);
    }

    @Override
    public ItemStack getRecipeOutput()
    {
        return output;
    }

    @Override
    public NonNullList<Ingredient> getIngredients()
    {
        return ingredientList;
    }

    @Override
    public ResourceLocation getId()
    {
        return id;
    }

    public static abstract class Serializer<R extends SimpleRecipe<?>> extends RecipeSerializer<R>
    {
        @Override
        public R read(ResourceLocation recipeId, JsonObject json)
        {
            Ingredient ingredient = Ingredient.deserialize(json.get("ingredient"));
            ItemStack output = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "output"), true);
            return create(recipeId, ingredient, output);
        }

        @Nullable
        @Override
        public R read(ResourceLocation recipeId, PacketBuffer buffer)
        {
            Ingredient ingredient = Ingredient.read(buffer);
            ItemStack output = buffer.readItemStack();
            return create(recipeId, ingredient, output);
        }

        @Override
        public void write(PacketBuffer buffer, R recipe)
        {
            recipe.ingredient.write(buffer);
            buffer.writeItemStack(recipe.output);
        }

        protected abstract R create(ResourceLocation id, Ingredient ingredient, ItemStack output);
    }
}
