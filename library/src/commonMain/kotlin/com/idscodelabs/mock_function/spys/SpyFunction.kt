package com.idscodelabs.mock_function.spys

import com.idscodelabs.mock_function.core.ISpyFunction

class SpyFunction<R>(
    private val function: ()->R
): ISpyFunction(),
        () -> R {
    override fun invoke(): R{
        onInvokeWithArguments()
        return function()
    }
}

fun SpyFunction(function: ()->Unit) = SpyFunction<Unit>(function)