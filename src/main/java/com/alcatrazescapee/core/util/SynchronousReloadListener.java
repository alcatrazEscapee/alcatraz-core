/*
 * Part of the Alcatraz Core mod by AlcatrazEscapee.
 * Copyright (c) 2020. See the project LICENSE.md for details.
 */

package com.alcatrazescapee.core.util;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResourceManager;

/**
 * A simple reload listener that can be used to do synchronous (i.e. on game thread) work
 *
 * @since 2.0.0
 */
public abstract class SynchronousReloadListener implements IFutureReloadListener
{
    @Override
    public CompletableFuture<Void> reload(IStage stage, IResourceManager resourceManager, IProfiler preparationsProfiler, IProfiler reloadProfiler, Executor backgroundExecutor, Executor gameExecutor)
    {
        return CompletableFuture.runAsync(() -> {}, backgroundExecutor).thenCompose(stage::markCompleteAwaitingOthers).thenRunAsync(this::doWork, gameExecutor);
    }

    protected abstract void doWork();
}
