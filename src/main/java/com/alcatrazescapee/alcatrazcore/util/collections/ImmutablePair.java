/*
 * Part of the AlcatrazCore mod by AlcatrazEscapee
 * Work under Copyright. Licensed under the GPL-3.0.
 * See the project LICENSE.md for more information.
 */

package com.alcatrazescapee.alcatrazcore.util.collections;

import java.util.Map;

public class ImmutablePair<K, V> implements Map.Entry<K, V>
{
    public static <K, V> ImmutablePair<K, V> of(K key, V value)
    {
        return new ImmutablePair<>(key, value);
    }

    private final K key;
    private final V value;

    private ImmutablePair(K key, V value)
    {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey()
    {
        return key;
    }

    @Override
    public V getValue()
    {
        return value;
    }

    @Override
    public V setValue(V value)
    {
        throw new UnsupportedOperationException("Tried to set value in ImmutablePair");
    }

    @Override
    public int hashCode()
    {
        return value.hashCode() + 65536 * key.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof ImmutablePair)) return false;
        ImmutablePair other = (ImmutablePair) obj;
        return this.key.equals(other.key) && this.value.equals(other.value);
    }
}
