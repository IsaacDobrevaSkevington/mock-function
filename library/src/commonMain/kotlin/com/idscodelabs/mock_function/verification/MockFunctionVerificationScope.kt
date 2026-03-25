package com.idscodelabs.mock_function.verification

import com.idscodelabs.mock_function.matchers.CallMatcherScope

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