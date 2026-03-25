package com.idscodelabs.mock_function.functions

import com.idscodelabs.mock_function.core.IMockFunction
import com.idscodelabs.mock_function.result.ResultBuilderScope
import com.idscodelabs.mock_function.result.ResultProducer

class MockFunction2<T, U, R>(
    resultProducer: ResultProducer<R>,
) : IMockFunction<R>(resultProducer),
    (T, U) -> R {
    override fun invoke(
        p1: T,
        p2: U,
    ): R = onInvoke(p1, p2)
}

fun <T, U> MockFunction2() = MockFunction2<T, U, Unit>(Unit)
fun <T, U, R> MockFunction2(result: R) = MockFunction2<T, U, R>(ResultProducer.always(result))
fun <T, U, R> MockFunction2(resultBuilder: ResultBuilderScope<R>.() -> Unit) = MockFunction2<T, U, R>(ResultProducer.from(resultBuilder))

