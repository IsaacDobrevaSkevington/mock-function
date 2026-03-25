package com.idscodelabs.mock_function.functions

import com.idscodelabs.mock_function.core.IMockFunction
import com.idscodelabs.mock_function.result.ResultBuilderScope
import com.idscodelabs.mock_function.result.ResultProducer

class MockFunction4<T, U, V, W, R>(
    resultProducer: ResultProducer<R>,
) : IMockFunction<R>(resultProducer),
    (T, U, V, W) -> R {
    override fun invoke(
        p1: T,
        p2: U,
        p3: V,
        p4: W,
    ): R = onInvoke(p1, p2, p3, p4)
}

fun <T, U, V, W> MockFunction4() = MockFunction4<T, U, V, W, Unit>(Unit)
fun <T, U, V, W, R> MockFunction4(result: R) = MockFunction4<T, U, V, W, R>(ResultProducer.always(result))
fun <T, U, V, W, R> MockFunction4(resultBuilder: ResultBuilderScope<R>.() -> Unit) = MockFunction4<T, U, V, W, R>(ResultProducer.from(resultBuilder))