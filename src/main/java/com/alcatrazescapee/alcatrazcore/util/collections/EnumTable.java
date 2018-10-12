/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.util.collections;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import com.google.common.collect.Table;

@ParametersAreNonnullByDefault
@SuppressWarnings({"SuspiciousMethodCalls", "WeakerAccess", "unused"})
public class EnumTable<R extends Enum<R>, C extends Enum<C>, V> implements Table<R, C, V>
{
    protected final EnumMap<R, EnumMap<C, V>> table;
    private final Class<R> rowClass;
    private final Class<C> colClass;

    public EnumTable(Class<R> rowClass, Class<C> colClass)
    {
        this(new EnumMap<>(rowClass), rowClass, colClass);
    }

    public EnumTable(Table<? extends R, ? extends C, ? extends V> table, Class<R> rowClass, Class<C> colClass)
    {
        this(rowClass, colClass);
        putAll(table);
    }

    public EnumTable(EnumMap<R, EnumMap<C, V>> table, Class<R> rowClass, Class<C> colClass)
    {
        this.table = table;
        this.colClass = colClass;
        this.rowClass = rowClass;
    }

    public Class<C> getColClass()
    {
        return colClass;
    }

    public Class<R> getRowClass()
    {
        return rowClass;
    }

    @Override
    public boolean contains(@Nullable Object rowKey, @Nullable Object columnKey)
    {
        if (rowKey == null || columnKey == null) return false;
        EnumMap<C, V> map = table.get(rowKey);
        if (map == null) return false;
        return map.containsKey(columnKey);
    }

    @Override
    public boolean containsRow(@Nullable Object rowKey)
    {
        return table.containsKey(rowKey);
    }

    @Override
    public boolean containsColumn(@Nullable Object columnKey)
    {
        return table.values().stream().anyMatch(x -> x.containsKey(columnKey));
    }

    @Override
    public boolean containsValue(@Nullable Object value)
    {
        if (value == null) return false;
        return table.values().stream().flatMap(x -> x.values().stream()).anyMatch(x -> x.equals(value));
    }

    @Override
    public V get(@Nullable Object rowKey, @Nullable Object columnKey)
    {
        if (rowKey == null || columnKey == null) return null;
        EnumMap<C, V> map = table.get(rowKey);
        return map == null ? null : map.get(columnKey);
    }

    @Override
    public boolean isEmpty()
    {
        return table.isEmpty();
    }

    @Override
    public int size()
    {
        if (table.isEmpty()) return 0;
        return table.values().stream().mapToInt(EnumMap::size).sum();
    }

    @Override
    public void clear()
    {
        table.values().forEach(EnumMap::clear);
        table.clear();
    }

    @Nullable
    @Override
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

    @Override
    public void putAll(Table<? extends R, ? extends C, ? extends V> table)
    {
        table.rowMap().forEach((r, x) -> x.forEach((c, v) -> put(r, c, v)));
    }

    @Nullable
    @Override
    public V remove(@Nullable Object rowKey, @Nullable Object columnKey)
    {
        if (rowKey == null || columnKey == null) return null;
        EnumMap<C, V> map = table.get(rowKey);
        if (map == null) return null;
        return map.remove(columnKey);
    }

    @Override
    @Nonnull
    public Map<C, V> row(R rowKey)
    {
        EnumMap map = table.get(rowKey);
        return map == null ? Collections.EMPTY_MAP : map;
    }

    @Override
    @Nonnull
    public Map<R, V> column(C columnKey)
    {
        EnumMap<R, V> map = new EnumMap<>(rowClass);
        table.forEach((r, c) -> {
            V v = c.get(columnKey);
            if (v != null)
                map.put(r, v);
        });
        return map;
    }

    @Override
    public Set<Cell<R, C, V>> cellSet()
    {
        return table.entrySet()
                .stream()
                .map(r ->
                        r.getValue()
                                .entrySet()
                                .stream()
                                .map(c -> new EnumCell<>(r.getKey(), c.getKey(), c.getValue()))
                                .collect(Collectors.toSet()))
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<R> rowKeySet()
    {
        return table.keySet();
    }

    @Override
    public Set<C> columnKeySet()
    {
        return table.values().stream().flatMap(x -> x.keySet().stream()).collect(Collectors.toSet());
    }

    @Override
    public Set<V> values()
    {
        return table.values().stream().flatMap(x -> x.values().stream()).collect(Collectors.toSet());
    }

    @Override
    public Map<R, Map<C, V>> rowMap()
    {
        return Collections.unmodifiableMap(table);
    }

    @Override
    public Map<C, Map<R, V>> columnMap()
    {
        Map<C, Map<R, V>> map = new EnumMap<>(colClass);
        for (C c : columnKeySet())
            map.put(c, column(c));
        return Collections.unmodifiableMap(map);
    }

    public static class EnumCell<R, C, V> implements Table.Cell<R, C, V>
    {
        private R rowKey;
        private C colKey;
        private V value;

        public EnumCell(R rowKey, C colKey, V value)
        {
            this.rowKey = rowKey;
            this.colKey = colKey;
            this.value = value;
        }

        @Nullable
        @Override
        public R getRowKey()
        {
            return rowKey;
        }

        @Nullable
        @Override
        public C getColumnKey()
        {
            return colKey;
        }

        @Nullable
        @Override
        public V getValue()
        {
            return value;
        }
    }
}