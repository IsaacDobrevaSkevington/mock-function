package com.idscodelabs.mock_function.functions

import com.idscodelabs.mock_function.core.IMockFunction
import com.idscodelabs.mock_function.result.ResultBuilderScope
import com.idscodelabs.mock_function.result.ResultProducer

class MockFunction3<T, U, V, R>(
    resultProducer: ResultProducer<R>,
) : IMockFunction<R>(resultProducer),
    (T, U, V) -> R {
    override fun invoke(
        p1: T,
        p2: U,
        p3: V,
    ): R = onInvoke(p1, p2, p3)
}

fun <T, U, V> MockFunction3() = MockFunction3<T, U, V, Unit>(Unit)
fun <T, U, V, R> MockFunction3(result: R) = MockFunction3<T, U, V, R>(ResultProducer.always(result))
fun <T, U, V, R> MockFunction3(resultBuilder: ResultBuilderScope<R>.() -> Unit) = MockFunction3<T, U, V, R>(ResultProducer.from(resultBuilder))

