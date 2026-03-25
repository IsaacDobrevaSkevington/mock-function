package com.idscodelabs.mock_function.spys

import com.idscodelabs.mock_function.core.ISpyFunction

class SpySuspendFunction<R>(
    private val function: suspend ()->R
): ISpyFunction(),
        suspend () -> R {
    override suspend fun invoke(): R{
        onInvokeWithArguments()
        return function()
    }
}

fun SpySuspendFunction(function: suspend ()->Unit) = SpySuspendFunction<Unit>(function)