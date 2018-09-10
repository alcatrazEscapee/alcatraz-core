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
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;

@ParametersAreNonnullByDefault
@SuppressWarnings("SuspiciousMethodCalls")
public class ImmutableEnumTable<R extends Enum<R>, C extends Enum<C>, V> implements Table<R, C, V>
{
    private final EnumMap<R, EnumMap<C, V>> TABLE;

    private ImmutableEnumTable(EnumMap<R, EnumMap<C, V>> TABLE)
    {
        this.TABLE = TABLE;
    }

    @Override
    public boolean contains(@Nullable Object rowKey, @Nullable Object columnKey)
    {
        if (rowKey == null || columnKey == null) return false;
        EnumMap<C, V> map = TABLE.get(rowKey);
        if (map == null) return false;
        return map.containsKey(columnKey);
    }

    @Override
    public boolean containsRow(@Nullable Object rowKey)
    {
        return TABLE.containsKey(rowKey);
    }

    @Override
    public boolean containsColumn(@Nullable Object columnKey)
    {
        return TABLE.values().stream().anyMatch(x -> x.containsKey(columnKey));
    }

    @Override
    public boolean containsValue(@Nullable Object value)
    {
        if (value == null) return false;
        return TABLE.values().stream().flatMap(x -> x.values().stream()).anyMatch(x -> x.equals(value));
    }

    @Override
    public V get(@Nullable Object rowKey, @Nullable Object columnKey)
    {
        if (rowKey == null || columnKey == null) return null;
        EnumMap<C, V> map = TABLE.get(rowKey);
        return map == null ? null : map.get(columnKey);
    }

    @Override
    public boolean isEmpty()
    {
        return TABLE.isEmpty();
    }

    @Override
    public int size()
    {
        if (TABLE.isEmpty()) return 0;
        return TABLE.values().stream().mapToInt(EnumMap::size).sum();
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
    public ImmutableMap<C, V> row(R rowKey)
    {
        EnumMap map = TABLE.get(rowKey);
        return map == null ? ImmutableMap.of() : ImmutableMap.copyOf(TABLE.get(rowKey));
    }

    @Override
    public ImmutableMap<R, V> column(C columnKey)
    {
        ImmutableMap.Builder<R, V> b = ImmutableMap.builder();
        TABLE.forEach((r, c) -> {
            V v = c.get(columnKey);
            if (v != null)
                b.put(r, v);
        });
        return b.build();
    }

    @Override
    public ImmutableSet<Cell<R, C, V>> cellSet()
    {
        return TABLE.entrySet()
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
        return ImmutableSet.copyOf(TABLE.keySet());
    }

    @Override
    public ImmutableSet<C> columnKeySet()
    {
        return ImmutableSet.copyOf(TABLE.values().stream().flatMap(x -> x.keySet().stream()).collect(ImmutableSet.toImmutableSet()));
    }

    @Override
    public ImmutableSet<V> values()
    {
        return TABLE.values().stream().flatMap(x -> x.values().stream()).collect(ImmutableSet.toImmutableSet());
    }

    @Override
    public ImmutableMap<R, Map<C, V>> rowMap()
    {
        return ImmutableMap.copyOf(TABLE);
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
        private final EnumMap<R, EnumMap<C, V>> TABLE;
        private final Class<C> columnClass;

        public Builder(Class<R> rowClass, Class<C> columnClass)
        {
            this.columnClass = columnClass;
            TABLE = new EnumMap<>(rowClass);
        }

        @Nullable
        public V put(R rowKey, C columnKey, V value)
        {
            EnumMap<C, V> map = TABLE.get(rowKey);
            if (map == null)
            {
                map = new EnumMap<>(columnClass);
                map.put(columnKey, value);
                TABLE.put(rowKey, map);
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

        public ImmutableEnumTable build()
        {
            return new ImmutableEnumTable<>(TABLE);
        }
    }
}
