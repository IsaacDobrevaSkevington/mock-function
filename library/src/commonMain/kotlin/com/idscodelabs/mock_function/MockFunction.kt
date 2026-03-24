package com.idscodelabs.mock_function

import kotlin.test.*

class ArgMatcherScope(
    private val arg: Any?,
) {
    infix fun isEqualTo(expected: Any?) = asserter.assertEquals(null, expected, arg)

    fun isNull() = assertNull(arg)

    fun isNotNull() = assertNotNull(arg)

    @Suppress("UNCHECKED_CAST")
    fun <T> matches(condition: T.() -> Boolean) {
        val tArg = arg as T
        assertTrue(tArg.condition(), "Expected argument to match condition.\nArgument was: $arg")
    }
}

class CallMatcherScope(
    private val args: List<Any?>,
) {
    fun argument(
        index: Int,
        matcher: ArgMatcherScope.() -> Unit = {},
    ): ArgMatcherScope {
        val arg = args[index]
        return ArgMatcherScope(arg).apply(matcher)
    }

    fun argument(matcher: ArgMatcherScope.() -> Unit = {}): ArgMatcherScope = argument(0, matcher)

    val argument get() = argument()
}

interface MockFunctionVerificationScope {
    fun anyCall(matcher: CallMatcherScope.() -> Unit)

    fun callAt(
        index: Int,
        matcher: CallMatcherScope.() -> Unit,
    )

    fun onlyCall(matcher: CallMatcherScope.() -> Unit) {
        wasCalledExactly(1)
        callAt(0, matcher)
    }

    fun wasCalled()

    fun wasNotCalled()

    fun wasCalledExactly(times: Int)

    fun wasCalledAtLeast(times: Int)

    fun wasCalledAtMost(times: Int)
}

abstract class IMockFunction {
    protected var calls: MutableList<Call> = mutableListOf()

    class Call(
        val args: List<Any?>,
    ) {
        override fun toString(): String = "CALL with arguments $args"
    }

    protected val verificationScope =
        object : MockFunctionVerificationScope {
            override fun anyCall(matcher: CallMatcherScope.() -> Unit) {
                calls.forEach {
                    try {
                        CallMatcherScope(it.args).matcher()
                        return@anyCall
                    } catch (_: Throwable) {
                    }
                }
                throw AssertionError(
                    "Expected at least one matching call, but no calls found.\nCalls include\n:${calls.joinToString("\n")} ",
                )
            }

            override fun callAt(
                index: Int,
                matcher: CallMatcherScope.() -> Unit,
            ) {
                wasCalledAtLeast(index)
                CallMatcherScope(calls[index].args).matcher()
            }

            override fun wasCalled() {
                assertTrue(calls.isNotEmpty(), "Expected function to be called, was not called.")
            }

            override fun wasNotCalled() {
                assertTrue(calls.isEmpty(), "Expected function not to be called, was called.")
            }

            override fun wasCalledExactly(times: Int) {
                assertEquals(times, calls.size, "Expected function to be called $times times, was called ${calls.size} times.")
            }

            override fun wasCalledAtLeast(times: Int) {
                assertTrue(calls.size >= times, "Expected function to be called at least $times times, was called ${calls.size} times.")
            }

            override fun wasCalledAtMost(times: Int) {
                assertTrue(calls.size <= times, "Expected function to be called at most $times times, was called ${calls.size} times.")
            }
        }

    protected fun onInvoke(vararg args: Any?) {
        calls.add(Call(args.toList()))
    }

    fun verify(block: MockFunctionVerificationScope.() -> Unit) {
        block(verificationScope)
    }
}

class MockFunction<R>(
    private val result: R,
) : IMockFunction(),
    () -> R {
    override fun invoke(): R {
        onInvoke()
        return result
    }
}

fun MockFunction() = MockFunction(Unit)

class MockFunction1<T, R>(
    private val result: R,
) : IMockFunction(),
    (T) -> R {
    override fun invoke(p1: T): R {
        onInvoke(p1)
        return result
    }
}

fun <T> MockFunction1() = MockFunction1<T, Unit>(Unit)

class MockFunction2<T, U, R>(
    private val result: R,
) : IMockFunction(),
    (T, U) -> R {
    override fun invoke(
        p1: T,
        p2: U,
    ): R {
        onInvoke(p1, p2)
        return result
    }
}

fun <T, U> MockFunction2() = MockFunction2<T, U, Unit>(Unit)

class MockFunction3<T, U, V, R>(
    private val result: R,
) : IMockFunction(),
    (T, U, V) -> R {
    override fun invoke(
        p1: T,
        p2: U,
        p3: V,
    ): R {
        onInvoke(p1, p2, p3)
        return result
    }
}

fun <T, U, V> MockFunction3() = MockFunction3<T, U, V, Unit>(Unit)

class MockFunction4<T, U, V, W, R>(
    private val result: R,
) : IMockFunction(),
    (T, U, V, W) -> R {
    override fun invoke(
        p1: T,
        p2: U,
        p3: V,
        p4: W,
    ): R {
        onInvoke(p1, p2, p3, p4)
        return result
    }
}

fun <T, U, V, W> MockFunction4() = MockFunction4<T, U, V, W, Unit>(Unit)
