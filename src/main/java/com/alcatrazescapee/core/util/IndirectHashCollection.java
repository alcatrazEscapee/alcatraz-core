package com.alcatrazescapee.core.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * This is a structure which provides O(1), {@link HashMap} access of the wrapped {@code Map<Predicate<V>, R>}
 * It does this by using the "indirect key", or {@code K}. By using two constructs:
 * - The key mapper, a {@code Function<V, K>}.
 * - And the key extractor, a{@code Function<R, Iterable<K>>}.
 * The above must satisfy the following condition:
 * - For any K, V, R, {@code (keyMapper.apply(V) == K) -> (keyExtractor.apply(R).contains(K))}
 * This was benchmarked using VisualVM, with a recipe list of ~1000 recipes (not uncommon), over >10,000 invocations, and showed gains of ~300-700x faster than vanilla recipe queries.
 *
 * @since 2.0.0
 */
public class IndirectHashCollection<K, R>
{
    /**
     * A key extractor to be used with recipes with a single item ingredient.
     */
    public static <R extends IRecipe<C>, C extends IInventory> Function<R, Iterable<Item>> ingredientItemKeyExtractor(int ingredientIndex)
    {
        return recipe -> Arrays.stream(recipe.getIngredients().get(ingredientIndex).getMatchingStacks()).map(ItemStack::getItem).collect(Collectors.toList());
    }

    private final Map<K, Collection<R>> indirectResultMap;
    private final Function<R, Iterable<K>> keyExtractor;

    public IndirectHashCollection(Function<R, Iterable<K>> keyExtractor)
    {
        this.keyExtractor = keyExtractor;
        this.indirectResultMap = new HashMap<>();
    }

    /**
     * This is implemented for convenience rather than add / clear methods.
     */
    public void reload(Collection<R> results)
    {
        indirectResultMap.clear();
        results.forEach(result -> {
            for (K directKey : keyExtractor.apply(result))
            {
                indirectResultMap.computeIfAbsent(directKey, k -> new ArrayList<>()).add(result);
            }
        });
    }

    public Collection<R> getAll(K key)
    {
        return indirectResultMap.getOrDefault(key, Collections.emptySet());
    }
}
