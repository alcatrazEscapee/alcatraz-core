package com.alcatrazescapee.core.util;

import java.util.function.Supplier;

/**
 * Mimics {@link net.minecraftforge.common.util.Lazy} but with an invalidate function
 *
 * @since 2.0.0
 */
public interface Cache<T> extends Supplier<T>
{
    static <T> Cache<T> of(Supplier<T> supplier)
    {
        return new Fast<>(supplier);
    }

    void invalidate();

    final class Fast<T> implements Cache<T>
    {
        private final Supplier<T> supplier;
        private T cache;
        private boolean valid;

        private Fast(Supplier<T> supplier)
        {
            this.supplier = supplier;
        }

        @Override
        public T get()
        {
            if (!valid)
            {
                valid = true;
                cache = supplier.get();
            }
            return cache;
        }

        @Override
        public void invalidate()
        {
            valid = false;
        }
    }
}
