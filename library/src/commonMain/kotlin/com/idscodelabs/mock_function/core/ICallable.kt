package com.idscodelabs.mock_function.core

import com.idscodelabs.mock_function.matchers.CallMatcherScope
import com.idscodelabs.mock_function.verification.MockFunctionVerificationScope
import kotlin.test.assertEquals
import kotlin.test.assertTrue

abstract class ICallable {


    protected var calls: MutableList<Call> = mutableListOf()
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

    fun verify(block: MockFunctionVerificationScope.() -> Unit) {
        block(verificationScope)
    }

    protected fun onInvokeWithArguments(vararg args: Any?) {
        calls.add(Call(args.toList()))
    }

}