/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.util.collections;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;

@ParametersAreNonnullByDefault
@SuppressWarnings("SuspiciousMethodCalls")
public class ImmutableEnumTable<R extends Enum<R>, C extends Enum<C>, V> extends EnumTable<R, C, V>
{
    private ImmutableEnumTable(EnumMap<R, EnumMap<C, V>> table, Class<R> rowClass, Class<C> colClass)
    {
        super(table, rowClass, colClass);
    }

    @Override
    @Deprecated
    public void clear()
    {
        throw new UnsupportedOperationException();
    }

    @Nullable
    @Override
    @Deprecated
    public V put(R rowKey, C columnKey, V value)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public void putAll(Table<? extends R, ? extends C, ? extends V> table)
    {
        throw new UnsupportedOperationException();
    }

    @Nullable
    @Override
    @Deprecated
    public V remove(@Nullable Object rowKey, @Nullable Object columnKey)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    @Nonnull
    public ImmutableMap<C, V> row(R rowKey)
    {
        EnumMap map = table.get(rowKey);
        return map == null ? ImmutableMap.of() : ImmutableMap.copyOf(map);
    }

    @Override
    @Nonnull
    public ImmutableMap<R, V> column(C columnKey)
    {
        ImmutableMap.Builder<R, V> b = ImmutableMap.builder();
        table.forEach((r, c) -> {
            V v = c.get(columnKey);
            if (v != null)
                b.put(r, v);
        });
        return b.build();
    }

    @Override
    public ImmutableSet<Cell<R, C, V>> cellSet()
    {
        return table.entrySet()
                .stream()
                .map(r ->
                        r.getValue()
                                .entrySet()
                                .stream()
                                .map(c -> Tables.immutableCell(r.getKey(), c.getKey(), c.getValue()))
                                .collect(Collectors.toSet()))
                .flatMap(Set::stream)
                .collect(ImmutableSet.toImmutableSet());
    }

    @Override
    public ImmutableSet<R> rowKeySet()
    {
        return ImmutableSet.copyOf(table.keySet());
    }

    @Override
    public ImmutableSet<C> columnKeySet()
    {
        return ImmutableSet.copyOf(table.values().stream().flatMap(x -> x.keySet().stream()).collect(ImmutableSet.toImmutableSet()));
    }

    @Override
    public ImmutableSet<V> values()
    {
        return table.values().stream().flatMap(x -> x.values().stream()).collect(ImmutableSet.toImmutableSet());
    }

    @Override
    public ImmutableMap<R, Map<C, V>> rowMap()
    {
        return ImmutableMap.copyOf(table);
    }

    @Override
    public ImmutableMap<C, Map<R, V>> columnMap()
    {
        ImmutableMap.Builder<C, Map<R, V>> b = ImmutableMap.builder();
        for (C c : columnKeySet())
            b.put(c, column(c));
        return b.build();
    }

    public static class Builder<R extends Enum<R>, C extends Enum<C>, V>
    {
        private final EnumMap<R, EnumMap<C, V>> table;
        private final Class<C> colClass;
        private final Class<R> rowClass;

        public Builder(Class<R> rowClass, Class<C> colClass)
        {
            this.colClass = colClass;
            this.rowClass = rowClass;
            table = new EnumMap<>(rowClass);
        }

        @Nullable
        public V put(R rowKey, C columnKey, V value)
        {
            EnumMap<C, V> map = table.get(rowKey);
            if (map == null)
            {
                map = new EnumMap<>(colClass);
                map.put(columnKey, value);
                table.put(rowKey, map);
                return null;
            }
            else
            {
                V old = map.get(columnKey);
                map.put(columnKey, value);
                return old;
            }
        }

        public void putAll(Table<? extends R, ? extends C, ? extends V> table)
        {
            table.rowMap().forEach((r, x) -> x.forEach((c, v) -> put(r, c, v)));
        }

        public ImmutableEnumTable<R, C, V> build()
        {
            return new ImmutableEnumTable<>(table, rowClass, colClass);
        }
    }
}
