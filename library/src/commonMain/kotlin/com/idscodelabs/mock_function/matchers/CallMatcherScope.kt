package com.idscodelabs.mock_function.matchers

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