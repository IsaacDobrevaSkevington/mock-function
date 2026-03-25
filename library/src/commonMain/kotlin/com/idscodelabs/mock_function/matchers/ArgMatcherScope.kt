package com.idscodelabs.mock_function.matchers

import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.test.asserter

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