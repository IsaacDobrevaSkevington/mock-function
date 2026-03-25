package com.idscodelabs.mock_function.functions

import com.idscodelabs.mock_function.core.IMockFunction
import com.idscodelabs.mock_function.result.ResultBuilderScope
import com.idscodelabs.mock_function.result.ResultProducer

class MockFunction1<T, R>(
    resultProducer: ResultProducer<R>,
) : IMockFunction<R>(resultProducer),
    (T) -> R {
    override fun invoke(p1: T): R = onInvoke(p1)
}

fun <T> MockFunction1() = MockFunction1<T, Unit>(Unit)
fun <T, R> MockFunction1(result: R) = MockFunction1<T, R>(ResultProducer.always(result))
fun <T, R> MockFunction1(resultBuilder: ResultBuilderScope<R>.() -> Unit) = MockFunction1<T, R>(ResultProducer.from(resultBuilder))