package com.idscodelabs.mock_function.functions

import com.idscodelabs.mock_function.core.IMockFunction
import com.idscodelabs.mock_function.result.ResultProducer
import com.idscodelabs.mock_function.result.ResultBuilderScope

class MockFunction<R>(
    resultProducer: ResultProducer<R>,
) : IMockFunction<R>(resultProducer),
    () -> R {
    override fun invoke(): R = onInvoke()
}

fun MockFunction() = MockFunction(Unit)
fun <R> MockFunction(result: R) = MockFunction(ResultProducer.always(result))
fun <R> MockFunction(resultBuilder: ResultBuilderScope<R>.() -> Unit) = MockFunction(ResultProducer.from(resultBuilder))