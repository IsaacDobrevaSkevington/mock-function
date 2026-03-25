package com.idscodelabs.mock_function.core

import com.idscodelabs.mock_function.matchers.CallMatcherScope
import com.idscodelabs.mock_function.result.ResultProducer
import com.idscodelabs.mock_function.verification.MockFunctionVerificationScope
import kotlin.test.assertEquals
import kotlin.test.assertTrue

abstract class ISpyFunction: ICallable(){
    protected fun onInvoke(vararg args: Any?) = onInvokeWithArguments(args)
}